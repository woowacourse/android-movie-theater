package woowacourse.movie.view.activities.reservationresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.data.reservation.ReservationDbHelper
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.view.activities.common.BackButtonActivity
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

class ReservationResultActivity : BackButtonActivity(), ReservationResultContract.View {

    private val presenter: ReservationResultContract.Presenter by lazy {
        ReservationResultPresenter(
            this,
            intent.getLongExtra(RESERVATION_ID, -1),
            ReservationRepositoryImpl(ReservationDbHelper.getDbInstance(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_result)

        presenter.loadReservation()
    }

    override fun setReservation(reservationUIState: ReservationUIState) {
        val movieTitleView = findViewById<TextView>(R.id.movie_title_tv)
        movieTitleView.text = reservationUIState.movieTitle

        val screeningDateTimeView = findViewById<TextView>(R.id.screening_date_time_tv)
        screeningDateTimeView.text =
            DATE_TIME_FORMATTER.format(reservationUIState.screeningDateTime)

        val audienceCountSeatNamesView = findViewById<TextView>(R.id.audience_count_seat_names_tv)
        audienceCountSeatNamesView.text = getString(R.string.reservation_people_count_format)
            .format(
                getString(R.string.general_person),
                reservationUIState.audienceCount,
                reservationUIState.seatNames
            )

        val reservationFeeView = findViewById<TextView>(R.id.reservation_fee_tv)
        reservationFeeView.text =
            getString(R.string.total_price_format).format(DECIMAL_FORMAT.format(reservationUIState.reservationFee))
    }

    companion object {
        const val RESERVATION_ID = "RESERVATION_ID"
        private val DATE_TIME_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
        val DECIMAL_FORMAT = DecimalFormat("#,###")

        fun startActivity(context: Context, reservationId: Long) {
            val intent = Intent(context, ReservationResultActivity::class.java).apply {
                putExtra(RESERVATION_ID, reservationId)
            }
            context.startActivity(intent)
        }
    }
}
