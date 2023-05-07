package woowacourse.movie.presentation.view.main.home.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.model.MovieBookingInfo
import woowacourse.movie.presentation.view.common.BackButtonActivity
import woowacourse.movie.presentation.view.main.home.seatpick.SeatPickerActivity

class MovieDetailActivity : BackButtonActivity(), MovieDetailContract.View {
    private lateinit var binding: ActivityMovieDetailBinding
    private var restoreInstanceFlag = true
    private val presenter: MovieDetailContract.Presenter by lazy {
        MovieDetailPresenter(
            view = this,
            movie = intent.getParcelableCompat(MOVIE_DATA_INTENT_KEY)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        restoreInstanceFlag = true
        presenter.onCreate()
        presenter.getMovieSchedule()
        setClickListener()
        reloadTicketCountInstance(savedInstanceState)
    }

    override fun initView(movie: Movie) {
        binding.ivMoviePoster.setImageResource(movie.poster)
        binding.tvMovieTitle.text = movie.title
        binding.tvMovieReleaseDate.text = movie.releaseDate
        binding.tvMovieRunningTime.text = movie.runningTime
        binding.tvMovieSynopsis.text = movie.synopsis
    }

    override fun finishErrorView() {
        Toast.makeText(this, getString(R.string.error_intent_message), Toast.LENGTH_SHORT)
            .show()
        this.finish()
    }

    override fun updateScheduleDateView(scheduleDate: List<String>, scheduleTime: List<String>) {
        binding.spMovieDate.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            scheduleDate
        )
        binding.spMovieTime.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            scheduleTime
        )
    }

    private fun reloadTicketCountInstance(
        savedInstanceState: Bundle?,
    ) {
        if (savedInstanceState != null) {
            binding.tvTicketCount.text =
                savedInstanceState.getString(USER_TICKET_COUNT_BUNDLE_KEY)
        }
    }

    override fun updateScheduleTimeView(movieScheduleTime: List<String>) {
        binding.spMovieDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.spMovieTime.adapter = ArrayAdapter(
                    this@MovieDetailActivity,
                    android.R.layout.simple_spinner_item,
                    movieScheduleTime
                )
                if (restoreInstanceFlag && bundleOf().isEmpty.not()) {
                    binding.spMovieTime.setSelection(
                        (
                                bundleOf().getString(MOVIE_INFO_TIME_BUNDLE_KEY)
                                    ?: movieScheduleTime.first()
                                ).toInt()
                    )
                    restoreInstanceFlag = false
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setClickListener() {
        binding.btTicketCountMinus.setOnClickListener {
            presenter.removeTicket(binding.tvTicketCount.text.toString().toInt())
        }

        binding.btTicketCountPlus.setOnClickListener {
            presenter.addTicket(binding.tvTicketCount.text.toString().toInt())
        }

        binding.btBookComplete.setOnClickListener {
            presenter.getMovieBookingInfo(
                binding.tvTicketCount.text.toString().toInt(),
                binding.spMovieDate.selectedItem.toString(),
                binding.spMovieTime.selectedItem.toString()
            )
        }
    }

    override fun updateTicketCountView(currentTicketCount: Int) {
        binding.tvTicketCount.text = currentTicketCount.toString()
    }

    override fun showErrorTicketCountIsZeroView() {
        Toast.makeText(
            this,
            getString(R.string.error_booking_over_one_ticket),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showInfoSelectedView(movieBookingInfo: MovieBookingInfo) {
        val intent = SeatPickerActivity.getIntent(this).apply {
            putExtra(
                SeatPickerActivity.MOVIE_BOOKING_INFO_SCHEDULE_INTENT_KEY,
                movieBookingInfo
            )
        }
        startActivity(intent)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            MOVIE_INFO_TIME_BUNDLE_KEY,
            binding.spMovieTime.selectedItemPosition.toString()
        )
        outState.putString(
            USER_TICKET_COUNT_BUNDLE_KEY,
            binding.tvTicketCount.text.toString()
        )
    }

    companion object {
        const val MOVIE_DATA_INTENT_KEY = "MOVIE_DATA_INTENT_KEY"
        const val MOVIE_INFO_TIME_BUNDLE_KEY = "MOVIE_INFO_TIME_BUNDLE_KEY"
        const val USER_TICKET_COUNT_BUNDLE_KEY = "USER_TICKET_COUNT_BUNDLE_KEY"

        fun getIntent(context: Context): Intent {
            return Intent(context, MovieDetailActivity::class.java)
        }
    }
}
