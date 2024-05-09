package woowacourse.movie.ticket.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTicketBinding
import woowacourse.movie.list.model.TicketDatabase.Companion.getDatabase
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.DbTicket
import woowacourse.movie.ticket.presenter.MovieTicketPresenter
import java.io.Serializable

class MovieTicketActivity : AppCompatActivity(), MovieTicketContract.View {
    override val presenter: MovieTicketPresenter = MovieTicketPresenter(this)
    private lateinit var binding: ActivityMovieTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_ticket)
        binding.ticket = this
        setContentView(binding.root)
        processPresenterTask()
    }

    private fun processPresenterTask() {
        presenter.storeTicketData(intent.getSerializableExtra("ticket_key"))
        presenter.setTicketInfo()
        presenter.storeTicketInDb()
    }

    override fun storeTicketInDb(ticket: DbTicket) {
        val ticketDb = getDatabase(applicationContext)
        Thread {
            ticketDb.ticketDao().insertAll(ticket)
        }.start()
    }

    override fun showTicketView(
        movieTitle: String,
        screeningDate: String,
        screeningTime: String,
        seatsCount: Int,
        seats: String,
        theater: String,
        moviePrice: Int,
    ) {
        binding.ticketTitle.text = movieTitle
        binding.ticketScreeningDate.text = screeningDate
        binding.ticketScreeningTime.text = screeningTime
        binding.ticketReservationInformation.text =
            getString(
                R.string.ticket_information_format,
                seatsCount,
                seats,
                theater,
            )
        binding.ticketPrice.text = TICKET_PRICE.format(moviePrice)
    }

    companion object {
        private const val TICKET_PRICE = "%,d원 (현장결제)"

        fun newTicketActivityInstance(context: Context, tickets: List<DbTicket>, id: Long): Intent {
            return Intent(context, MovieTicketActivity::class.java).apply {
                putExtra("ticket_key", tickets[id.toInt() - 1] as Serializable)
            }
        }
    }
}
