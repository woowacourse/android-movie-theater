package woowacourse.movie.view.reservation.complete

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.view.Extras
import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.getParcelableExtraCompat
import woowacourse.movie.view.model.ReservationInfoUiModel
import woowacourse.movie.view.model.SeatsUiModel
import java.time.LocalDateTime

@BindingAdapter("dateTime")
fun setDateTime(
    view: TextView,
    dateTime: LocalDateTime,
) {
    val context = view.context
    val date = dateTime.toLocalDate()
    val time = dateTime.toLocalTime()

    val formatted =
        context.getString(
            R.string.reservation_complete_date_time,
            ReservationUiFormatter.localDateToUI(date),
            time,
        )
    view.text = formatted
}

@BindingAdapter(value = ["seats", "theaterName"])
fun setSeatInfo(
    view: TextView,
    seats: SeatsUiModel,
    theaterName: String,
) {
    val context = view.context
    val seatCount = seats.size
    val seatLabels = seats.labels().sorted().joinToString()

    val formatted =
        context.getString(
            R.string.reservation_complete_ticket_count,
            seatCount,
            seatLabels,
            theaterName,
        )
    view.text = formatted
}

@BindingAdapter("reservationCompleteTotalPrice")
fun setTotalPrice(
    view: TextView,
    totalPrice: Int,
) {
    val context = view.context
    val formatted =
        context
            .getString(R.string.reservation_complete_ticket_price)
            .format(ReservationUiFormatter.priceToUI(totalPrice))
    view.text = formatted
}

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

        val reservationInfoUiModel =
            intent?.getParcelableExtraCompat<ReservationInfoUiModel>(Extras.ReservationInfoData.RESERVATION_KEY)
        presenter.fetchData(reservationInfoUiModel)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showErrorDialog() {
    }

    override fun showReservationInfo(reservationInfoUiModel: ReservationInfoUiModel) {
        binding.reservationInfo = reservationInfoUiModel
    }
}
