package woowacourse.movie.moviebookingseat

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
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieBookingSeatBinding
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.helper.BuildVersion
import woowacourse.movie.helper.CustomClickListenerHelper.setOnSingleClickListener
import woowacourse.movie.moviebooked.MovieBookedActivity

class MovieBookingSeatActivity : AppCompatActivity(), MovieBookingSeat.View {
    private lateinit var binding: MovieBookingSeatBinding
    private lateinit var presenter: MovieBookingSeatPresenter
    private lateinit var bookingStatus: BookingStatus
    private lateinit var theater: Theater
    private var price: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.movie_booking_seat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booking_seat)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bookingStatus =
            BuildVersion().getParcelableClass(intent, KEY_BOOKING_SEAT, BookingStatus::class)
        theater = BuildVersion().getParcelableClass(intent, KEY_THEATER, Theater::class)
        presenter = MovieBookingSeatPresenter(this@MovieBookingSeatActivity)
        presenter.loadBookingStatus(bookingStatus)
        initSeatTable()
    }

    override fun showBookingStatusInfo() {
        binding.bookingStatus = bookingStatus
    }

    override fun updateButton() {
        binding.confirmButton.setBackgroundResource(R.color.purple_500)
        binding.confirmButton.setOnSingleClickListener { showConfirmDialog(bookingStatus) }
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
        binding.moviePrice.text =
            binding.moviePrice.context.getString(
                R.string.booking_seat_price,
                price,
            )
    }

    override fun showConfirmDialog(bookingStatus: BookingStatus) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.check_movie_booking))
            .setMessage(getString(R.string.confirm_reservation_message))
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(getString(R.string.okay)) { _, _ ->
                navigateToMovieBooked(bookingStatus, theater)
            }
            .show()
            .setCancelable(false)
    }

    override fun navigateToMovieBooked(
        bookingStatus: BookingStatus,
        theater: Theater,
    ) {
        val intent =
            MovieBookedActivity.Companion.movieBookedIntent(
                this@MovieBookingSeatActivity,
                bookingStatus,
                theater,
            )
        startActivity(intent)
        finish()
    }

    override fun showError(messageResId: Int) {
        AlertDialog.Builder(this)
            .setMessage(getString(messageResId))
            .setPositiveButton(R.string.error_dialog_okay, null)
            .show()
            .setCancelable(false)
    }

    private fun initSeatTable() {
        binding.seatTable.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<TextView>().forEachIndexed { colIndex, seatTextView ->
                val seat = Seat.Companion.of(rowIndex, colIndex)
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
            otherActivity: AppCompatActivity,
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
