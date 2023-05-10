package woowacourse.movie.movie.seat

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import domain.Seat
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.movie.database.DBController
import woowacourse.movie.movie.database.TicketDataDBHelper
import woowacourse.movie.movie.dto.BookingHistoryDto
import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movie.dto.movie.SeatMovieDto
import woowacourse.movie.movie.dto.seat.SeatsDto
import woowacourse.movie.movie.moviedetail.MovieDetailActivity.Companion.SEAT_BASE_INFORMATION_KEY
import woowacourse.movie.movie.setting.AlarmReceiver
import woowacourse.movie.movie.ticket.TicketActivity
import woowacourse.movie.movie.utils.PendingIntentBuilder
import woowacourse.movie.movie.utils.Toaster
import woowacourse.movie.movie.utils.getParcelableCompat
import woowacourse.movie.movie.view.SeatSelectView

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {
    private lateinit var binding: ActivitySeatSelectionBinding
    override lateinit var presenter: SeatSelectionContract.Presenter
    private lateinit var dBController: DBController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatSelectionBinding.inflate(layoutInflater)
        dBController = DBController(TicketDataDBHelper(this).writableDatabase)
        setToolBar()
        presenter = SeatSelectionPresenter(this)
        setContentView(binding.root)
        initData()
        setUpState(savedInstanceState)
    }

    private fun setToolBar() {
        setSupportActionBar(binding.seatSelectionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initData() {
        val seatBaseInfo = intent.getParcelableCompat<SeatMovieDto>(SEAT_BASE_INFORMATION_KEY)
        seatBaseInfo?.let { presenter.initInfo(it) }
    }

    override fun setMovieTitle(title: String) {
        binding.movieTitle.text = title
    }

    override fun setUpState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val seats = savedInstanceState.getParcelableCompat<SeatsDto>(SEATS_POSITION)
            seats?.let { presenter.initOtherSeatState(it) }
        } else {
            presenter.initNoneSeatState()
        }
        setUpSeatsView()
        setEnterBtnClickable()
    }

    override fun setPrice(ticketPrice: Int) {
        binding.ticketPrice.text = getString(R.string.ticket_price_seat_page, ticketPrice)
    }

    private fun setUpSeatsView() {
        SeatSelectView(
            binding.seatLayout,
            ::onSeatClick,
            presenter.seats
        )
    }

    private fun onSeatClick(seat: Seat, textView: TextView) {
        when {
            presenter.isPossibleSelect(seat) -> selectSeat(textView, seat)
            presenter.isSeatCancelable(seat) -> unselectSeat(textView, seat)
            else -> {
                Toaster.showToast(this, TOAST_MESSAGGE)
                return
            }
        }
        presenter.getPricePerDate()
        setEnterBtnClickable()
    }

    private fun setEnterBtnClickable() {
        if (presenter.getPossibleClick()) {
            binding.enterBtn.setBackgroundColor(getColor(R.color.enter))
            onEnterBtnClickListener()
        } else {
            binding.enterBtn.setBackgroundColor(getColor(R.color.not_enter))
            binding.enterBtn.setOnClickListener(null)
        }
    }

    private fun onEnterBtnClickListener() {
        binding.enterBtn.setOnClickListener {
            showBookingDialog()
        }
    }

    private fun showBookingDialog() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.seat_dialog_title)
            .setMessage(R.string.seat_dialog_message)
            .setPositiveButton(R.string.seat_dialog_yes) { _, _ -> moveActivity() }
            .setNegativeButton(R.string.seat_dialog_no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun selectSeat(textView: TextView, seat: Seat) {
        textView.setBackgroundColor(getColor(R.color.select_seat))
        presenter.selectSeat(seat)
    }

    private fun unselectSeat(textView: TextView, seat: Seat) {
        textView.setBackgroundColor(getColor(R.color.white))
        presenter.unselectSeat(seat)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SEATS_POSITION, presenter.convertSeatsToDto())
        super.onSaveInstanceState(outState)
    }

    private fun moveActivity() {
        val bookingMovie = presenter.getBookingMovie()
        storeData(bookingMovie)
        val intent = Intent(this, TicketActivity::class.java)
        intent.putExtra(BOOKING_MOVIE_KEY, bookingMovie)
        putAlarm()
        BookingHistoryDto.add(bookingMovie)
        startActivity(intent)
        finish()
    }

    private fun storeData(bookingMovie: BookingMovieEntity) {
        dBController.insertDB(bookingMovie)
    }

    override fun putAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            presenter.setDateTime(),
            PendingIntentBuilder(this).createReceiverPendingIntent(
                Intent(this, AlarmReceiver::class.java),
                presenter.getBookingMovie()
            )
        )
    }

    companion object {
        const val TICKET_KEY = "ticket"
        const val MOVIE_KEY = "movie"
        const val DATE_KEY = "movie_date"
        const val TIME_KEY = "movie_time"
        const val BOOKING_MOVIE_KEY = "booking_movie"
        const val SEATS_POSITION = "seats_position"

        const val ALARM_TIME = 30

        private const val TOAST_MESSAGGE = "지정한 좌석 수를 초과합니다"
    }
}
