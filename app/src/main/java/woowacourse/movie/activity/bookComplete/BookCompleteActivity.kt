package woowacourse.movie.activity.bookComplete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.databinding.ActivityBookCompleteBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.BookingHistoryData
import woowacourse.movie.model.MovieBookingSeatInfoUIModel
import woowacourse.movie.model.TicketData
import woowacourse.movie.model.toHistoryData

class BookCompleteActivity : BackButtonActivity(), BookCompleteContract.View {

    override lateinit var presenter: BookCompleteContract.Presenter
    private lateinit var binding: ActivityBookCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val historyData = getMovieHistoryData()

        presenter = BookCompletePresenter(this, historyData)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_complete)

        presenter.initView()
        presenter.hasDummyData()
    }

    private fun getMovieHistoryData(): BookingHistoryData {
        return intent.getSerializableCompat(MOVIE_BOOKING_TICKET_KEY)
            ?: BookingHistoryData("데이터를 불러올 수 없습니다.", "", 0, listOf(), "", "")
    }

    override fun initView(ticketData: TicketData) {
        val data: BookingHistoryData =
            if (ticketData !is BookingHistoryData) {
                (ticketData as MovieBookingSeatInfoUIModel).toHistoryData()
            } else {
                ticketData
            }
        binding.data = data
    }

    override fun displayToastIfDummyData() {
        Toast.makeText(
            this,
            getString(R.string.cant_get_movie_booking_data),
            Toast.LENGTH_LONG,
        ).show()
    }

    companion object {
        private const val MOVIE_BOOKING_TICKET_KEY = "movieBookingHistoryData"

        fun getIntent(context: Context, movieBookingData: BookingHistoryData): Intent {
            val intent = Intent(context, BookCompleteActivity::class.java)
            intent.putExtra(MOVIE_BOOKING_TICKET_KEY, movieBookingData)
            return intent
        }
    }
}
