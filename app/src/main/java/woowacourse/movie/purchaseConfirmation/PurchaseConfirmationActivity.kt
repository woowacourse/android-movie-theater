package woowacourse.movie.purchaseConfirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.base.BindingActivity
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.database.Ticket
import woowacourse.movie.databinding.ActivityPurchaseConfirmationBinding
import woowacourse.movie.error.ErrorActivity
import kotlin.concurrent.thread

class PurchaseConfirmationActivity :
    BindingActivity<ActivityPurchaseConfirmationBinding>(R.layout.activity_purchase_confirmation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ticketId = intent.getIntExtra(EXTRA_TICKET_ID, 0)
        if (ticketId == 0) {
            ErrorActivity.start(this)
            return
        }

        thread {
            try {
                val ticket =
                    AppDatabase.getInstance(applicationContext).ticketDao().getTicketById(ticketId)
                if (ticket != null) {
                    runOnUiThread {
                        updateUI(ticket)
                    }
                } else {
                    runOnUiThread {
                        showError()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    showError()
                }
            }
        }
    }

    private fun updateUI(ticket: Ticket) {
        binding.movieTitleConfirmation.text = ticket.movieTitle
        binding.purchaseMovieRunningTime.text = ticket.runningTime
        binding.reservedInformation.text =
            "일반 ${ticket.seatNumbers.split(",").size}명 | ${ticket.seatNumbers} | ${ticket.cinemaName}"
        binding.ticketCharge.text = ticket.ticketPrice.toString()
        binding.movieTimeDate.text = ticket.screeningDate
    }

    private fun showError() {
        Toast.makeText(this, "Failed to load ticket information.", Toast.LENGTH_LONG).show()
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
            ticketId: Int
        ): Intent {
            return Intent(context, PurchaseConfirmationActivity::class.java).apply {
                putExtra(EXTRA_TICKET_ID, ticketId)
            }
        }
    }
}
