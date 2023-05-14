package woowacourse.movie.ui.activity

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTicketBinding
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.PriceModel
import woowacourse.movie.ui.model.TicketTimeModel
import woowacourse.movie.ui.model.seat.SeatModel
import woowacourse.movie.ui.utils.getParcelable
import java.time.format.DateTimeFormatter

class MovieTicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTicketInfo()

        setBackPressedCallback()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        setContentView(R.layout.activity_movie_ticket)
        super.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                return goBackToMainActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goBackToMainActivity(): Boolean {
        val intent = MainActivity.createIntent(this)
        intent.flags = FLAG_ACTIVITY_CLEAR_TOP + FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        return true
    }

    private fun setTicketInfo() {
        intent.getParcelable<MovieTicketModel>(TICKET_EXTRA_KEY)?.let { ticketModel ->
            binding.ticketTitle.text = ticketModel.title
            binding.ticketDate.text = ticketModel.time.format()
            binding.ticketReservedSeats.text =
                getString(
                    R.string.reserved_seat,
                    ticketModel.peopleCount.count,
                    ticketModel.seats.sortedBy { seat -> seat.format() }
                        .joinToString(", ") { seat ->
                            seat.format()
                        },
                    ticketModel.theaterName
                )
            binding.ticketPrice.text = ticketModel.price.format()
        }
    }

    private fun TicketTimeModel.format(): String =
        dateTime.format(DateTimeFormatter.ofPattern(getString(R.string.date_time_format)))

    private fun SeatModel.format(): String = getString(R.string.seat, row.letter, column.value)

    private fun PriceModel.format(): String = getString(R.string.price, amount)

    private fun setBackPressedCallback() {
        onBackPressedDispatcher.addCallback(this) {
            goBackToMainActivity()
        }
    }

    companion object {
        private const val TICKET_EXTRA_KEY = "ticket"

        fun createIntent(context: Context, ticket: MovieTicketModel): Intent {
            val intent = Intent(context, MovieTicketActivity::class.java)
            intent.putExtra(TICKET_EXTRA_KEY, ticket)
            return intent
        }
    }
}
