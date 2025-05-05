package woowacourse.movie.presentation.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.common.util.getSerializableExtraCompat
import woowacourse.movie.databinding.ActivityBookingResultBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.presentation.BaseActivity

class BookingResultActivity :
    BaseActivity<ActivityBookingResultBinding>(R.layout.activity_booking_result),
    BookingResultContract.View {
    private lateinit var presenter: BookingResultContract.Presenter
    private lateinit var ticket: Ticket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!fetchTicketFromIntent()) return
        presenter = BookingResultPresenter(this, ticket)
        presenter.loadBookingResult()
    }

    override fun showTicketInfo(ticket: Ticket) {
        binding.ticket = ticket
    }

    private fun fetchTicketFromIntent(): Boolean {
        val data = intent.getSerializableExtraCompat(EXTRA_TICKET, Ticket::class.java)
        if (data == null) {
            Toast.makeText(this, ERROR_INTENT_KEY, Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        ticket = data
        return true
    }

    companion object {
        fun newIntent(
            context: Context,
            ticket: Ticket,
        ): Intent =
            Intent(context, BookingResultActivity::class.java).apply {
                putExtra(EXTRA_TICKET, ticket)
            }

        private const val EXTRA_TICKET = "ticket"
        private const val ERROR_INTENT_KEY = "[ERROR] 키 값이 올바르지 않습니다."
    }
}
