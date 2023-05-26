package woowacourse.movie.activity.seatselection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.activity.reservationresult.ReservationResultActivity
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.datasource.ReservationDataSource
import woowacourse.movie.service.AlarmMaker
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.PriceViewData
import woowacourse.movie.view.data.ReservationDetailViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.SeatsViewData
import woowacourse.movie.view.error.ActivityError.finishWithError
import woowacourse.movie.view.getSerializable
import woowacourse.movie.view.widget.SaveStateSeats
import woowacourse.movie.view.widget.SeatTableLayout
import java.time.LocalDateTime

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {
    override lateinit var presenter: SeatSelectionContract.Presenter
    lateinit var movie: MovieViewData
    private val binding: ActivitySeatSelectionBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_seat_selection)
    }

    private val seatTableLayout: SeatTableLayout by lazy {
        SeatTableLayout.from(
            binding.seatSelectionTable,
            SEAT_ROW_COUNT,
            SEAT_COLUMN_COUNT,
        )
    }
    private val saveStateSeats: SaveStateSeats by lazy {
        SaveStateSeats(
            SEAT_TABLE_LAYOUT_STATE_KEY,
            seatTableLayout,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val movie: MovieViewData =
            intent.extras?.getSerializable<MovieViewData>(MovieViewData.MOVIE_EXTRA_NAME)
                ?: return finishWithError(MovieViewData.MOVIE_EXTRA_NAME)
        initPresenter()
        binding.movie = movie
        initSeatSelectionView(savedInstanceState, movie)
    }

    private fun initPresenter() {
        presenter = SeatSelectionPresenter(
            this,
            ReservationDataSource(this),
        )
    }

    private fun initSeatSelectionView(savedInstanceState: Bundle?, movie: MovieViewData) {
        val reservationDetail =
            intent.extras?.getSerializable<ReservationDetailViewData>(ReservationDetailViewData.RESERVATION_DETAIL_EXTRA_NAME)
                ?: return finishWithError(ReservationDetailViewData.RESERVATION_DETAIL_EXTRA_NAME)

        initMovieView(movie)
        initPrice(PriceViewData())
        initSeatTableLayout(movie, reservationDetail, savedInstanceState)
    }

    private fun initPrice(priceViewData: PriceViewData) {
        presenter.initPrice(priceViewData)
    }

    private fun initSeatTableLayout(
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData,
        savedInstanceState: Bundle?,
    ) {
        initReserveButton(seatTableLayout, movie, reservationDetail)
        makeBackButton()

        seatTableLayout.onSelectSeat = {
            onSelectSeat(it, reservationDetail)
        }

        seatTableLayout.seatSelectCondition = { seatsSize ->
            seatsSize < reservationDetail.peopleCount
        }
        restoreCheckedSeats(savedInstanceState)
    }

    private fun restoreCheckedSeats(savedInstanceState: Bundle?) {
        val seatsViewData: SeatsViewData = saveStateSeats.load(savedInstanceState)
        seatsViewData.seats.forEach {
            seatTableLayout.findSeatViewByRowAndColumn(it.row, it.column)?.callOnClick()
        }
    }

    private fun makeBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onSelectSeat(seats: SeatsViewData, reservationDetail: ReservationDetailViewData) {
        presenter.updateSeatsPrice(seats, reservationDetail)
        setReservationButtonState(seats.seats.size, reservationDetail.peopleCount)
    }

    override fun setPriceView(price: String) {
        binding.seatSelectionMoviePrice.text = price
    }

    override fun makeAlarm(alarmDate: LocalDateTime, reservation: ReservationViewData) {
        AlarmMaker.make(alarmDate, reservation, this)
    }

    private fun setReservationButtonState(
        seatsSize: Int,
        peopleCount: Int,
    ) {
        binding.seatSelectionReserveButton.isEnabled = seatsSize == peopleCount
    }

    private fun initReserveButton(
        seatTableLayout: SeatTableLayout,
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData,
    ) {
        binding.seatSelectionReserveButton.setOnClickListener {
            onClickReserveButton(seatTableLayout, movie, reservationDetail)
        }
        setReservationButtonState(DEFAULT_SEAT_SIZE, reservationDetail.peopleCount)
    }

    private fun onClickReserveButton(
        seatTableLayout: SeatTableLayout,
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData,
    ) {
        AlertDialog.Builder(this).setTitle(getString(R.string.seat_selection_alert_title))
            .setMessage(getString(R.string.seat_selection_alert_message))
            .setPositiveButton(getString(R.string.seat_selection_alert_positive)) { _, _ ->
                reserveMovie(seatTableLayout, movie, reservationDetail)
            }.setNegativeButton(getString(R.string.seat_selection_alert_negative)) { dialog, _ ->
                dialog.dismiss()
            }.setCancelable(false).show()
    }

    private fun reserveMovie(
        seatTableLayout: SeatTableLayout,
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData,
    ) {
        val seats = seatTableLayout.selectedSeats()
        presenter.reserveMovie(seats, movie, reservationDetail)
    }

    override fun startReservationResultActivity(
        reservation: ReservationViewData,
    ) {
        ReservationResultActivity.from(
            this,
            reservation,
        ).run {
            startActivity(this)
        }
    }

    private fun initMovieView(movie: MovieViewData) {
        binding.seatSelectionMovieTitle.text = movie.title
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveStateSeats.save(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ACTION_ALARM = "actionAlarm"

        private const val SEAT_ROW_COUNT = 5
        private const val SEAT_COLUMN_COUNT = 4
        private const val DEFAULT_SEAT_SIZE = 0
        private const val SEAT_TABLE_LAYOUT_STATE_KEY = "seatTable"

        fun from(
            context: Context,
            movie: MovieViewData,
            reservationDetailViewData: ReservationDetailViewData,
        ): Intent {
            return Intent(context, SeatSelectionActivity::class.java).apply {
                putExtra(MovieViewData.MOVIE_EXTRA_NAME, movie)
                putExtra(
                    ReservationDetailViewData.RESERVATION_DETAIL_EXTRA_NAME,
                    reservationDetailViewData,
                )
            }
        }
    }
}
