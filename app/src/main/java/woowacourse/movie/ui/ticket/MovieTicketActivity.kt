package woowacourse.movie.ui.ticket

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTicketBinding
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.utils.failLoadingData
import woowacourse.movie.utils.getSerializableExtraCompat
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MovieTicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieTicketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTicketInfo()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setTicketInfo() {
        val ticket: MovieTicketModel = intent.getSerializableExtraCompat(KEY_TICKET)
            ?: return failLoadingData()

        with(binding) {
            ticketTitle.text = ticket.title
            ticketDate.text = ticket.time.format()
            ticketPeopleCount.text = getString(R.string.people_count, ticket.peopleCount.count)
            ticketSeats.text = getString(R.string.seats_with_separator, ticket.seats)
            ticketPrice.text = getString(
                R.string.price_with_payment,
                DecimalFormat("#,###").format(ticket.seats.toDomain().getAllPrice(ticket.time)),
            )
        }
    }

    private fun LocalDateTime.format(): String =
        format(DateTimeFormatter.ofPattern(getString(R.string.date_time_format)))

    companion object {
        private const val KEY_TICKET = "TICKET"
    }
}
