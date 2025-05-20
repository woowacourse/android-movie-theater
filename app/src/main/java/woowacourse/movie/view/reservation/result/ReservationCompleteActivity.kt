package woowacourse.movie.view.reservation.result

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.BindingAdapter
import woowacourse.movie.data.mapper.toDomain
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.view.dialog.DialogFactory
import woowacourse.movie.view.reservation.TicketUi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationCompleteActivity : AppCompatActivity(), ReservationCompleteContract.View {
    private val presenter by lazy {
        ReservationCompletePresenter(this)
    }

    private var _binding: ActivityReservationCompleteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReservationCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootLayoutReservationComplete) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ticket =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(KEY_TICKET, TicketUi::class.java)
            } else {
                intent.getSerializableExtra(KEY_TICKET) as? TicketUi
            }

        checkTicket(ticket)
    }

    private fun checkTicket(ticketUi: TicketUi?) {
        if (ticketUi == null) {
            handleInvalidTicket()
        } else {
            presenter.fetchData(ticketUi.toDomain())
        }
    }

    override fun handleInvalidTicket() {
        DialogFactory().showError(this) {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showTicketInfo(ticketUi: TicketUi) {
        binding.ticket = ticketUi
    }

    override fun showSeatsInfo(seats: String) {
        binding.seats = seats
    }

    override fun showTicketMoney(moviePrice: Int) {
        binding.price = moviePrice
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val KEY_TICKET = "ticket"

        fun newIntent(
            context: Context,
            ticketUi: TicketUi,
        ): Intent =
            Intent(context, ReservationCompleteActivity::class.java)
                .putExtra(KEY_TICKET, ticketUi)
    }
}

@BindingAdapter("dateTime")
fun setDateTime(
    view: TextView,
    date: LocalDateTime,
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy.M.d. HH:mm")
    val dateTimeFormat = date.format(formatter)
    view.text = dateTimeFormat.toString()
}
