package woowacourse.movie.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.contract.SeatSelectionContract
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.PriceViewData
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.data.SeatTableViewData
import woowacourse.movie.error.ActivityError.finishWithError
import woowacourse.movie.error.ViewError
import woowacourse.movie.presenter.SeatSelectionPresenter
import woowacourse.movie.system.BroadcastAlarm.registerAlarmReceiver
import woowacourse.movie.system.BroadcastAlarm.setAlarmAtDate
import woowacourse.movie.system.ReservationAlarmReceiver
import woowacourse.movie.system.getSerializableCompat
import woowacourse.movie.view.widget.SeatTableLayout
import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.Locale

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {
    override val presenter: SeatSelectionContract.Presenter = SeatSelectionPresenter(this)

    private val priceText: TextView by lazy {
        findViewById(R.id.seat_selection_movie_price)
    }

    private val reservationButton: Button by lazy {
        findViewById(R.id.seat_selection_reserve_button)
    }

    private lateinit var seatTableLayout: SeatTableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)
        makeBackButton()

        initSeatSelectionView(savedInstanceState)
    }

    private fun makeBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initSeatSelectionView(savedInstanceState: Bundle?) {
        val movie =
            intent.extras?.getSerializableCompat<MovieViewData>(MovieViewData.MOVIE_EXTRA_NAME)
                ?: return finishWithError(ViewError.MissingExtras(MovieViewData.MOVIE_EXTRA_NAME))
        val reservationDetail = intent.extras?.getSerializableCompat<ReservationDetailViewData>(
            ReservationDetailViewData.RESERVATION_DETAIL_EXTRA_NAME
        )
            ?: return finishWithError(ViewError.MissingExtras(ReservationDetailViewData.RESERVATION_DETAIL_EXTRA_NAME))

        presenter.initActivity(movie, reservationDetail)
        seatTableLayout.load(savedInstanceState)
        initReserveButton(seatTableLayout, movie, reservationDetail)
    }

    private fun initReserveButton(
        seatTableLayout: SeatTableLayout,
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData
    ) {
        reservationButton.setOnClickListener {
            onClickReserveButton(seatTableLayout, movie, reservationDetail)
        }
        setReservationButtonState(DEFAULT_SEAT_SIZE, reservationDetail.peopleCount)
    }

    private fun onClickReserveButton(
        seatTableLayout: SeatTableLayout,
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData
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
        reservationDetail: ReservationDetailViewData
    ) {
        val seats = seatTableLayout.selectedSeats()
        presenter.confirmSeats(movie, reservationDetail, seats)
    }

    override fun setReservationButtonState(seatsSize: Int, peopleCount: Int) {
        reservationButton.isEnabled = seatsSize == peopleCount
    }

    override fun setPriceText(price: PriceViewData) {
        val formattedPrice = NumberFormat.getNumberInstance(Locale.US).format(price.value)
        priceText.text = getString(R.string.seat_price, formattedPrice)
    }

    override fun makeReservationAlarm(
        reservation: ReservationViewData,
        date: LocalDateTime
    ) {
        registerAlarmReceiver(ReservationAlarmReceiver(), ReservationAlarmReceiver.ACTION_ALARM)

        val alarmIntent = ReservationAlarmReceiver.from(this, reservation)
        setAlarmAtDate(date, alarmIntent)
    }

    override fun setMovieData(movie: MovieViewData) {
        findViewById<TextView>(R.id.seat_selection_movie_title).text = movie.title
    }

    override fun makeSeatLayout(reservationDetail: ReservationDetailViewData, seatTable: SeatTableViewData) {
        seatTableLayout = SeatTableLayout.from(
            findViewById(R.id.seat_selection_table),
            seatTable,
            SEAT_TABLE_LAYOUT_STATE_KEY
        )

        seatTableLayout.onSelectSeat = {
            presenter.selectSeat(it, reservationDetail)
        }

        seatTableLayout.seatSelectCondition = { seatsSize ->
            seatsSize < reservationDetail.peopleCount
        }
    }

    override fun startReservationResultActivity(reservation: ReservationViewData) {
        ReservationResultActivity.from(this, reservation).run {
            startActivity(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        seatTableLayout.save(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val DEFAULT_SEAT_SIZE = 0
        private const val SEAT_TABLE_LAYOUT_STATE_KEY = "seatTable"

        fun from(
            context: Context,
            movie: MovieViewData,
            reservationDetailViewData: ReservationDetailViewData
        ): Intent {
            return Intent(context, SeatSelectionActivity::class.java).apply {
                putExtra(MovieViewData.MOVIE_EXTRA_NAME, movie)
                putExtra(
                    ReservationDetailViewData.RESERVATION_DETAIL_EXTRA_NAME,
                    reservationDetailViewData
                )
            }
        }
    }
}
