package woowacourse.movie.presentation.bookingsummary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingsummaryBinding
import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.ui.DataBindingBaseActivity
import woowacourse.movie.ui.util.getSerializableExtraCompat

class BookingSummaryActivity :
    DataBindingBaseActivity(),
    BookingSummaryContract.View {
    private val binding by binding<ActivityBookingsummaryBinding>(R.layout.activity_bookingsummary)
    private val presenter: BookingSummaryPresenter by lazy { BookingSummaryPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!fetchTicketFromIntent()) return
        setupScreen(binding.root)
    }

    override fun showTicket(ticket: MovieTicket) {
        binding.ticket = ticket
    }

    override fun showCancelableTime(cancelableTime: Int) {
        binding.textviewNotice.text = String.format(getString(R.string.cancel_notice), cancelableTime)
    }

    private fun fetchTicketFromIntent(): Boolean {
        val data = intent.getSerializableExtraCompat(BOOKING_SUMMARY_KEY, MovieTicket::class.java)
        if (data == null) {
            Toast.makeText(this, getString(R.string.ticket_intent_error), Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        presenter.initializeBookingSummary(data)
        return true
    }

    companion object {
        private const val BOOKING_SUMMARY_KEY = "BookingSummary"

        fun newIntent(context: Context, ticket: MovieTicket): Intent {
            return Intent(context, BookingSummaryActivity::class.java).apply {
                putExtra(BOOKING_SUMMARY_KEY, ticket)
            }
        }
    }
}
