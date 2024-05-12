package woowacourse.movie.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.moviedetail.uimodel.BookingDetailUiModel
import woowacourse.movie.moviedetail.uimodel.BookingInfoUiModel
import woowacourse.movie.moviedetail.uimodel.HeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.MovieDetailUiModel
import woowacourse.movie.moviedetail.uimodel.ScheduleUiModels
import woowacourse.movie.selectseat.SelectSeatActivity
import woowacourse.movie.util.buildFetchScreeningScheduleWithMovieIdAndTheaterId
import woowacourse.movie.util.buildFetchScreeningsWithMovieIdAndTheaterIdUseCase
import woowacourse.movie.util.bundleParcelable
import woowacourse.movie.util.showErrorToastMessage

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
    private lateinit var presenter: MovieDetailContract.Presenter
    private lateinit var dateAdapter: ArrayAdapter<String>
    private lateinit var timeAdapter: ArrayAdapter<String>
    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var bookingDetailUiModel: BookingDetailUiModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickListener()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val db = (application as MovieApplication).db
        presenter =
            MovieDetailPresenter(
                this,
                buildFetchScreeningScheduleWithMovieIdAndTheaterId(db),
                buildFetchScreeningsWithMovieIdAndTheaterIdUseCase(db),
            )
        val movieId = movieId()
        val theaterId = theaterId()
        presenter.loadMovieDetail(movieId, theaterId)
        binding.bookingDetail = bookingDetailUiModel
        binding.view = this
    }

    private fun movieId(): Long {
        val movieId = intent.getLongExtra(MOVIE_ID, INVALID_ID)
        if (movieId == INVALID_ID) {
            error("movieId에 관한 정보가 없습니다.")
        }
        return movieId
    }

    private fun theaterId(): Long {
        val theaterId = intent.getLongExtra(THEATER_ID, INVALID_ID)
        if (theaterId == INVALID_ID) {
            error("theaterId에 관한 정보가 없습니다.")
        }
        return theaterId
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val bookingInfo = bookingDetailUiModel
        outState.putParcelable(STATE_BOOKING_ID, bookingInfo)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val storedBookingInfo =
            savedInstanceState.bundleParcelable(STATE_BOOKING_ID, BookingDetailUiModel::class.java)
        storedBookingInfo?.let {
            bookingDetailUiModel = it
            binding.bookingDetail = it
            binding.spinnerDetailDate.setSelection(bookingDetailUiModel.date.position)
            binding.spinnerDetailTime.setSelection(bookingDetailUiModel.time.position)
        }
    }

    private fun initClickListener() {
        binding.btnDetailComplete.setOnClickListener {
            presenter.confirm()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun intendToPlusCount() {
        presenter.plusCount()
    }

    override fun intendToMinusCount() {
        presenter.minusCount()
    }

    override fun showMovieInfo(movieDetail: MovieDetailUiModel) {
        binding.movieReservationUiModel = movieDetail
    }

    override fun updateHeadCount(updatedCount: HeadCountUiModel) {
        bookingDetailUiModel = bookingDetailUiModel.updateCount(updatedCount)
        binding.bookingDetail = bookingDetailUiModel
    }

    override fun navigateToSeatSelect(bookingInfoUiModel: BookingInfoUiModel) {
        startActivity(
            SelectSeatActivity.getIntent(
                this,
                bookingInfoUiModel,
            ),
        )
    }

    override fun showScreeningMovieError() {
        showErrorToastMessage(this, getString(R.string.load_movie_error_message))
    }

    override fun showMovieReservationError() {
        showErrorToastMessage(this, getString(R.string.reserve_error_message))
    }

    override fun showTime(times: List<String>) {
        timeAdapter.clear()
        timeAdapter.addAll(times)
    }

    override fun showCantDecreaseError(minCount: Int) {
        showErrorToastMessage(this, getString(R.string.min_count_error_message, minCount))
    }

    override fun showBookingDetail(
        screeningDateTimeUiModels: ScheduleUiModels,
        bookingDetailUiModel: BookingDetailUiModel,
    ) {
        this.bookingDetailUiModel = bookingDetailUiModel
        initSpinner(screeningDateTimeUiModels)
    }

    private fun initSpinner(scheduleUiModels: ScheduleUiModels) {
        dateAdapter =
            ArrayAdapter(
                this,
                R.layout.item_spinner,
                scheduleUiModels.dates(),
            )

        timeAdapter =
            ArrayAdapter(this, R.layout.item_spinner, scheduleUiModels.defaultTimes())

        binding.spinnerDetailDate.adapter = dateAdapter
        binding.spinnerDetailTime.adapter = timeAdapter

        setSpinnerClickListener(scheduleUiModels)
    }

    private fun setSpinnerClickListener(scheduleUiModels: ScheduleUiModels) {
        binding.spinnerDetailDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    presenter.selectDate(position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

        binding.spinnerDetailTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    presenter.selectTime(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    companion object {
        const val MOVIE_ID: String = "movieId"
        const val THEATER_ID: String = "theaterId"
        const val INVALID_ID = -1L
        private const val STATE_BOOKING_ID = "booking"

        fun getIntent(
            context: Context,
            movieId: Long,
            theaterId: Long,
        ): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
                putExtra(THEATER_ID, theaterId)
            }
        }
    }
}
