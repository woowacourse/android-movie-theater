package woowacourse.movie.view.home.complete

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.notification.NotificationManagerImpl
import woowacourse.movie.view.notification.NotificationScheduler
import woowacourse.movie.view.util.StringFormatter
import woowacourse.movie.view.util.getSerializableCompat
import woowacourse.movie.view.util.showToast

class BookingCompleteActivity : AppCompatActivity(), BookingCompleteContract.View {
    private lateinit var binding: ActivityBookingCompleteBinding
    private lateinit var presenter: BookingCompleteContract.Presenter

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

        val ticket: Ticket? = intent.extras?.getSerializableCompat(KEY_TICKET)
        if (ticket == null) {
            showToast(getString(R.string.text_error))
            finish()
            return
        }

        initPresenter(ticket)
        presenter.loadTicket()

        val isNewBooking = intent.extras?.getBoolean(KEY_IS_NEW_BOOKING)
        if (isNewBooking == null) {
            showToast(getString(R.string.text_error))
            finish()
            return
        }
        if (isNewBooking) {
            presenter.addToHistory(ticket)
            presenter.decideNotification(ticket)
        }

        setBackAction()
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPresenter(ticket: Ticket) {
        val application = application as MovieApplication
        presenter =
            BookingCompletePresenter(
                this,
                application.ticketRepository,
                application.settingRepository,
                NotificationManagerImpl(this, NotificationScheduler()),
                ticket,
            )
    }

    override fun showTicket(ticket: Ticket) {
        val formattedSchedule =
            getString(R.string.text_booking_schedule).format(
                StringFormatter.dotDateFormat(ticket.screeningDate),
                ticket.screeningTime,
            )
        val formattedPrice = getString(R.string.text_on_site_payment).format(StringFormatter.thousandFormat(ticket.price))

        binding.tvTitle.text = ticket.movieTitle
        binding.tvSchedule.text = formattedSchedule
        binding.tvSeat.text = seatToLabel(ticket.seats)
        binding.tvTheaterName.text = ticket.theaterName
        binding.tvAdmissionCount.text = getString(R.string.text_general_people_count).format(ticket.count.value)
        binding.tvPrice.text = formattedPrice
    }

    private fun seatToLabel(seats: Set<Seat>): String {
        return seats.joinToString { seat ->
            val rowLetter = (ROW_STARTING_VALUE + seat.row.value)
            val columnNumber = COL_STARTING_VALUE + seat.col.value
            "$rowLetter$columnNumber"
        }
    }

    override fun isNotificationPermitted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionStatus =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                )
            permissionStatus == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    override fun notifyNoNotificationPermission() {
        this.showToast(getString(R.string.text_notification_permission_not_granted))
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

    private fun moveToHome() {
        val intent = Intent(this@BookingCompleteActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    companion object {
        const val KEY_TICKET = "ticket"
        private const val KEY_IS_NEW_BOOKING = "is_new_booking"

        private const val ROW_STARTING_VALUE = 'A'
        private const val COL_STARTING_VALUE = 1

        fun newIntent(
            context: Context,
            ticket: Ticket,
            isNewBooking: Boolean,
        ) = Intent(context, BookingCompleteActivity::class.java).apply {
            putExtra(KEY_TICKET, ticket)
            putExtra(KEY_IS_NEW_BOOKING, isNewBooking)
        }
    }
}
