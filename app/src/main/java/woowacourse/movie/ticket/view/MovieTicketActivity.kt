package woowacourse.movie.ticket.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.alarm.MovieBroadcastReceiver
import woowacourse.movie.database.TicketDatabase.Companion.getDatabase
import woowacourse.movie.databinding.ActivityMovieTicketBinding
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.DbTicket
import woowacourse.movie.ticket.presenter.MovieTicketPresenter
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

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
        presenter.storeTicketData(intent.getSerializableExtra(EXTRA_TICKET_KEY))
        presenter.setTicketInfo()
        presenter.storeTicketInDb()
    }

    override fun showTicketView(dbTicket: DbTicket) {
        binding.ticketTitle.text = dbTicket.movieTitle
        binding.ticketScreeningDate.text = dbTicket.screeningDate
        binding.ticketScreeningTime.text = dbTicket.screeningTime
        binding.ticketReservationInformation.text =
            getString(
                R.string.ticket_information_format,
                dbTicket.seatsCount,
                dbTicket.seats,
                dbTicket.theaterName,
            )
        binding.ticketPrice.text = TICKET_PRICE.format(dbTicket.price)
    }

    override fun storeTicketInDb(ticket: DbTicket) {
        val ticketDb = getDatabase(applicationContext)
        Thread {
            ticketDb.ticketDao().insertAll(ticket)
        }.start()

        val alarmDate =
            LocalDate.parse(ticket.screeningDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        val alarmTime =
            LocalTime.parse(ticket.screeningTime, DateTimeFormatter.ofPattern("HH:mm"))
                .minusMinutes(30)
        makeAlarmAtTime(alarmDate, alarmTime, ticket.movieTitle)
    }

    private fun makeAlarmAtTime(date: LocalDate, time: LocalTime, movieTitle: String) {
        val intent = Intent(this, MovieBroadcastReceiver::class.java).apply {
            putExtra(EXTRA_MOVIE_TITLE_KEY, movieTitle)
        }
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.YEAR, date.year)
            set(Calendar.MONTH, date.monthValue - 1)
            set(Calendar.DATE, date.dayOfMonth)
            set(Calendar.HOUR, time.hour)
            set(Calendar.MINUTE, time.minute)
        }
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
    }

    companion object {
        private const val TICKET_PRICE = "%,d원 (현장결제)"
        private const val EXTRA_TICKET_KEY = "ticket_key"
        const val EXTRA_MOVIE_TITLE_KEY = "movie_title_key"

        fun newTicketActivityInstance(context: Context, tickets: List<DbTicket>, id: Long): Intent {
            return Intent(context, MovieTicketActivity::class.java).apply {
                putExtra(EXTRA_TICKET_KEY, tickets[id.toInt() - 1] as Serializable)
            }
        }
    }
}
