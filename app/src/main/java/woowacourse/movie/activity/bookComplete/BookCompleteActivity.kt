package woowacourse.movie.activity.bookComplete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.BookingHistoryData
import woowacourse.movie.model.MovieBookingSeatInfoUIModel
import woowacourse.movie.model.TicketData
import woowacourse.movie.model.toHistoryData

class BookCompleteActivity : BackButtonActivity(), BookCompleteContract.View {

    override lateinit var presenter: BookCompleteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_complete)
        val movieBookingSeatInfo = getMovieBookingSeatInfo()
        val historyData = getMovieHistoryData()

        presenter = if (movieBookingSeatInfo.seats.isEmpty()) {
            BookCompletePresenter(this, historyData)
        } else {
            BookCompletePresenter(this, movieBookingSeatInfo)
        }

        presenter.initView()
        presenter.hasDummyData()
    }

    private fun getMovieBookingSeatInfo(): MovieBookingSeatInfoUIModel {
        return intent.getSerializableCompat(MOVIE_BOOKING_SEAT_INFO_KEY)
            ?: MovieBookingSeatInfoUIModel.dummyData
    }

    private fun getMovieHistoryData(): BookingHistoryData {
        return intent.getSerializableCompat(MOVIE_BOOKING_HISTORY_KEY)
            ?: BookingHistoryData("데이터를 불러올 수 없습니다.", "", 0, listOf(), "")
    }

    override fun initView(ticketData: TicketData) {
        val data: BookingHistoryData =
            if (ticketData !is BookingHistoryData) {
                (ticketData as MovieBookingSeatInfoUIModel).toHistoryData()
            } else {
                ticketData
            }
        findViewById<TextView>(R.id.tv_book_movie_title).text =
            data.title
        findViewById<TextView>(R.id.tv_book_date).text =
            data.date
        findViewById<TextView>(R.id.tv_book_person_count).text =
            getString(
                R.string.book_person_count,
                data.numberOfPeople,
                data.seat.joinToString(", ")
            )
        findViewById<TextView>(R.id.tv_book_total_pay).text =
            getString(R.string.book_total_pay, data.price)
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
        private const val MOVIE_BOOKING_HISTORY_KEY = "movieBookingHistoryData"
        fun getIntent(context: Context, movieBookingSeatInfo: MovieBookingSeatInfoUIModel): Intent {
            val intent = Intent(context, BookCompleteActivity::class.java)
            intent.putExtra(MOVIE_BOOKING_SEAT_INFO_KEY, movieBookingSeatInfo)
            return intent
        }

        fun getIntent(context: Context, movieBookingData: BookingHistoryData): Intent {
            val intent = Intent(context, BookCompleteActivity::class.java)
            intent.putExtra(MOVIE_BOOKING_HISTORY_KEY, movieBookingData)
            return intent
        }
    }
}
