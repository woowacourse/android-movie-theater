package woowacourse.movie.moviereservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.databinding.ActivityMovieReservationBinding
import woowacourse.movie.moviereservation.uimodel.BookingDetailUiModel
import woowacourse.movie.moviereservation.uimodel.BookingInfoUiModel
import woowacourse.movie.moviereservation.uimodel.HeadCountUiModel
import woowacourse.movie.moviereservation.uimodel.MovieReservationUiModel
import woowacourse.movie.moviereservation.uimodel.ScreeningDateTimesUiModel
import woowacourse.movie.reservationresult.ReservationResultActivity
import woowacourse.movie.selectseat.SelectSeatActivity
import woowacourse.movie.util.bundleParcelable
import woowacourse.movie.util.showErrorToastMessage

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    private lateinit var presenter: MovieReservationContract.Presenter
    private lateinit var dateAdapter: ArrayAdapter<String>
    private lateinit var timeAdapter: ArrayAdapter<String>
    private lateinit var binding: ActivityMovieReservationBinding

    private lateinit var bookingDetailUiModel: BookingDetailUiModel

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
        binding.bookingDetail = bookingDetailUiModel
        binding.presenter = presenter
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
            startActivity(SelectSeatActivity.getIntent(this, BookingInfoUiModel(movieId(), theaterId(), bookingDetailUiModel)))
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

    override fun updateHeadCount(updatedCount: HeadCountUiModel) {
        bookingDetailUiModel = bookingDetailUiModel.updateCount(updatedCount)
        binding.bookingDetail = bookingDetailUiModel
    }

    override fun navigateToReservationResultView(reservationId: Long) {
        startActivity(ReservationResultActivity.getIntent(this, reservationId))
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
        bookingDetailUiModel: BookingDetailUiModel,
    ) {
        this.bookingDetailUiModel = bookingDetailUiModel
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
                    bookingDetailUiModel =
                        bookingDetailUiModel.updateDate(
                            position, binding.spinnerDetailDate.selectedItem.toString(),
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
                    bookingDetailUiModel =
                        bookingDetailUiModel.updateTime(
                            position, binding.spinnerDetailTime.selectedItem.toString(),
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
