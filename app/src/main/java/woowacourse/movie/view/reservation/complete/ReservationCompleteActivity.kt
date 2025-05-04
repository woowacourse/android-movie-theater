package woowacourse.movie.view.reservation.complete

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.view.Extras
import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.getParcelableExtraCompat

class ReservationCompleteActivity :
    AppCompatActivity(),
    ReservationCompleteContract.View {
    private lateinit var binding: ActivityReservationCompleteBinding
    private val presenter: ReservationCompletePresenter by lazy { ReservationCompletePresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_complete)
        ViewCompat.setOnApplyWindowInsetsListener(binding.reservationComplete) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val reservationInfo =
            intent?.getParcelableExtraCompat<ReservationInfo>(Extras.ReservationInfoData.RESERVATION_KEY)
        presenter.fetchData(reservationInfo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showErrorDialog() {
    }

    override fun showReservationInfo(reservationInfo: ReservationInfo) {
        binding.tvReservationCompleteTitle.text = reservationInfo.title
        binding.tvReservationCompleteTimestamp.text =
            resources.getString(
                R.string.reservation_complete_date_time,
                ReservationUiFormatter.localDateToUI(reservationInfo.date),
                reservationInfo.time,
            )
        binding.tvReservationCompleteCountSeats.text =
            resources.getString(
                R.string.reservation_complete_ticket_count,
                reservationInfo.seats.size,
                reservationInfo.seats
                    .labels()
                    .sorted()
                    .joinToString(),
                reservationInfo.theaterName,
            )
        binding.tvReservationCompleteTicketPrice.text =
            resources.getString(
                R.string.reservation_complete_ticket_price,
                ReservationUiFormatter.priceToUI(reservationInfo.price),
            )
    }
}
