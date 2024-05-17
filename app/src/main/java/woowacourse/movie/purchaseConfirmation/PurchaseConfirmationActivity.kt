package woowacourse.movie.purchaseConfirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.base.BindingActivity
import woowacourse.movie.database.Ticket
import woowacourse.movie.databinding.ActivityPurchaseConfirmationBinding

class PurchaseConfirmationActivity :
    BindingActivity<ActivityPurchaseConfirmationBinding>(R.layout.activity_purchase_confirmation),
    PurchaseConfirmationContract.View {
    private lateinit var presenter: PurchaseConfirmationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val ticketId = intent.getIntExtra(EXTRA_TICKET_ID, 0)

        presenter =
            PurchaseConfirmationPresenter(this, TicketModel(applicationContext))
        presenter.loadTicket(ticketId)
    }

    override fun showTicketInfo(ticket: Ticket) {
        runOnUiThread {
            binding.ticket = ticket
            binding.seatNumbersSize = ticket.seatNumbers.split(",").size
        }
    }

    override fun showError() {
        runOnUiThread {
            Toast.makeText(this, "Failed to load ticket information.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun navigateBack() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_TICKET_ID = "ticketId"

        fun newIntent(
            context: Context,
            ticketId: Int,
        ): Intent {
            return Intent(context, PurchaseConfirmationActivity::class.java).apply {
                putExtra(EXTRA_TICKET_ID, ticketId)
            }
        }
    }
}
