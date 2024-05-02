package woowacourse.movie.movieDetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.seat.TheaterSeatActivity
import java.time.LocalTime

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
    private val binding: ActivityMovieDetailBinding by lazy {
        ActivityMovieDetailBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var presenter: MovieDetailContract.Presenter
    private lateinit var dateAdapter: ArrayAdapter<String>
    private lateinit var timeAdapter: ArrayAdapter<String>
    private val seatConfirmationButton: Button by lazy { findViewById(R.id.seat_confirmation_button) }
    private lateinit var times: List<LocalTime>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val cinema =
            IntentCompat.getSerializableExtra(intent, "Cinema", Cinema::class.java) ?: error(" ")
        val theater = cinema.theater
        times = theater.times
        presenter = MovieDetailPresenter(
            view = this@MovieDetailActivity,
        ).also { binding.presenter = it }
        presenter.load(theater.movie)
        cinema.let { setupEventListeners(it) }
        presenter.generateDateRange()
    }

    override fun navigateToPurchaseConfirmation(intent: Intent) {
        startActivity(intent)
    }

    override fun onTicketCountChanged() {
        binding.invalidateAll()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateDateAdapter(dates: List<String>) {
        dateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dates)
        binding.movieDateSpinner.adapter = dateAdapter
        binding.movieDateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    presenter.updateTimeSpinner(times.map { it.toString() })
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    override fun updateTimeAdapter(times: List<String>) {
        timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, times)
        binding.movieTimeSpinner.adapter = timeAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun setupEventListeners(cinema: Cinema) {

        seatConfirmationButton.setOnClickListener {
            val intent = Intent(this, TheaterSeatActivity::class.java).apply {
                putExtra("ticketNum", presenter.getTickets().toString())
                putExtra("Cinema", cinema)
                putExtra(
                    "timeDate",
                    binding.movieDateSpinner.selectedItem.toString() + " " + binding.movieTimeSpinner.selectedItem.toString()
                )
            }
            navigateToPurchaseConfirmation(intent)
        }
    }
}
