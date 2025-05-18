package woowacourse.movie.ui.seat

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingSeatBinding
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.notification.MovieReminderReceiver
import woowacourse.movie.providers.StorageProvider
import woowacourse.movie.ui.complete.BookingCompleteActivity
import woowacourse.movie.utils.AlarmManagerCompat
import woowacourse.movie.utils.StringFormatter
import woowacourse.movie.utils.intentSerializable
import java.time.LocalDateTime
import java.time.ZoneId

class BookingSeatActivity :
    AppCompatActivity(),
    BookingSeatContract.View {
    private val bookingSeatPresenter by lazy { BookingSeatPresenter(this) }
    private lateinit var binding: ActivityBookingSeatBinding
    private val cachedSeats: MutableMap<String, TextView> = mutableMapOf()
    private val occupiedSeatBackGroundColor: Int by lazy {
        resources.getColor(R.color.selected_seat, null)
    }
    private val nonOccupiedSeatBackGroundColor: Int by lazy {
        resources.getColor(R.color.white, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =
            DataBindingUtil.setContentView(this@BookingSeatActivity, R.layout.activity_booking_seat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        applyWindowInsets()
        initializeSeatTables()

        bookingSeatPresenter.loadBookingSeatInfo(
            movieId = restoreMovieId(),
            movieSchedule = restoreMovieSchedule(),
            headcount = restoreHeadcount(),
            theaterName = restoreTheaterName(),
        )

        setConfirmButtonClickListener()
    }

    override fun showMovieTitle(movieTitle: String) {
        binding.movieTitle = movieTitle
    }

    override fun showTotalPrice(totalPrice: Int) {
        binding.totalPrice = totalPrice
        binding.stringFormatter = StringFormatter
    }

    override fun showSeatView(
        seatPosition: Seat,
        isOccupied: Boolean,
    ) {
        val seatView: TextView? = cachedSeats[seatPosition.toSeatTag()]
        when (isOccupied) {
            true -> seatView?.setBackgroundColor(occupiedSeatBackGroundColor)
            false -> seatView?.setBackgroundColor(nonOccupiedSeatBackGroundColor)
        }
    }

    override fun showConfirmButton(isEnabled: Boolean) {
        binding.btnConfirm.isEnabled = isEnabled
    }

    override fun moveToBookedTicket(bookedTicket: BookedTicket) {
        handleScheduleNotification(bookedTicket)

        startActivity(BookingCompleteActivity.newIntent(this, bookedTicket.id!!))
        finish()
    }

    private fun handleScheduleNotification(bookedTicket: BookedTicket) {
        if (StorageProvider.hasPushNotificationPermission &&
            AlarmManagerCompat.hasExactAlarmPermission(this)
        ) {
            scheduleNotification(bookedTicket)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initializeSeatTables() {
        binding.tableLayoutSeats
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                tableRow.forEachIndexed { colIndex, textView ->
                    setSeatTag(textView as TextView, rowIndex, colIndex)
                    setSeatColor(textView, rowIndex)
                    textView.setOnClickListener {
                        bookingSeatPresenter.updateSeat(textView.getTag(R.id.seat_tag).toString())
                        bookingSeatPresenter.updateConfirmButton()
                    }
                }
            }
    }

    private fun setConfirmButtonClickListener() {
        binding.btnConfirm.setOnClickListener {
            showDialog(
                getString(R.string.text_booking_dialog_title),
                getString(R.string.text_booking_dialog_description),
            )
        }
    }

    private fun scheduleNotification(bookedTicket: BookedTicket) {
        val triggerTime = bookedTicket.movieSchedule.screeningDateTime.minusMinutes(30L)
        if (triggerTime.isBefore(LocalDateTime.now())) return

        val triggerAtMillis =
            triggerTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val intent = MovieReminderReceiver.newIntent(this, bookedTicket)
        val requestCode =
            bookedTicket.id!!.toInt() // 현재 Long 타입의 변수이기에 int 범위보다 커질경우 에러가 발생할 가능성 존재
        val pendingIntent =
            PendingIntent.getBroadcast(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        AlarmManagerCompat.setExact(this, triggerAtMillis, pendingIntent)
    }

    private fun setSeatTag(
        textView: TextView,
        rowIndex: Int,
        colIndex: Int,
    ) {
        val seatText = "${ASCII_A + rowIndex}${colIndex + 1}"
        textView.setTag(R.id.seat_tag, seatText)
        cachedSeats[seatText] = textView
        textView.text = seatText
    }

    private fun setSeatColor(
        textView: TextView,
        rowIndex: Int,
    ) {
        when {
            rowIndex < B_LINE -> textView.setTextColor(getColor(R.color.seat_b_grade))
            rowIndex < S_LINE -> textView.setTextColor(getColor(R.color.seat_s_grade))
            rowIndex < A_LINE -> textView.setTextColor(getColor(R.color.seat_a_grade))
        }
    }

    private fun showDialog(
        title: String,
        description: String,
    ) {
        AlertDialog
            .Builder(this)
            .setTitle(title)
            .setMessage(description)
            .setPositiveButton(getString(R.string.text_booking_dialog_positive_button)) { _, _ ->
                bookingSeatPresenter.bookingTicket()
            }
            .setNegativeButton(getString(R.string.text_booking_dialog_negative_button)) { dialog, _ ->
                dialog.dismiss()
            }.setCancelable(false)
            .show()
    }

    private fun restoreMovieId() = intent.getLongExtra(EXTRA_MOVIE_ID, 0L)

    private fun restoreMovieSchedule() = intent.intentSerializable(EXTRA_MOVIE_SCHEDULE, MovieSchedule::class.java)!!

    private fun restoreHeadcount() = intent.intentSerializable(EXTRA_HEADCOUNT, Headcount::class.java)!!

    private fun restoreTheaterName() = intent.getStringExtra(EXTRA_THEATER_NAME)!!

    private fun Seat.toSeatTag(): String = "${ASCII_A + row}${col + 1}"

    companion object {
        fun newIntent(
            context: Context,
            movieId: Long,
            movieSchedule: MovieSchedule,
            headcount: Headcount,
            theaterName: String,
        ) = Intent(context, BookingSeatActivity::class.java).apply {
            putExtra(EXTRA_MOVIE_ID, movieId)
            putExtra(EXTRA_MOVIE_SCHEDULE, movieSchedule)
            putExtra(EXTRA_HEADCOUNT, headcount)
            putExtra(EXTRA_THEATER_NAME, theaterName)
        }

        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        private const val EXTRA_MOVIE_SCHEDULE = "EXTRA_MOVIE_SCHEDULE"
        private const val EXTRA_HEADCOUNT = "EXTRA_HEADCOUNT"
        private const val EXTRA_THEATER_NAME = "EXTRA_THEATER_NAME"
        private const val ASCII_A = 'A'

        private const val B_LINE = 2
        private const val S_LINE = 4
        private const val A_LINE = 5
    }
}
