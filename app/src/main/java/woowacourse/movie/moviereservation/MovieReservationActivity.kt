package woowacourse.movie.moviereservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.databinding.ActivityMovieReservationBinding
import woowacourse.movie.moviereservation.uimodel.BookingInfo
import woowacourse.movie.moviereservation.uimodel.CurrentBookingDetail
import woowacourse.movie.moviereservation.uimodel.MovieReservationUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimesUiModel
import woowacourse.movie.selectseat.SelectSeatActivity
import woowacourse.movie.util.bundleParcelable
import woowacourse.movie.util.showErrorToastMessage

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    private lateinit var presenter: MovieReservationContract.Presenter
    private lateinit var dateAdapter: ArrayAdapter<String>
    private lateinit var timeAdapter: ArrayAdapter<String>
    private lateinit var binding: ActivityMovieReservationBinding

    private lateinit var currentBookingDetail: CurrentBookingDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = movieId()
        initClickListener()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter =
            MovieReservationPresenter(
                this, DummyMovies,
            )
        presenter.loadMovieDetail(id)
    }

    private fun movieId(): Long {
        val movieId = intent.getLongExtra(EXTRA_SCREEN_MOVIE_ID, INVALID_ID)
        val theaterId = intent.getLongExtra(EXTRA_THEATER_ID, INVALID_ID)
        if (movieId == INVALID_ID || theaterId == INVALID_ID) {
            error("movieId에 관한 정보가 없습니다.")
        }
        return movieId
    }

    private fun theaterId(): Long {
        val theaterId = intent.getLongExtra(EXTRA_THEATER_ID, INVALID_ID)
        if (theaterId == INVALID_ID) {
            error("theaterId에 관한 정보가 없습니다.")
        }
        return theaterId
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val bookingInfo = currentBookingDetail
        Log.d("테스트", "$bookingInfo")
        outState.putParcelable(STATE_BOOKING_ID, bookingInfo)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val storedBookingInfo =
            savedInstanceState.bundleParcelable(STATE_BOOKING_ID, CurrentBookingDetail::class.java)
        Log.d("테스트", "$storedBookingInfo")
        storedBookingInfo?.let {
            currentBookingDetail = it
            binding.currentBookingDetail = currentBookingDetail
            binding.spinnerDetailDate.setSelection(currentBookingDetail.datePosition)
            binding.spinnerDetailTime.setSelection(currentBookingDetail.timePosition)
        }
    }

    private fun initClickListener() {
        binding.btnDetailComplete.setOnClickListener {
            presenter.completeBookingDetail(movieId(), theaterId(), this.currentBookingDetail)
        }

        binding.btnDetailPlus.setOnClickListener {
            Log.d("테스트", "플러스 버튼 $currentBookingDetail")
            presenter.plusCount(this.currentBookingDetail.count)
        }

        binding.btnDetailMinus.setOnClickListener {
            presenter.minusCount(this.currentBookingDetail.count)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMovieInfo(reservation: MovieReservationUiModel) {
        binding.movieReservationUiModel = reservation
    }

    override fun updateHeadCount(updatedCount: Int) {
        currentBookingDetail = currentBookingDetail.updateCount(updatedCount)
        binding.currentBookingDetail = currentBookingDetail
    }

    override fun navigateToSelectSeatView(bookingInfo: BookingInfo) {
        startActivity(SelectSeatActivity.getIntent(this, bookingInfo))
    }

    override fun showScreeningMovieError() {
        showErrorToastMessage(this, getString(R.string.load_movie_error_message))
    }

    override fun showMovieReservationError() {
        showErrorToastMessage(this, getString(R.string.reserve_error_message))
    }

    override fun showCantDecreaseError(minCount: Int) {
        showErrorToastMessage(this, getString(R.string.min_count_error_message, minCount))
    }

    override fun showBookingDetail(
        screeningDateTimesUiModel: ScreeningDateTimesUiModel,
        currentBookingDetail: CurrentBookingDetail,
    ) {
        Log.d("테스트", "showBookingDetail $currentBookingDetail")
        this.currentBookingDetail = currentBookingDetail
        binding.currentBookingDetail = currentBookingDetail
        Log.d("테스트", "showBookingDetail2 ${this.currentBookingDetail}")
        initSpinner(screeningDateTimesUiModel)
    }

    private fun initSpinner(screeningDateTimesUiModel: ScreeningDateTimesUiModel) {
        dateAdapter =
            ArrayAdapter(
                this,
                R.layout.item_spinner,
                screeningDateTimesUiModel.dates(),
            )

        timeAdapter =
            ArrayAdapter(this, R.layout.item_spinner, screeningDateTimesUiModel.defaultTimes())

        binding.spinnerDetailDate.adapter = dateAdapter
        binding.spinnerDetailTime.adapter = timeAdapter

        setSpinnerClickListener(screeningDateTimesUiModel)
    }

    private fun setSpinnerClickListener(screeningDateTimesUiModel: ScreeningDateTimesUiModel) {
        binding.spinnerDetailDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    currentBookingDetail =
                        currentBookingDetail.updateDate(
                            position,
                        )
                    timeAdapter.clear()
                    timeAdapter.addAll(screeningDateTimesUiModel.screeningTimeOfDate(position))
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
                    currentBookingDetail =
                        currentBookingDetail.updateTime(
                            position,
                        )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    companion object {
        const val EXTRA_SCREEN_MOVIE_ID: String = "screenMovieId"
        const val EXTRA_THEATER_ID: String = "theaterId"
        const val INVALID_ID = -1L
        private const val STATE_BOOKING_ID = "booking"

        fun getIntent(
            context: Context,
            screenMovieId: Long,
            theaterId: Long,
        ): Intent {
            return Intent(context, MovieReservationActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MOVIE_ID, screenMovieId)
                putExtra(EXTRA_THEATER_ID, theaterId)
            }
        }
    }
}
