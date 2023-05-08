package woowacourse.movie.seatSelection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.common.error.ActivityError.finishWithError
import woowacourse.movie.common.error.ViewError
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.model.PriceViewData
import woowacourse.movie.common.model.ReservationDetailViewData
import woowacourse.movie.common.model.ReservationViewData
import woowacourse.movie.common.model.SeatTableViewData
import woowacourse.movie.common.model.TheaterViewData
import woowacourse.movie.common.system.App
import woowacourse.movie.common.system.BroadcastAlarm.registerAlarmReceiver
import woowacourse.movie.common.system.BroadcastAlarm.setAlarmAtDate
import woowacourse.movie.common.system.ReservationAlarmReceiver
import woowacourse.movie.common.system.getSerializableCompat
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.reservationResult.ReservationResultActivity
import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.Locale

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {
    override lateinit var presenter: SeatSelectionContract.Presenter
    private lateinit var binding: ActivitySeatSelectionBinding

    private lateinit var seatTableLayout: SeatTableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_selection)
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
        val theaterName =
            intent.extras?.getSerializableCompat<String>(TheaterViewData.THEATER_EXTRA_NAME)
                ?: return finishWithError(ViewError.MissingExtras(TheaterViewData.THEATER_EXTRA_NAME))

        presenter = SeatSelectionPresenter(
            this, movie = movie, reservationDetail = reservationDetail, movieDao = App.movieDao
        )
        seatTableLayout.load(savedInstanceState)
        initReserveButton(seatTableLayout, movie, reservationDetail, theaterName)
    }

    private fun initReserveButton(
        seatTableLayout: SeatTableLayout,
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData,
        theaterName: String
    ) {
        binding.seatSelectionReserveButton.setOnClickListener {
            onClickReserveButton(seatTableLayout, movie, reservationDetail, theaterName)
        }
        setReservationButtonState(
            seatTableLayout.selectedSeats().value.size, reservationDetail.peopleCount
        )
    }

    private fun onClickReserveButton(
        seatTableLayout: SeatTableLayout,
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData,
        theaterName: String
    ) {
        AlertDialog.Builder(this).setTitle(getString(R.string.seat_selection_alert_title))
            .setMessage(getString(R.string.seat_selection_alert_message))
            .setPositiveButton(getString(R.string.seat_selection_alert_positive)) { _, _ ->
                reserveMovie(seatTableLayout, movie, reservationDetail, theaterName)
            }.setNegativeButton(getString(R.string.seat_selection_alert_negative)) { dialog, _ ->
                dialog.dismiss()
            }.setCancelable(false).show()
    }

    private fun reserveMovie(
        seatTableLayout: SeatTableLayout,
        movie: MovieViewData,
        reservationDetail: ReservationDetailViewData,
        theaterName: String
    ) {
        val seats = seatTableLayout.selectedSeats()
        presenter.confirmSeats(movie, reservationDetail, seats, theaterName)
    }

    override fun setReservationButtonState(seatsSize: Int, peopleCount: Int) {
        binding.seatSelectionReserveButton.isEnabled = seatsSize == peopleCount
    }

    override fun setPriceText(price: PriceViewData) {
        val formattedPrice = NumberFormat.getNumberInstance(Locale.US).format(price.value)
        binding.seatSelectionMoviePrice.text = getString(R.string.seat_price, formattedPrice)
    }

    override fun makeReservationAlarm(
        reservation: ReservationViewData,
        date: LocalDateTime
    ) {
        registerAlarmReceiver(
            this, ReservationAlarmReceiver(), ReservationAlarmReceiver.ACTION_ALARM
        )

        val alarmIntent = ReservationAlarmReceiver.from(this, reservation)
        setAlarmAtDate(this, date, alarmIntent)
    }

    override fun setMovieData(movie: MovieViewData) {
        binding.seatSelectionMovieTitle.text = movie.title
    }

    override fun makeSeatLayout(
        reservationDetail: ReservationDetailViewData,
        seatTable: SeatTableViewData
    ) {
        seatTableLayout = SeatTableLayout.from(
            binding.seatSelectionTable, seatTable, SEAT_TABLE_LAYOUT_STATE_KEY
        )

        seatTableLayout.onSelectSeat = {
            presenter.selectSeat(it, reservationDetail)
        }

        seatTableLayout.seatSelectCondition = { seatsSize ->
            seatsSize < reservationDetail.peopleCount
        }
    }

    override fun startReservationResultActivity(
        reservation: ReservationViewData
    ) {
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
        private const val SEAT_TABLE_LAYOUT_STATE_KEY = "seatTable"

        fun from(
            context: Context,
            movie: MovieViewData,
            reservationDetailViewData: ReservationDetailViewData,
            theaterName: String
        ): Intent {
            return Intent(context, SeatSelectionActivity::class.java).apply {
                putExtra(MovieViewData.MOVIE_EXTRA_NAME, movie)
                putExtra(
                    ReservationDetailViewData.RESERVATION_DETAIL_EXTRA_NAME,
                    reservationDetailViewData
                )
                putExtra(TheaterViewData.THEATER_EXTRA_NAME, theaterName)
            }
        }
    }
}
