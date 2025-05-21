package woowacourse.movie.presentation.ticket.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityTicketDetailBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.presentation.BaseActivity
import woowacourse.movie.util.getSerializableExtraCompat

class TicketDetailActivity :
    BaseActivity<ActivityTicketDetailBinding>(R.layout.activity_ticket_detail),
    TicketDetailContract.View {
    private lateinit var presenter: TicketDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ticket = intent.getSerializableExtraCompat(EXTRA_TICKET, Ticket::class.java)
        ticket ?: run {
            Toast.makeText(this, ERROR_INTENT_KEY, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        presenter = TicketDetailPresenter(this, ticket)
        presenter.loadBookingResult()
    }

    override fun showTicketInfo(ticket: Ticket) {
        binding.ticket = ticket
    }

    companion object {
        fun newIntent(
            context: Context?,
            ticket: Ticket,
        ): Intent =
            Intent(context, TicketDetailActivity::class.java).apply {
                putExtra(EXTRA_TICKET, ticket)
            }

        private const val EXTRA_TICKET = "ticket"
        private const val ERROR_INTENT_KEY = "[ERROR] 키 값이 올바르지 않습니다."
    }
}
