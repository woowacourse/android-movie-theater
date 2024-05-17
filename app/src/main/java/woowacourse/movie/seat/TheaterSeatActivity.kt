package woowacourse.movie.seat

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.IntentCompat
import androidx.core.view.children
import androidx.room.Room
import woowacourse.movie.R
import woowacourse.movie.base.BindingActivity
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.databinding.ActivityTheaterSeatBinding
import woowacourse.movie.error.ErrorActivity
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat
import woowacourse.movie.notification.NotificationChannelManager
import woowacourse.movie.purchaseConfirmation.PurchaseConfirmationActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class TheaterSeatActivity :
    BindingActivity<ActivityTheaterSeatBinding>(R.layout.activity_theater_seat),
    TheaterSeatContract.View {
    private lateinit var presenter: TheaterSeatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        initPresenter()
        initSeats()

        binding.confirmButton.setOnClickListener {
            confirmTicketPurchase()
        }
    }

    override fun updateSeatDisplay(seat: Seat) {
        val buttonId = resources.getIdentifier("${seat.row}${seat.number}", "id", packageName)
        val button = findViewById<Button>(buttonId)
        val color = if (seat.chosen) Color.RED else Color.WHITE
        button.setBackgroundColor(color)
    }

    override fun showConfirmationDialog(
        title: String,
        message: String,
        positiveLabel: String,
        onPositiveButtonClicked: () -> Unit,
        negativeLabel: String,
        onNegativeButtonClicked: () -> Unit,
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(positiveLabel) { _, _ -> onPositiveButtonClicked() }
        builder.setNegativeButton(negativeLabel) { dialog, _ ->
            onNegativeButtonClicked()
            dialog.dismiss()
        }
        builder.show()
    }

    override fun setSeatBackground(
        seatId: String,
        color: String,
    ) {
        val buttonId = resources.getIdentifier(seatId, "id", packageName)
        val button = findViewById<Button>(buttonId)
        val colorInt = Color.parseColor(color)
        button.setBackgroundColor(colorInt)
    }

    override fun navigateToNextPage(intent: Intent) {
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun showTitle(title: Title) {
        binding.title = title.toString()
        binding.executePendingBindings()
    }

    override fun showPrice(price: Int) {
        binding.price = price
        binding.executePendingBindings()
    }

    private fun initPresenter() {
        val ticketNum = intent.getStringExtra(EXTRA_TICKET_NUM) ?: return ErrorActivity.start(this)
        val cinema = IntentCompat.getSerializableExtra(intent, EXTRA_CINEMA, Cinema::class.java)
        val showTime = intent.getStringExtra(EXTRA_TIME_DATE) ?: return ErrorActivity.start(this)
        if (cinema == null) {
            ErrorActivity.start(this)
            return finish()
        }
        val database = Room.databaseBuilder(this, AppDatabase::class.java, "ticket").build()
        presenter = TheaterSeatPresenter(this, database, showTime, ticketNum.toInt(), cinema)
    }

    private fun initSeats() {
        binding.seatTable.children.filterIsInstance<TableRow>()
            .forEach { row ->
                row.children.filterIsInstance<Button>()
                    .forEach { button ->
                        button.setOnClickListener { presenter.toggleSeatSelection(button.text.toString()) }
                    }
            }
    }

    private fun confirmTicketPurchase() {
        showConfirmationDialog(
            title = "예매 확인",
            message = "정말 예매하시겠습니까?",
            positiveLabel = "예매 완료",
            onPositiveButtonClicked = onPositiveButtonClicked@{
                val cinema =
                    IntentCompat.getSerializableExtra(intent, EXTRA_CINEMA, Cinema::class.java)
                val ticketPrice = findViewById<TextView>(R.id.total_price).text
                if (cinema != null) {
                    alarmSettings(cinema, ticketPrice)
                } else {
                    Toast.makeText(this, "Cinema data is not available.", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            negativeLabel = "취소",
            onNegativeButtonClicked = {},
        )
    }

    private fun alarmSettings(
        cinema: Cinema,
        ticketPrice: CharSequence,
    ) {
        val timeDate = intent.getStringExtra(EXTRA_TIME_DATE)!!
        val formatter = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA)
        try {
            val notificationChannelManager = NotificationChannelManager(this)
            notificationChannelManager.createNotificationChannel()
            val date = formatter.parse(timeDate)
            val movieStartTime = date?.time ?: return
            presenter.saveTicketToDatabase(movieStartTime, cinema)
        } catch (e: ParseException) {
            Toast.makeText(this, RESERVATION_TIME_FORMAT_ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
        }
    }

    override fun navigateToPurchase(ticketId: Int) {
        val confirmationIntent =
            PurchaseConfirmationActivity
                .newIntent(context = this, ticketId = ticketId)
        navigateToNextPage(confirmationIntent)
    }

    override fun makeNotify(
        movieStartTime: Long,
        cinema: Cinema,
        ticketId: Int,
    ) {
        NotificationChannelManager(this).scheduleMovieStartNotification(
            movieStartTime,
            cinema,
            ticketId,
        )
    }

    companion object {
        const val EXTRA_TIME_DATE = "timeDate"
        const val EXTRA_TICKET_NUM = "ticketNum"
        const val EXTRA_CINEMA = "cinema"
        const val RESERVATION_TIME_FORMAT_ERROR_MESSAGE = "예매 시간 형식이 잘못되었습니다."

        fun newIntent(
            context: Context,
            ticketNum: String,
            cinema: Cinema,
            timeDate: String,
        ): Intent {
            return Intent(
                context,
                TheaterSeatActivity::class.java,
            ).apply {
                putExtra(EXTRA_TICKET_NUM, ticketNum)
                putExtra(EXTRA_CINEMA, cinema)
                putExtra(EXTRA_TIME_DATE, timeDate)
            }
        }
    }
}
