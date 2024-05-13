package woowacourse.movie.presentation.ticketingResult

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.databinding.ActivityTicketingResultBinding
import woowacourse.movie.model.Ticket
import woowacourse.movie.presentation.home.HomeActivity
import woowacourse.movie.repository.DummyTheaterList
import woowacourse.movie.repository.ReservationRepositoryImpl

class TicketingResultActivity() :
    AppCompatActivity(),
    TicketingResultContract.View {
    private lateinit var binding: ActivityTicketingResultBinding
    private val presenter: TicketingResultPresenter =
        TicketingResultPresenter(this, DummyTheaterList)

    // 부자연스럽게 작동
    private val onBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@TicketingResultActivity, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketingResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        Log.d("result crong", "${intent.extras}")
        Log.d("result crong", "${intent.getLongExtra(TICKET_ID, -1L)}")

        var movieTicket =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_MOVIE_TICKET, Ticket::class.java)
            } else {
                intent.getParcelableExtra(EXTRA_MOVIE_TICKET)
            }

        if (movieTicket == null) {
            // val ticketId = intent.getLongExtra(TICKET_ID, -1L)
            val ticketId = intent.extras?.getLong(TICKET_ID) ?: -1L
            // val ticketId = 1L
            movieTicket = presenter.loadTicket(ticketId, ReservationRepositoryImpl(this))
            // intent.extras?.getLong(TICKET_ID)

            Log.d("result crong", "$ticketId")
            Log.d("result crong", "$movieTicket")
        }

        if (movieTicket != null) {
            presenter.loadTicketInfo(movieTicket)
        } else {
            Toast.makeText(this, "무언가 잘못 되었습니다.", Toast.LENGTH_SHORT).show()
        }
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
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_MOVIE_TICKET = "movie_ticket"
        const val TICKET_ID = "ticket_id"

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
