package woowacourse.movie.activity.bookcomplete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import woowacourse.movie.BundleKeys
import woowacourse.movie.BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY
import woowacourse.movie.R
import woowacourse.movie.Theater
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.databinding.ActivityBookCompleteBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.movie.MovieBookingSeatInfo

class BookCompleteActivity : BackButtonActivity(), BookCompleteContract.View {
    private lateinit var binding: ActivityBookCompleteBinding
    override lateinit var presenter: BookCompleteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_complete)

        val movieBookingData = getMovieBookingSeatInfo()
        presenter = BookCompletePresenter(this, movieBookingData)
        presenter.progressIfDummyData()
        presenter.initBookCompletePage()
    }

    private fun getTheaterData(): Theater {
        return intent.getSerializableCompat(BundleKeys.THEATER_DATA_KEY) ?: Theater.dummyData
    }

    private fun getMovieBookingSeatInfo(): MovieBookingSeatInfo {
        return intent.getSerializableCompat(MOVIE_BOOKING_SEAT_INFO_KEY)
            ?: MovieBookingSeatInfo.dummyData
    }

    override fun initMovieTitle(movieTitle: String) {
        binding.tvBookMovieTitle.text = movieTitle
    }

    override fun initBookDate(bookingTime: String) {
        binding.tvBookDate.text = bookingTime
    }

    override fun initBookPersonCount(ticketCount: Int, seats: List<String>) {
        binding.tvBookPersonCount.text =
            getString(
                R.string.book_person_count,
                ticketCount,
                seats.joinToString(", "),
                getTheaterData().name
            )
    }

    override fun initBookTotalPrice(totalPrice: String) {
        binding.tvBookTotalPay.text =
            getString(R.string.book_total_pay, totalPrice)
    }

    override fun showMessageIfDummyData() {
        Toast.makeText(
            this,
            getString(R.string.cant_get_movie_booking_data),
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        fun intent(context: Context, data: MovieBookingSeatInfo) =
            Intent(context, BookCompleteActivity::class.java).putExtra(
                MOVIE_BOOKING_SEAT_INFO_KEY,
                data
            )
    }
}
