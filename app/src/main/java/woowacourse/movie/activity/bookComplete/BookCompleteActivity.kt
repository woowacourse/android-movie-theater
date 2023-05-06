package woowacourse.movie.activity.bookComplete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.movie.MovieBookingSeatInfoUIModel
import woowacourse.movie.movie.toDomain

class BookCompleteActivity : BackButtonActivity(), BookCompleteContract.View {

    override lateinit var presenter: BookCompleteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_complete)
        presenter = BookCompletePresenter(this, getMovieBookingSeatInfo().toDomain())
        presenter.hasDummyData()
    }

    private fun getMovieBookingSeatInfo(): MovieBookingSeatInfoUIModel {
        return intent.getSerializableCompat(MOVIE_BOOKING_SEAT_INFO_KEY)
            ?: MovieBookingSeatInfoUIModel.dummyData
    }

    override fun initView(movieBookingSeatInfo: MovieBookingSeatInfoUIModel) {
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

    override fun displayToastIfDummyData() {
        Toast.makeText(
            this,
            getString(R.string.cant_get_movie_booking_data),
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        private const val MOVIE_BOOKING_SEAT_INFO_KEY = "movieBookingSeatInfo"
        fun getIntent(context: Context, movieBookingSeatInfo: MovieBookingSeatInfoUIModel): Intent {
            val intent = Intent(context, BookCompleteActivity::class.java)
            intent.putExtra(MOVIE_BOOKING_SEAT_INFO_KEY, movieBookingSeatInfo)
            return intent
        }
    }
}
