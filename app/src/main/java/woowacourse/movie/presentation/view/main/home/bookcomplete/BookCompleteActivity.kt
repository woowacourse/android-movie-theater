package woowacourse.movie.presentation.view.main.home.bookcomplete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookCompleteBinding
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.ReservationResult
import woowacourse.movie.presentation.view.common.BackButtonActivity

class BookCompleteActivity : BackButtonActivity(), BookingCompleteContract.View {
    private lateinit var binding: ActivityBookCompleteBinding
    private val presenter: BookingCompleteContract.Presenter by lazy {
        BookingCompletePresenter(
            this, intent.getParcelableCompat(RESERVATION_ID_INTENT_KEY)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_complete)
        presenter.onCreate()
    }

    private fun formatBookingTime(date: String, time: String): String {
        val formattedDate: String = date.split("-").joinToString(".")
        return "$formattedDate $time"
    }

    override fun initView(reservationResult: ReservationResult) {
        binding.tvBookMovieTitle.text = reservationResult.movieTitle
        binding.tvBookDate.text =
            formatBookingTime(reservationResult.date, reservationResult.time)
        binding.tvBookPersonCount.text =
            getString(R.string.book_person_info).format(
                reservationResult.ticketCount,
                reservationResult.seatNames
            )
        binding.tvBookTotalPay.text =
            getString(R.string.book_total_pay).format(
                reservationResult.totalPrice
            )
    }

    override fun finishErrorView() {
        Toast.makeText(this, getString(R.string.error_intent_message), Toast.LENGTH_SHORT)
            .show()
        this.finish()
    }

    companion object {
        const val RESERVATION_ID_INTENT_KEY = "RESERVATION_ID_INTENT_KEY"
        fun getIntent(context: Context): Intent {
            return Intent(context, BookCompleteActivity::class.java)
        }
    }
}
