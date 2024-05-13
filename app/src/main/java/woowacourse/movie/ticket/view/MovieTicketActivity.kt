package woowacourse.movie.ticket.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.alarm.MovieBroadcastReceiver
import woowacourse.movie.databinding.ActivityMovieTicketBinding
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.TicketEntity
import woowacourse.movie.ticket.presenter.MovieTicketPresenter
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
        presenter.storeTicketData(
            intent.getSerializableExtra(EXTRA_TICKET_KEY),
            intent.getLongExtra(EXTRA_MOVIE_ID_KEY, 0),
        )
    }

    override fun showTicketView(ticketEntity: TicketEntity) {
        binding.ticketTitle.text = ticketEntity.movieTitle
        binding.ticketScreeningDate.text = ticketEntity.screeningDate
        binding.ticketScreeningTime.text = ticketEntity.screeningTime
        binding.ticketReservationInformation.text =
            getString(
                R.string.ticket_information_format,
                ticketEntity.seatsCount,
                ticketEntity.seats,
                ticketEntity.theaterName,
            )
        binding.ticketPrice.text = TICKET_PRICE.format(ticketEntity.price)
    }

    override fun makeAlarm(ticket: TicketEntity) {
        val alarmDate =
            LocalDate.parse(ticket.screeningDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        val alarmTime =
            LocalTime.parse(ticket.screeningTime, DateTimeFormatter.ofPattern("HH:mm"))
                .minusMinutes(30)
        makeAlarmAtTime(alarmDate, alarmTime, ticket.movieTitle, ticket.id)
        Log.d("alsong", "ticketId: ${ticket.id}")
    }

    private fun makeAlarmAtTime(date: LocalDate, time: LocalTime, movieTitle: String, id: Long) {
        val intent = Intent(this, MovieBroadcastReceiver::class.java).apply {
            putExtra(EXTRA_MOVIE_TITLE_KEY, movieTitle)
            putExtra(EXTRA_MOVIE_ID_KEY, id)
        }
        val calendar = setCalendar(date, time)
        if (System.currentTimeMillis() > calendar.timeInMillis) return
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
    }

    private fun setCalendar(date: LocalDate, time: LocalTime): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, date.year)
            set(Calendar.MONTH, date.monthValue - 1)
            set(Calendar.DATE, date.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
            set(Calendar.SECOND, 0)
        }
    }

    companion object {
        private const val TICKET_PRICE = "%,d원 (현장결제)"
        private const val EXTRA_TICKET_KEY = "ticket_key"
        const val EXTRA_MOVIE_TITLE_KEY = "movie_title_key"
        const val EXTRA_MOVIE_ID_KEY = "movie_id_key"

        fun newTicketActivityInstance(context: Context, id: Long): Intent {
            return Intent(context, MovieTicketActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID_KEY, id)
            }
        }
    }
}
