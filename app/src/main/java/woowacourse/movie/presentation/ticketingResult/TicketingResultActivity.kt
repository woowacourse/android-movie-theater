package woowacourse.movie.presentation.ticketingResult

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityTicketingResultBinding
import woowacourse.movie.model.Ticket
import woowacourse.movie.repository.DummyTheaterList

class TicketingResultActivity : AppCompatActivity(), TicketingResultContract.View {
    private lateinit var binding: ActivityTicketingResultBinding
    private val presenter: TicketingResultPresenter = TicketingResultPresenter(this, DummyTheaterList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticketing_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityTicketingResultBinding.inflate(layoutInflater)

        val movieTicket =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_MOVIE_TICKET, Ticket::class.java)
            } else {
                intent.getParcelableExtra(EXTRA_MOVIE_TICKET)
            }
        presenter.loadTicketInfo(movieTicket)
    }

    override fun displayTicketInfo(
        ticket: Ticket,
        theaterName: String,
    ) {
        binding.ticket = ticket
        binding.theaterName = "$theaterName 극장"
    }

    override fun showToastMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_MOVIE_TICKET = "movie_ticket"

        fun createIntent(
            context: Context,
            movieTicket: Ticket,
        ): Intent {
            return Intent(context, TicketingResultActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_TICKET, movieTicket)
            }
        }
    }
}
