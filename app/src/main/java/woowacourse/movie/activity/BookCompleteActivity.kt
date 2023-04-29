package woowacourse.movie.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import woowacourse.movie.BundleKeys
import woowacourse.movie.BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY
import woowacourse.movie.R
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.movie.MovieBookingSeatInfoUIModel

class BookCompleteActivity : BackButtonActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_complete)

        val movieBookingData = getMovieBookingSeatInfo()
        displayToastIfDummyData(movieBookingData)
        initView(movieBookingData)
    }

    private fun getMovieBookingSeatInfo(): MovieBookingSeatInfoUIModel {
        return intent.getSerializableCompat(MOVIE_BOOKING_SEAT_INFO_KEY)
            ?: MovieBookingSeatInfoUIModel.dummyData
    }

    private fun displayToastIfDummyData(movieBookingData: MovieBookingSeatInfoUIModel) {
        if (movieBookingData == MovieBookingSeatInfoUIModel.dummyData) {
            Toast.makeText(
                this,
                getString(R.string.cant_get_movie_booking_data),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun initView(movieBookingSeatInfo: MovieBookingSeatInfoUIModel) {
        findViewById<TextView>(R.id.tv_book_movie_title).text =
            movieBookingSeatInfo.movieBookingInfo.movieInfo.title
        findViewById<TextView>(R.id.tv_book_date).text =
            movieBookingSeatInfo.movieBookingInfo.formatBookingTime()
        findViewById<TextView>(R.id.tv_book_person_count).text =
            getString(
                R.string.book_person_count,
                movieBookingSeatInfo.movieBookingInfo.ticketCount,
                movieBookingSeatInfo.seats.joinToString(", ")
            )
        findViewById<TextView>(R.id.tv_book_total_pay).text =
            getString(R.string.book_total_pay, movieBookingSeatInfo.totalPrice)
    }

    companion object {
        fun intent(context: Context, movieBookingSeatInfo: MovieBookingSeatInfoUIModel): Intent {
            val intent = Intent(context, BookCompleteActivity::class.java)
            intent.putExtra(BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY, movieBookingSeatInfo)
            return intent
        }
    }
}
