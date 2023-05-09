package woowacourse.movie.activity.seatpicker

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.woowacourse.domain.seat.Seat
import com.woowacourse.domain.seat.SeatColumn
import com.woowacourse.domain.seat.SeatGroup
import com.woowacourse.domain.seat.SeatRow
import com.woowacourse.domain.ticket.Ticket
import com.woowacourse.domain.ticket.TicketBundle
import woowacourse.movie.AlarmSetter
import woowacourse.movie.BookHistories
import woowacourse.movie.BookingHistoryRepository
import woowacourse.movie.BundleKeys
import woowacourse.movie.BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY
import woowacourse.movie.MovieReminder
import woowacourse.movie.R
import woowacourse.movie.Theater
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.activity.bookcomplete.BookCompleteActivity
import woowacourse.movie.databinding.ActivitySeatPickerBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toPresentation
import woowacourse.movie.model.SeatGroupModel
import woowacourse.movie.movie.Movie
import woowacourse.movie.movie.MovieBookingInfo
import woowacourse.movie.movie.MovieBookingSeatInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SeatPickerActivity : BackButtonActivity(), SeatPickerContract.View {
    private lateinit var binding: ActivitySeatPickerBinding

    override lateinit var presenter: SeatPickerContract.Presenter
    private var seatGroup = SeatGroup()
    private lateinit var ticketBundle: TicketBundle
    private val bookHistory: BookingHistoryRepository by lazy {
        BookingHistoryRepository(BookHistories.getDBInstance(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_picker)

        val movieBookingInfo = getMovieBookingInfo()
        presenter = SeatPickerPresenter(this, movieBookingInfo)
        presenter.initPage()
        ticketBundle = TicketBundle(count = movieBookingInfo.ticketCount)
        reloadData(savedInstanceState)

        setPickDoneButtonClickListener()
        setSeatsOnClickListener(movieBookingInfo)
    }

    private fun getMovieBookingInfo(): MovieBookingInfo {
        return intent.getSerializableCompat(BundleKeys.MOVIE_BOOKING_INFO_KEY)
            ?: MovieBookingInfo.dummyData
    }

    private fun getTheaterData(): Theater {
        return intent.getSerializableCompat(BundleKeys.THEATER_DATA_KEY) ?: Theater.dummyData
    }

    override fun initSeat() {
        initSeatName()
        initSeatNameColor()
    }

    private fun initSeatName() {
        getRowSeats().forEachIndexed { index, rowSeat ->
            rowSeat.children
                .filterIsInstance<TextView>()
                .forEachIndexed { rowIndex, seat ->
                    seat.text = formatSeatName(index, rowIndex)
                }
        }
    }

    private fun formatSeatName(index: Int, rowIndex: Int) =
        getString(R.string.seat_name).format(ALPHABET[index], rowIndex + 1)

    private fun initSeatNameColor() {
        getSeats().forEachIndexed { index, seat ->
            when (index) {
                in SEAT_B -> seat.setTextColor(getColor(R.color.seat_color_b))
                in SEAT_S -> seat.setTextColor(getColor(R.color.seat_color_s))
                in SEAT_A -> seat.setTextColor(getColor(R.color.seat_color_a))
                else -> throw IllegalArgumentException("잘못된 값: $index 분류될 수 없는 값입니다.")
            }
        }
    }

    private fun getRowSeats() =
        binding.tlSeats.children
            .filterIsInstance<TableRow>()
            .toList()

    private fun getSeats() =
        binding.tlSeats.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>()
            .toList()

    private fun reloadData(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            setMovieTitle(savedInstanceState.getString(MOVIE_TITLE) ?: Movie.dummyData.title)
            setTicketPrice(savedInstanceState.getString(TICKET_PRICE) ?: "0원")
            seatGroup =
                (savedInstanceState.getSerializable(PICKED_SEAT) as SeatGroupModel).toDomain()
            val seatNames = seatGroup.seats.map {
                formatSeatName(
                    it.row.value,
                    it.column.value
                )
            }
            setPickDoneButtonColor()

            Toast.makeText(this, "$seatNames", Toast.LENGTH_SHORT).show()
            getSeats().forEach {
                if (seatNames.contains(it.text)) it.setBackgroundColor(getColor(R.color.picked_seat_color))
            }
        }
    }

    override fun setMovieTitle(title: String) {
        binding.tvSeatPickerMovie.text = title
    }

    override fun setTicketPrice(price: String) {
        binding.tvSeatPickerTicketPrice.text = price
    }

    private fun setPickDoneButtonClickListener() {
        binding.btSeatPickerDone.setOnClickListener {
            presenter.onPickDoneButtonClicked()
        }
    }

    override fun showDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.alert_dialog_book_confirm))
            setMessage(getString(R.string.alert_dialog_book_re_confirm))
            setPositiveButton(getString(R.string.alert_dialog_book_done)) { _, _ ->
                val movieBookingSeatInfo = getMovieBookingSeatInfo()
                BookHistories.items.add(movieBookingSeatInfo)
                bookHistory.insert(movieBookingSeatInfo)

                setMovieAlarm(movieBookingSeatInfo)
                startActivity(
                    getIntent(movieBookingSeatInfo).putExtra(
                        BundleKeys.THEATER_DATA_KEY,
                        getTheaterData()
                    )
                )
                finish()
            }
            setNegativeButton(getString(R.string.alert_dialog_book_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(false)
            show()
        }
    }

    private fun getMovieBookingSeatInfo() = MovieBookingSeatInfo(
        getMovieBookingInfo(),
        seatGroup.sorted().seats.map {
            formatSeatName(it.row.value, it.column.value)
        },
        binding.tvSeatPickerTicketPrice.text.toString()
    )

    private fun setMovieAlarm(movieBookingSeatInfo: MovieBookingSeatInfo) {
        val movieReminderIntent = MovieReminder.intent(this)
        movieReminderIntent.putExtra(MOVIE_BOOKING_SEAT_INFO_KEY, movieBookingSeatInfo)

        val formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm")
        val movieDateTime =
            LocalDateTime.parse(movieBookingSeatInfo.movieBookingInfo.formatAlarmDate(), formatter)
        val triggerTime = movieDateTime.minusMinutes(MOVIE_RUN_BEFORE_TIME)

        AlarmSetter.setAlarm(
            this,
            MovieReminder.NOTIFICATION_ID,
            movieReminderIntent,
            triggerTime
        )
    }

    private fun getIntent(movieBookingSeatInfo: MovieBookingSeatInfo): Intent {
        return BookCompleteActivity.intent(this, movieBookingSeatInfo)
    }

    private fun setSeatsOnClickListener(movieBookingInfo: MovieBookingInfo) {
        getSeats().forEachIndexed { index, seat ->
            seat.setOnClickListener {
                val newSeat =
                    Seat(SeatRow(index / SEAT_ROW_INTERVAL), SeatColumn(index % SEAT_ROW_INTERVAL))
                if (seatGroup.seats.contains(newSeat)) {
                    progressRemoveSeat(newSeat, seat, movieBookingInfo)
                    return@setOnClickListener
                }
                if (seatGroup.canAdd(ticketBundle.count) && !seatGroup.seats.contains(newSeat)) {
                    progressAddSeat(newSeat, seat, movieBookingInfo)
                }
            }
        }
    }

    private fun progressAddSeat(
        newSeat: Seat,
        seat: TextView,
        movieBookingInfo: MovieBookingInfo,
    ) {
        seatGroup = seatGroup.add(newSeat)
        ticketBundle = ticketBundle.add(Ticket(newSeat.getSeatTier().price))
        seat.setBackgroundColor(getColor(R.color.picked_seat_color))
        setPickDoneButtonColor()
        setTicketPrice(
            getString(
                R.string.ticket_price_format,
                ticketBundle.calculateTotalPrice(
                    movieBookingInfo.date,
                    movieBookingInfo.time
                )
            )
        )
    }

    private fun progressRemoveSeat(
        newSeat: Seat,
        seat: TextView,
        movieBookingInfo: MovieBookingInfo
    ) {
        seatGroup = seatGroup.remove(newSeat)
        ticketBundle = ticketBundle.remove(Ticket(newSeat.getSeatTier().price))
        seat.setBackgroundColor(getColor(R.color.unpicked_seat_color))
        setPickDoneButtonColor()
        setTicketPrice(
            getString(
                R.string.ticket_price_format,
                ticketBundle.calculateTotalPrice(
                    movieBookingInfo.date,
                    movieBookingInfo.time
                )
            )
        )
    }

    private fun setPickDoneButtonColor() {
        binding.btSeatPickerDone.isEnabled = !seatGroup.canAdd(ticketBundle.count)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(MOVIE_TITLE, binding.tvSeatPickerMovie.text.toString())
        outState.putString(TICKET_PRICE, binding.tvSeatPickerTicketPrice.text.toString())
        outState.putSerializable(PICKED_SEAT, seatGroup.toPresentation())
    }

    companion object {
        private val ALPHABET = ('A'..'Z').toList()
        private val SEAT_B = 0..7
        private val SEAT_S = 8..15
        private val SEAT_A = 16..19
        private const val MOVIE_TITLE = "movieTitle"
        private const val TICKET_PRICE = "ticketPrice"
        private const val PICKED_SEAT = "prickedSeat"
        private const val SEAT_ROW_INTERVAL = 4
        private const val MOVIE_RUN_BEFORE_TIME = 30L

        fun intent(context: Context) = Intent(context, SeatPickerActivity::class.java)
    }
}
