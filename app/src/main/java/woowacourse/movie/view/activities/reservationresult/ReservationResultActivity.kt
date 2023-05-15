package woowacourse.movie.view.activities.reservationresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import woowacourse.movie.R
import woowacourse.movie.data.reservation.ReservationDbHelper
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.view.activities.common.BackButtonActivity
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

class ReservationResultActivity : BackButtonActivity(), ReservationResultContract.View {

    private val binding: ActivityReservationResultBinding by lazy {
        ActivityReservationResultBinding.inflate(layoutInflater)
    }

    private val presenter: ReservationResultContract.Presenter by lazy {
        ReservationResultPresenter(
            this,
            intent.getLongExtra(RESERVATION_ID, -1),
            ReservationRepositoryImpl(ReservationDbHelper.getDbInstance(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter.loadReservation()
    }

    override fun setReservation(reservationUIState: ReservationUIState) {
        binding.movieTitleTv.text = reservationUIState.movieTitle

        binding.screeningDateTimeTv.text =
            DATE_TIME_FORMATTER.format(reservationUIState.screeningDateTime)

        binding.audienceCountSeatNamesTv.text = getString(R.string.reservation_detail_infos)
            .format(
                getString(R.string.general_person),
                reservationUIState.audienceCount,
                reservationUIState.seatNames,
                reservationUIState.theaterName
            )

        binding.reservationFeeTv.text =
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
