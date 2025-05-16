package woowacourse.movie.view.home.complete

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.data.setting.SettingStorageManagerImpl
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.home.seat.SeatActivity
import woowacourse.movie.view.notification.NotificationReceiver
import woowacourse.movie.view.util.StringFormatter
import woowacourse.movie.view.util.getSerializableCompat
import woowacourse.movie.view.util.showToast
import java.time.LocalDate
import java.time.LocalTime

class BookingCompleteActivity : AppCompatActivity(), BookingCompleteContract.View {
    private lateinit var binding: ActivityBookingCompleteBinding
    private lateinit var presenter: BookingCompleteContract.Presenter

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ticket: Ticket? = intent.extras?.getSerializableCompat(KEY_TICKET)
        if (ticket == null) {
            showToast(getString(R.string.text_error))
            finish()
            return
        }

        val repository = (application as MovieApplication).repository
        presenter = BookingCompletePresenter(this, repository, SettingStorageManagerImpl(this), ticket)
        presenter.loadTicket()

        val caller: Class<*>? = intent.extras?.getSerializableCompat(KEY_CALLER)
        if (caller == SeatActivity::class.java) {
            presenter.addToHistory(ticket)
            presenter.loadNotificationInfo(ticket)
        }

        initView()
        setBackAction()
    }

    override fun setNotification(
        ticket: Ticket,
        time: Long,
    ) {
        val pendingIntent =
            PendingIntent.getBroadcast(
                this,
                ticket.hashCode(),
                NotificationReceiver.newIntent(this, ticket),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent,
        )
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setBackAction() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    moveToHome()
                }
            },
        )
    }

    override fun showTicket(ticket: Ticket) {
        with(ticket) {
            initBookingMovieTitleView(movieTitle)
            initBookingScheduleView(screeningDate, screeningTime)
            initBookingSeatView(seats)
            initTheaterNameView(theaterName)
            initBookingPeopleCountView(count.value)
            initBookingTicketPriceView(price)
        }
    }

    private fun initBookingMovieTitleView(title: String) {
        binding.tvTitle.text = title
    }

    private fun initBookingScheduleView(
        bookingDate: LocalDate,
        bookingTime: LocalTime,
    ) {
        val formattedBookingDate = StringFormatter.dotDateFormat(bookingDate)
        val scheduleFormat =
            getString(R.string.text_booking_schedule).format(formattedBookingDate, bookingTime)

        binding.tvSchedule.text = scheduleFormat
    }

    private fun initBookingSeatView(seats: Set<Seat>) {
        binding.tvSeat.text = seatToLabel(seats)
    }

    private fun initTheaterNameView(theaterName: String) {
        binding.tvTheaterName.text = theaterName
    }

    private fun initBookingPeopleCountView(peopleCount: Int) {
        binding.tvAdmissionCount.text =
            getString(R.string.text_general_people_count).format(peopleCount)
    }

    private fun initBookingTicketPriceView(ticketPrice: Int) {
        val priceFormat = StringFormatter.thousandFormat(ticketPrice)
        binding.tvPrice.text =
            getString(R.string.text_on_site_payment).format(priceFormat)
    }

    private fun seatToLabel(seats: Set<Seat>): String {
        return seats.joinToString { seat ->
            val rowLetter = (ROW_STARTING_VALUE + seat.row.value)
            val columnNumber = COL_STARTING_VALUE + seat.col.value
            "$rowLetter$columnNumber"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                moveToHome()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveToHome() {
        val intent = Intent(this@BookingCompleteActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    companion object {
        const val KEY_TICKET = "ticket"
        private const val KEY_CALLER = "caller"

        private const val ROW_STARTING_VALUE = 'A'
        private const val COL_STARTING_VALUE = 1

        fun <T> newIntent(
            context: Context,
            ticket: Ticket,
            caller: Class<T>,
        ) = Intent(context, BookingCompleteActivity::class.java).apply {
            putExtra(KEY_TICKET, ticket)
            putExtra(KEY_CALLER, caller)
        }
    }
}
