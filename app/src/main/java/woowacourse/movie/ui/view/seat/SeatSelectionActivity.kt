package woowacourse.movie.ui.view.seat

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.data.local.database.MovieDatabase.Companion.getMovieDatabase
import woowacourse.movie.data.local.datasource.TicketDataSourceImpl
import woowacourse.movie.domain.datasource.SettingsDataSource
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.reservation.PurchaseType
import woowacourse.movie.domain.reservation.Row
import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.reservation.SeatGrade
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.domain.ticket.TicketHistory
import woowacourse.movie.ui.alarm.Alarm
import woowacourse.movie.ui.util.ErrorMessage
import woowacourse.movie.ui.view.reservation.ShowReservationConfirmDialog
import woowacourse.movie.ui.view.ticket.TicketActivity
import java.io.Serializable
import java.time.LocalDateTime

class SeatSelectionActivity :
    AppCompatActivity(),
    SeatSelectionContract.View {
    private val showConfirmDialog by lazy { ShowReservationConfirmDialog(this) }

    private lateinit var presenter: SeatSelectionContract.Presenter
    private lateinit var seatsLayout: TableLayout
    private lateinit var titleView: TextView
    private lateinit var priceView: TextView
    private lateinit var completeView: Button

    private var seatViewMap: Map<Seat, TextView> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seat_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_seat_selection)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val selectedSeats: Set<Seat>? = savedInstanceState.getSelectedSeats()
        initPresenter(selectedSeats)
        findViews()
        presentModels()
        setEventListeners()
    }

    private fun setEventListeners() {
        completeView.setOnClickListener {
            presenter.tryReservation()
        }
    }

    private fun presentModels() {
        presenter.let {
            it.presentSeats()
            it.presentTitle()
            it.presentPrice()
            it.presentCompleteButton()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.getSelectedSeats().let { selectedSeats: Set<Seat> ->
            outState.putSerializable(KEY_SEATS, selectedSeats as Serializable)
        }
    }

    private fun initPresenter(selectedSeats: Set<Seat>?) {
        val ticket =
            intent.getTicketExtra(EXTRA_TICKET) ?: error(
                ErrorMessage(
                    CAUSE_TICKET,
                ).notProvided(),
            )
        val ticketDataSource: TicketDataSource =
            TicketDataSourceImpl(getMovieDatabase(this).ticketDao())
        val settingsDataSource: SettingsDataSource =
            (application as MovieApplication).settingDataSource
        presenter =
            SeatSelectionPresenter(
                this,
                ticket,
                ticketDataSource,
                settingsDataSource,
                selectedSeats,
            )
    }

    @Suppress("DEPRECATION")
    private fun Bundle?.getSelectedSeats(): Set<Seat>? {
        if (this == null) return null
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                getSerializable(
                    KEY_SEATS,
                    LinkedHashSet::class.java,
                ) as Set<Seat>

            else -> (getSerializable(KEY_SEATS) as? Set<Seat>)
        }
    }

    @Suppress("DEPRECATION")
    private fun Intent.getTicketExtra(key: String): Ticket? =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                getSerializableExtra(
                    key,
                    Ticket::class.java,
                )

            else -> getSerializableExtra(key) as? Ticket
        }

    private fun findViews() {
        seatsLayout = findViewById(R.id.layout_seat_selection_seats)
        titleView = findViewById(R.id.tv_seat_selection_title)
        priceView = findViewById(R.id.tv_seat_selection_price)
        completeView = findViewById(R.id.btn_seat_selection_complete)
    }

    override fun setSeats(
        seats: Set<Seat>,
        selectedSeats: Set<Seat>,
    ) {
        val seats: Map<Row, List<Seat>> = seats.groupBy { seat: Seat -> seat.row }
        seats.forEach { row: Row, seats: List<Seat> ->
            val seatsRow = TableRow(this)
            seats.forEach { seat: Seat ->
                val seatView: TextView = seatView(seat, selectedSeats)
                seatViewMap += seat to seatView
                seatsRow.addView(seatView)
            }
            seatsLayout.addView(seatsRow)
        }
    }

    private fun seatView(
        seat: Seat,
        selectedSeats: Set<Seat>,
    ): TextView =
        TextView(this).apply {
            text = seat.prettyString
            @ColorRes val colorResId: Int =
                when (seat.grade) {
                    SeatGrade.S -> R.color.seat_grade_s
                    SeatGrade.A -> R.color.seat_grade_a
                    SeatGrade.B -> R.color.seat_grade_b
                }
            val textColor = getColor(colorResId)
            setTextColor(textColor)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
            setPadding(27.dp)
            gravity = Gravity.CENTER
            background =
                AppCompatResources.getDrawable(
                    this@SeatSelectionActivity,
                    R.drawable.background_seat,
                )
            isSelected = seat in selectedSeats
            setOnClickListener { view: View ->
                presenter.onSeatSelect(seat)
            }
        }

    private val Seat.prettyString: String get() = "${row.prettyString}${column.value}"

    private val Row.prettyString: String get() = ('A' + this.value - 1).toString()

    private val Int.dp: Int get() = (this * resources.displayMetrics.density).toInt()

    override fun setTitle(title: String) {
        titleView.text = title
    }

    override fun setPrice(price: Int) {
        priceView.text = getString(R.string.seat_selection_price, price)
    }

    override fun setSeatIsSelected(
        seat: Seat,
        isSelected: Boolean,
    ) {
        val seatView: TextView =
            seatViewMap[seat] ?: error(
                ErrorMessage(
                    "seat",
                ).noSuch(),
            )
        seatView.isSelected = isSelected
    }

    override fun setConfirmEnabled(enabled: Boolean) {
        completeView.isEnabled = enabled
    }

    override fun askFinalReservation() {
        showConfirmDialog(
            title = getString(R.string.ticket_dialog_title),
            message = getString(R.string.ticket_dialog_message),
            positiveButtonText = getString(R.string.ticket_dialog_positive_button),
            positiveButtonAction = { _, _ ->
                presenter.confirmReservation()
            },
            negativeButtonText = getString(R.string.ticket_dialog_nagative_button),
            negativeButtonAction = { dialog: DialogInterface, _ -> dialog.dismiss() },
        )
    }

    override fun setTicketAlarm(
        ticketHistory: TicketHistory,
        isTicketAlarmChecked: Boolean,
    ) {
        if (isTicketAlarmChecked) {
            val alarm = Alarm(baseContext)
            alarm.scheduleTicketAlarm(ticketHistory)
        }
    }

    override fun navigateToTicketScreen(ticketId: Long) {
        val intent =
            TicketActivity.newIntent(
                this,
                ticketId,
            )
        startActivity(intent)
        finish()
    }

    companion object {
        private const val KEY_SEATS = "KEY_SEATS"

        private const val CAUSE_TICKET = "ticket"
        private const val CAUSE_CINEMA_NAME = "cinemaName"
        private const val CAUSE_SEAT_VIEW = "seatView"
        private const val IN_SEAT_LAYOUT = "seatLayout"

        private const val EXTRA_TICKET = "woowacourse.movie.EXTRA_TICKET"
        private const val EXTRA_CINEMA_NAME = "woowacourse.movie.CINEMA_NAME"

        fun newIntent(
            context: Context,
            title: String,
            count: Int,
            showtime: LocalDateTime,
            cinemaName: String,
            purchaseType: PurchaseType,
        ): Intent =
            run {
                val ticket =
                    Ticket(title, count, showtime, cinemaName, emptySet(), purchaseType)
                Intent(context, SeatSelectionActivity::class.java)
                    .putExtra(EXTRA_TICKET, ticket)
                    .putExtra(EXTRA_CINEMA_NAME, cinemaName)
            }
    }
}
