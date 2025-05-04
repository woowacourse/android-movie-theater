package woowacourse.movie.view.home.complete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.StringFormatter
import woowacourse.movie.view.ext.getSerializableOrNull
import woowacourse.movie.view.ext.showToastFromResource
import java.time.LocalDate
import java.time.LocalTime

class BookingCompleteActivity : AppCompatActivity(), BookingCompleteContract.View {
    private lateinit var binding: ActivityBookingCompleteBinding
    private lateinit var presenter: BookingCompleteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_complete)

        intent.getSerializableOrNull<Ticket>(KEY_TICKET)?.let {
            presenter = BookingCompletePresenter(this, it)
        } ?: run {
            showToastFromResource(R.string.error_missing_booking_info)
            finish()
        }

        initView()
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showTicket(ticket: Ticket) {
        with(ticket) {
            initBookingMovieTitleView(title)
            initBookingScheduleView(bookingDate, bookingTime)
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
        binding.tvPeopleCount.text =
            getString(R.string.text_general_people_count).format(peopleCount)
    }

    private fun initBookingTicketPriceView(ticketPrice: Int) {
        val priceFormat = StringFormatter.thousandFormat(ticketPrice)
        binding.tvPrice.text =
            getString(R.string.text_on_site_payment).format(priceFormat)
    }

    private fun seatToLabel(seats: Set<Seat>): String {
        return seats.joinToString {
            val rowLetter = ('A' + it.x.value - 1)
            val columnNumber = it.y.value
            "$rowLetter$columnNumber"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val KEY_TICKET = "BOOKING_TICKET"

        fun newIntent(
            context: Context,
            ticket: Ticket,
        ) = Intent(context, BookingCompleteActivity::class.java).apply {
            putExtra(KEY_TICKET, ticket)
        }
    }
}
