package woowacourse.movie.booking.complete

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.data.ReservationDatabase
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.mapper.IntentCompat
import woowacourse.movie.ui.model.TicketUiModel

class BookingCompleteActivity : AppCompatActivity(), BookingCompleteContract.View {
    private lateinit var binding: ActivityBookingCompleteBinding
    private lateinit var presenter: BookingCompleteContract.Presenter
    private lateinit var bookingType: String

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_complete)
        setUpUi()
        setupPresenter()

        val ticket = requireTicketOrFinish()
        bookingType = intent.getStringExtra(KEY_BOOKING_TYPE).toString()

        if (ticket == null) {
            showToastErrorAndFinish(getString(R.string.booking_toast_message))
        } else {
            presenter.initializeData(ticket)
            presenter.saveReservation(ticket, bookingType)
            presenter.setNotification(ticket, bookingType)
        }

        onBackPressedDispatcher.addCallback(this, callback)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupPresenter() {
        val db = ReservationDatabase.getInstance(applicationContext)
        val alarmScheduler = MovieAlarmScheduler(this)
        presenter = BookingCompletePresenter(this, db!!.reservationDao(), alarmScheduler)
    }

    private fun requireTicketOrFinish(): TicketUiModel? {
        return IntentCompat.getParcelableExtra(
            intent,
            KEY_BOOKING_RESULT,
            TicketUiModel::class.java,
        )
    }

    override fun showBookingCompleteResult(ticket: TicketUiModel) {
        binding.ticket = ticket
    }

    override fun showToastErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                handleBackAction()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private val callback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackAction()
            }
        }

    private fun handleBackAction() {
        if (bookingType == BookingType.HISTORY.name) {
            finish()
        } else {
            val intent =
                Intent(this, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            startActivity(intent)
        }
    }

    companion object {
        private const val KEY_BOOKING_RESULT = "bookingResult"
        private const val KEY_BOOKING_TYPE = "bookingType"

        fun createIntent(
            context: Context,
            bookingType: BookingType,
            ticket: TicketUiModel,
        ): Intent {
            return Intent(context, BookingCompleteActivity::class.java).apply {
                putExtra(KEY_BOOKING_RESULT, ticket)
                putExtra(KEY_BOOKING_TYPE, bookingType.name)
            }
        }
    }
}
