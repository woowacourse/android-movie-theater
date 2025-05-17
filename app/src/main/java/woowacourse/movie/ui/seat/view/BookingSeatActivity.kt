package woowacourse.movie.ui.seat.view

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.data.repository.BookedTicketRepositoryImpl
import woowacourse.movie.databinding.ActivityBookingSeatBinding
import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.movie.TicketType
import woowacourse.movie.domain.model.seat.AndroidAlarmScheduler
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.domain.model.theater.Seat
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.ui.complete.view.BookingCompleteActivity
import woowacourse.movie.ui.seat.contract.BookingSeatContract
import woowacourse.movie.ui.seat.presenter.BookingSeatPresenter
import woowacourse.movie.utils.StringFormatter.thousandFormat
import woowacourse.movie.utils.intentSerializable
import java.time.LocalDateTime

class BookingSeatActivity :
    AppCompatActivity(),
    BookingSeatContract.View {
    private val database by lazy { (application as MovieApplication).database }
    private val bookingSeatPresenter by lazy {
        BookingSeatPresenter(
            this,
            BookedTicketRepositoryImpl(database.bookedTicketDao()),
            AndroidAlarmScheduler(this),
        )
    }
    private lateinit var binding: ActivityBookingSeatBinding

    private val seatTextViews: MutableMap<String, TextView> = mutableMapOf()
    private val confirmButton: Button by lazy { binding.btnConfirm }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_seat)

        applyWindowInsets()
        initializeSeatTextViews()
        initializeFromIntent()
        bookingSeatPresenter.updateViews()
        setConfirmButtonClickListener()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initializeFromIntent() {
        val theater = intent.intentSerializable(EXTRA_THEATER, Theater::class.java) ?: Theater()
        val headcount =
            intent.intentSerializable(EXTRA_HEADCOUNT, Headcount::class.java) ?: Headcount()
        val title = intent.getStringExtra(EXTRA_MOVIE_TITLE) ?: ""
        val bookedDateTime =
            intent.intentSerializable(EXTRA_DATETIME, LocalDateTime::class.java)
                ?: LocalDateTime.now()
        val notificationSetting: Boolean =
            getSharedPreferences("settings", MODE_PRIVATE).getBoolean("notification", false)

        bookingSeatPresenter.loadState(
            theater,
            headcount,
            title,
            bookedDateTime,
            notificationSetting,
        )
    }

    override fun setTotalPrice(totalPrice: Int) {
        binding.tvPrice.text =
            getString(R.string.text_korean_won).format(thousandFormat(totalPrice))
    }

    override fun setMovieTitle(movieTitle: String) {
        binding.tvMovieTitle.text = movieTitle
    }

    override fun toggleSeat(
        seatPosition: Seat,
        isOccupied: Boolean,
    ) {
        val seatView: TextView? = seatTextViews[seatPosition.toSeatTag()]
        when (isOccupied) {
            true -> seatView?.setBackgroundColor(getColor(R.color.selected_seat))
            false -> seatView?.setBackgroundColor(getColor(R.color.white))
        }
    }

    private fun initializeSeatTextViews() {
        binding.tableLayoutSeats
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                tableRow.forEachIndexed { colIndex, textView ->
                    with(textView as TextView) {
                        setSeatTag(this, rowIndex, colIndex)
                        setSeatColor(this, rowIndex)
                        setOnClickListener {
                            bookingSeatPresenter.selectSeat(
                                seatFromTag(getTag(R.id.seat_tag).toString()),
                            )
                        }
                    }
                }
            }
    }

    override fun setConfirmButton(isEnabled: Boolean) {
        confirmButton.isEnabled = isEnabled
    }

    override fun startBookingCompleteActivity(bookedTicket: BookedTicket) {
        startActivity(BookingCompleteActivity.newIntent(this, bookedTicket))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    override fun setAlarmManager(bookedTicket: BookedTicket) {
        val alarmMgr = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = AlarmReceiver.newIntent(this, bookedTicket)
        val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmMgr.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 3000,
            // 테스트를 위해 3초로 설정
            alarmIntent,
        )
    }

    private fun setConfirmButtonClickListener() {
        binding.confirmBtnClickListener =
            ConfirmButtonClickListener {
                showDialog(
                    getString(R.string.text_booking_dialog_title),
                    getString(R.string.text_booking_dialog_description),
                )
            }
    }

    private fun setSeatTag(
        textView: TextView,
        rowIndex: Int,
        colIndex: Int,
    ) {
        val seatText = "${ASCII_A + rowIndex}${colIndex + 1}"
        textView.setTag(R.id.seat_tag, seatText)
        seatTextViews[seatText] = textView
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
                bookingSeatPresenter.insertBookedTicket()
                bookingSeatPresenter.completeBookingSeat()
                bookingSeatPresenter.postNotification()
            }.setNegativeButton(getString(R.string.text_booking_dialog_negative_button)) { dialog, _ ->
                dialog.dismiss()
            }.setCancelable(false)
            .show()
    }

    private fun Seat.toSeatTag(): String = "${ASCII_A + row}${col + 1}"

    private fun seatFromTag(tag: String): Seat {
        val row = tag[0] - ASCII_A
        val col = tag[1].digitToInt() - ONE_BASED
        val ticketType = TicketType.ticketTypeByRow(row)
        return Seat(row, col, ticketType)
    }

    companion object {
        fun newIntent(
            context: Context,
            movieTitle: String,
            dateTime: LocalDateTime,
            headcount: Headcount,
            theater: Theater,
        ) = Intent(context, BookingSeatActivity::class.java).apply {
            putExtra(EXTRA_MOVIE_TITLE, movieTitle)
            putExtra(EXTRA_DATETIME, dateTime)
            putExtra(EXTRA_HEADCOUNT, headcount)
            putExtra(EXTRA_THEATER, theater)
        }

        private const val EXTRA_MOVIE_TITLE = "movieTitle"
        private const val EXTRA_DATETIME = "dateTime"
        private const val EXTRA_HEADCOUNT = "headcount"
        private const val EXTRA_THEATER = "theater"
        private const val ASCII_A = 'A'

        private const val ONE_BASED = 1

        private const val B_LINE = 2
        private const val S_LINE = 4
        private const val A_LINE = 5
    }
}
