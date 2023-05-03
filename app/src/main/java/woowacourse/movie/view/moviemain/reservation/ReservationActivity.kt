package woowacourse.movie.view.moviemain.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.util.DATE_FORMATTER
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.view.model.MovieListModel.MovieUiModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.seatselection.SeatSelectionActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationActivity : AppCompatActivity(), ReservationContract.View {

    private lateinit var binding: ActivityReservationBinding
    override lateinit var presenter: ReservationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movie = intent.getParcelableCompat<MovieUiModel>(MOVIE)
        if (movie == null) {
            Toast.makeText(this, "데이터가 없습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        presenter = ReservationPresenter(this)

        setViewData(movie)
        setDateSpinner(movie.screeningDates)
        setPeopleCountAdjustButtonClickListener()
        setReserveButtonClickListener(movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setCount(count: Int) {
        binding.peopleCount.text = count.toString()
    }

    private fun setViewData(movie: MovieUiModel) {
        binding.apply {
            moviePoster.setImageResource(movie.posterResourceId)
            movieTitle.text = movie.title
            movieScreeningDate.text = getString(R.string.screening_date_format).format(
                movie.screeningDates.min().format(DATE_FORMATTER),
                movie.screeningDates.max().format(DATE_FORMATTER),
            )
            movieRunningTime.text =
                getString(R.string.running_time_format).format(movie.runningTime)
            movieSummary.text = movie.summary
        }
    }

    private fun setDateSpinner(screeningDates: List<LocalDate>) {
        val dateSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            screeningDates,
        )

        binding.dateSpinner.apply {
            adapter = dateSpinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    presenter.onDateSpinnerChanged(screeningDates[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }

    override fun setTimeSpinner(screeningTimes: List<LocalTime>) {
        val timeSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            screeningTimes,
        )
        binding.timeSpinner.apply {
            adapter = timeSpinnerAdapter
        }
    }

    private fun setPeopleCountAdjustButtonClickListener() {
        binding.apply {
            minusButton.setOnClickListener {
                presenter.onMinusClick()
            }
            plusButton.setOnClickListener {
                presenter.onPlusClick()
            }
        }
    }

    private fun setReserveButtonClickListener(movie: MovieUiModel) {
        binding.reservationButton.setOnClickListener {
            val reservationOptions = ReservationOptions(
                movie.title,
                LocalDateTime.of(
                    binding.dateSpinner.selectedItem as LocalDate,
                    binding.timeSpinner.selectedItem as LocalTime,
                ),
                binding.peopleCount.text.toString().toInt(),
            )
            startActivity(SeatSelectionActivity.newIntent(this, reservationOptions, movie))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.apply {
            putInt(PEOPLE_COUNT, binding.peopleCount.text.toString().toInt())
            putInt(SELECTED_DATE_POSITION, binding.dateSpinner.selectedItemPosition)
            putInt(SELECTED_TIME_POSITION, binding.timeSpinner.selectedItemPosition)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setCount(savedInstanceState.getInt(PEOPLE_COUNT))
        binding.dateSpinner.setSelection(savedInstanceState.getInt(SELECTED_DATE_POSITION))
        binding.timeSpinner.setSelection(savedInstanceState.getInt(SELECTED_TIME_POSITION))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val PEOPLE_COUNT = "PEOPLE_COUNT"

        private const val SELECTED_DATE_POSITION = "SELECTED_DATE_POSITION"
        private const val SELECTED_TIME_POSITION = "SELECTED_TIME_POSITION"
        private const val MOVIE = "MOVIE"

        fun newIntent(context: Context, movie: MovieUiModel): Intent {
            val intent = Intent(context, ReservationActivity::class.java)
            intent.putExtra(MOVIE, movie)
            return intent
        }
    }
}
