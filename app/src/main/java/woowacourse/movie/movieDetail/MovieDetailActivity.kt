package woowacourse.movie.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.IntentCompat
import woowacourse.movie.R
import woowacourse.movie.base.BindingActivity
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.error.ErrorActivity
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.seat.TheaterSeatActivity

class MovieDetailActivity :
    BindingActivity<ActivityMovieDetailBinding>(R.layout.activity_movie_detail),
    MovieDetailContract.View,
    TicketCountListener {
    private lateinit var presenter: MovieDetailContract.Presenter
    private lateinit var dateAdapter: ArrayAdapter<String>
    private lateinit var timeAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cinema =
            IntentCompat.getSerializableExtra(intent, EXTRA_CINEMA, Cinema::class.java)
                ?: return ErrorActivity.start(this)
        presenter = MovieDetailPresenter(this, cinema)
        initView()
        initClickListener()
        presenter.loadMovieInfo()
        presenter.loadRunMovieDateRange()
    }

    override fun onTicketCountChanged(ticketNum: Int) {
        binding.ticketNum = ticketNum
        binding.invalidateAll()
    }

    override fun showTicketMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClickDecrease() {
        presenter.decreaseTicketCount()
    }

    override fun onClickIncrease() {
        presenter.increaseTicketCount()
    }

    override fun showTitle(title: Title) {
        binding.title = title.toString()
        binding.invalidateAll()
    }

    override fun showRunningTime(runningTime: RunningTime) {
        binding.runningTime = runningTime.toString()
        binding.invalidateAll()
    }

    override fun showSynopsis(synopsis: Synopsis) {
        binding.synopsis = synopsis.toString()
        binding.invalidateAll()
    }

    override fun showReleaseDate(movieDate: MovieDate) {
        binding.date = movieDate.date
        binding.invalidateAll()
    }

    override fun updateDateAdapter(dates: List<String>) {
        dateAdapter.clear()
        dateAdapter.addAll(dates)
    }

    override fun updateTimeAdapter(times: List<String>) {
        timeAdapter.clear()
        timeAdapter.addAll(times)
    }

    override fun navigateToPurchaseConfirmation(cinema: Cinema) {
        val intent =
            TheaterSeatActivity.newIntent(
                this,
                binding.quantityTextView.text.toString(),
                cinema,
                timeDate(),
            )
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.listener = this
        dateAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                mutableListOf(),
            )
        binding.movieDateSpinner.adapter = dateAdapter
        binding.movieDateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    presenter.updateRunMovieTimes()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        timeAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                mutableListOf(),
            )
        binding.movieTimeSpinner.adapter = timeAdapter
    }

    private fun initClickListener() {
        binding.seatConfirmationButton.setOnClickListener {
            presenter.confirmPurchase()
        }
    }

    private fun timeDate() =
        buildString {
            append(binding.movieDateSpinner.selectedItem.toString())
            append(DELIMITER)
            append(binding.movieTimeSpinner.selectedItem.toString())
        }

    companion object {
        const val DELIMITER = " "
        const val EXTRA_CINEMA = "cinema"

        fun newIntent(
            context: Context,
            cinema: Cinema,
        ): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_CINEMA, cinema)
            }
        }
    }
}
