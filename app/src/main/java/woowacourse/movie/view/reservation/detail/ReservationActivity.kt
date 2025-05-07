package woowacourse.movie.view.reservation.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.movietime.MovieSchedule
import woowacourse.movie.view.dialog.DialogFactory
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.getMovieById
import woowacourse.movie.view.home.theater.Showing
import woowacourse.movie.view.reservation.seat.ReservationSeatActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationActivity : AppCompatActivity(), ReservationContract.View {
    private val present: ReservationContract.Presenter by lazy {
        ReservationPresent(this)
    }
    private var _binding: ActivityReservationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootLayoutReservation) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val movieId: Int = intent.getIntExtra(KEY_MOVIE_ID, 0)

        val showings: Showing? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(KEY_SHOWINGS, Showing::class.java)
            } else {
                intent.getSerializableExtra(KEY_SHOWINGS) as? Showing
            }
        checkTheater(movieId, showings)
        bindButtonListeners()
    }

    private fun checkTheater(
        movieId: Int,
        showings: Showing?,
    ) {
        if (movieId == 0 || showings == null) {
            showErrorInvalidMovie()
        } else {
            present.fetchData(getMovieById(movieId), showings)
        }
    }

    private fun bindButtonListeners() {
        binding.onClickPlus = View.OnClickListener { present.increasedCount() }
        binding.onClickMinus = View.OnClickListener { present.decreasedCount() }
    }

    override fun showErrorInvalidMovie() {
        DialogFactory().showError(this) {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showCount(count: Int) {
        binding.count = count
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        present.onSaveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        present.onRestoreState(savedInstanceState)
    }

    override fun showSpinnerData(
        movie: Movie,
        selectedDatePosition: Int,
        showings: Showing,
    ) {
        setDateSpinner(movie, LocalDate.now(), binding.spinnerTime, showings)

        binding.spinnerDate.setSelection(selectedDatePosition)
    }

    override fun showMovieReservationScreen(movieUi: MovieUi) {
        binding.movieUi = movieUi
    }

    override fun setReservationButton(showings: Showing) {
        val reservationButton = binding.btnReservation

        reservationButton.setOnClickListener {
            val selectedDate: LocalDate = binding.spinnerDate.selectedItem as LocalDate
            val selectedTime: LocalTime? = binding.spinnerTime.selectedItem as? LocalTime?
            if (selectedTime == null) {
                Toast.makeText(
                    this,
                    getString(R.string.message_not_allowed_time),
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }
            present.createTicket(LocalDateTime.of(selectedDate, selectedTime), showings.theaterName)
        }
    }

    override fun navigateToReservationComplete(ticket: Ticket) {
        val intent = ReservationSeatActivity.newIntent(this@ReservationActivity, ticket)
        startActivity(intent)
    }

    private fun setDateSpinner(
        movie: Movie,
        localDate: LocalDate,
        spinnerTime: Spinner,
        showings: Showing,
    ) {
        val movieSchedule = MovieSchedule(movie.date)
        val currentDateSpinner = movieSchedule.selectableDates(localDate)

        binding.spinnerDate.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                currentDateSpinner,
            )

        binding.spinnerDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedDate = currentDateSpinner[position]
                    present.resetSelectedTimePosition(position)
                    setTimeSpinner(spinnerTime, selectedDate, showings)
                    present.selectedDate(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setTimeSpinner(
        spinner: Spinner,
        localDate: LocalDate,
        showings: Showing,
    ) {
        val currentTimeTable =
            showings.scheduleTime.afterCurrentDateSchedule(
                localDate.atStartOfDay(),
                LocalDateTime.now(),
            )
        spinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                currentTimeTable,
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    present.selectedTime(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    override fun setTimeSelection(position: Int) {
        binding.spinnerTime.setSelection(position)
    }

    companion object {
        private const val KEY_MOVIE_ID = "MOVIE_ID"
        private const val KEY_SHOWINGS = "SHOWINGS"

        fun newIntent(
            context: Context,
            movieId: Int?,
            showings: Showing?,
        ): Intent =
            Intent(context, ReservationActivity::class.java)
                .putExtra(
                    KEY_MOVIE_ID,
                    movieId,
                )
                .putExtra(
                    KEY_SHOWINGS,
                    showings,
                )
    }
}
