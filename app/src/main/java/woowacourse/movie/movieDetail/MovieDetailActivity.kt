package woowacourse.movie.movieDetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.IntentCompat
import woowacourse.movie.R
import woowacourse.movie.common.BindingActivity
import woowacourse.movie.common.ui.redirectToErrorActivity
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.model.Cinema
import woowacourse.movie.seat.TheaterSeatActivity

class MovieDetailActivity :
    BindingActivity<ActivityMovieDetailBinding>(R.layout.activity_movie_detail),
    MovieDetailContract.View {
    private lateinit var presenter: MovieDetailContract.Presenter
    private lateinit var dateAdapter: ArrayAdapter<String>
    private lateinit var timeAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val cinema =
            IntentCompat.getSerializableExtra(intent, "Cinema", Cinema::class.java)
        if (cinema == null) {
            redirectToErrorActivity()
            return
        }
        presenter =
            MovieDetailPresenter(
                view = this@MovieDetailActivity,
                cinema
            ).also { binding.presenter = it }
        setupEventListeners(cinema)
        presenter.generateDateRange()
    }

    override fun navigateToPurchaseConfirmation(intent: Intent) {
        startActivity(intent)
    }

    override fun onTicketCountChanged() {
        binding.invalidateAll()
    }

    override fun showTicketMessage(message: String) {
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
                    presenter.updateTimes()
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
        binding.seatConfirmationButton.setOnClickListener {
            val intent =
                Intent(this, TheaterSeatActivity::class.java).apply {
                    putExtra("ticketNum", binding.quantityTextView.text.toString())
                    putExtra("Cinema", cinema)
                    putExtra(
                        "timeDate",
                        binding.movieDateSpinner.selectedItem.toString() + " " + binding.movieTimeSpinner.selectedItem.toString(),
                    )
                }
            navigateToPurchaseConfirmation(intent)
        }
    }
}
