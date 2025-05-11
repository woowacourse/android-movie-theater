package woowacourse.movie.moviebookingseat

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import woowacourse.movie.NotificationReceiver
import woowacourse.movie.R
import woowacourse.movie.data.MovieApplication
import woowacourse.movie.databinding.MovieBookingSeatBinding
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.helper.BuildVersion
import woowacourse.movie.helper.CustomClickListenerHelper.setOnSingleClickListener
import woowacourse.movie.moviebooked.MovieBookedActivity
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread

class MovieBookingSeatActivity : AppCompatActivity(), MovieBookingSeat.View {
    private lateinit var binding: MovieBookingSeatBinding
    private lateinit var presenter: MovieBookingSeatPresenter
    private lateinit var bookingStatus: BookingStatus
    private lateinit var theater: Theater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initBinding()
        applyWindowInserts()
        setUpIntentData()
        setUpPresenter()
        initSeatTable()
    }

    override fun showBookingStatusInfo() {
        binding.bookingStatus = bookingStatus
    }

    override fun updateButton() {
        binding.seatConfirmButton.setBackgroundResource(R.color.purple_500)
        binding.seatConfirmButton.setOnSingleClickListener { presenter.confirmBooking(applicationContext) }
    }

    override fun updateSeat(
        seat: Seat,
        isSelected: Boolean,
    ) {
        val seatTextView: TextView = binding.seatTable.findViewWithTag(seat)
        seatTextView.setBackgroundResource(if (isSelected) R.color.yellow else R.drawable.seat_background)
        presenter.calculatePrice()
    }

    override fun showTotalPrice(price: Int) {
        binding.seatMoviePrice.text =
            binding.seatMoviePrice.context.getString(
                R.string.booking_seat_price,
                price,
            )
    }

    override fun showConfirmDialog(id: Long) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.check_movie_booking))
            .setMessage(getString(R.string.confirm_reservation_message))
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(getString(R.string.okay)) { _, _ ->
                setUpNotification(id)
                navigateToMovieBooked(id)
            }
            .show()
            .setCancelable(false)
    }

    override fun navigateToMovieBooked(id: Long) {
        val intent =
            MovieBookedActivity.newIntent(
                this@MovieBookingSeatActivity,
                id,
            )
        startActivity(intent)
        finish()
    }

    override fun showError(messageRes: Int) {
        AlertDialog.Builder(this)
            .setMessage(getString(messageRes))
            .setPositiveButton(R.string.error_dialog_okay, null)
            .show()
            .setCancelable(false)
    }

    override fun setUpNotification(id: Long) {
        val sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE)
        if (!sharedPref.getBoolean("notification", true)) return

        thread {
            val db = (applicationContext as MovieApplication).database
            val reservation = db.reservationDao().getById(id) ?: return@thread

            val intent = Intent(this, NotificationReceiver::class.java).apply {
                putExtra("reservationId", id)
                putExtra("title", reservation.title)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
            val dateTime = LocalDateTime.parse("${reservation.date} ${reservation.time}", formatter)
            val notifyTime = dateTime.minusMinutes(30).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                notifyTime,
                pendingIntent,
            )
        }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.movie_booking_seat)
    }

    private fun applyWindowInserts() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booking_seat)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpIntentData() {
        bookingStatus =
            BuildVersion().getParcelableClass(intent, KEY_BOOKING_SEAT, BookingStatus::class)
        theater = BuildVersion().getParcelableClass(intent, KEY_THEATER, Theater::class)
    }

    private fun setUpPresenter() {
        presenter = MovieBookingSeatPresenter(this@MovieBookingSeatActivity)
        presenter.loadBookingStatus(bookingStatus, theater)
    }

    private fun initSeatTable() {
        binding.seatTable.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<TextView>().forEachIndexed { colIndex, seatTextView ->
                val seat = Seat.of(rowIndex, colIndex)
                seatTextView.apply {
                    tag = seat
                    setOnClickListener {
                        presenter.selectSeat(seat)
                    }
                }
            }
        }
    }

    companion object {
        private const val KEY_BOOKING_SEAT = "bookingSeat"
        private const val KEY_THEATER = "theater"

        fun movieBookingSeatIntent(
            otherActivity: Context,
            bookingStatus: BookingStatus,
            theater: Theater,
        ): Intent {
            return Intent(otherActivity, MovieBookingSeatActivity::class.java)
                .apply {
                    putExtra(KEY_BOOKING_SEAT, bookingStatus)
                    putExtra(KEY_THEATER, theater)
                }
        }
    }
}
