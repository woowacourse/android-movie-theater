package woowacourse.movie.movie.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.movie.activity.TicketActivity
import woowacourse.movie.movie.dto.BookingHistoryDto
import woowacourse.movie.movie.dto.movie.BookingMovieDto
import woowacourse.movie.movie.history.HistoryAdapter
import woowacourse.movie.movie.movielist.OnClickListener

class HistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        setUpHistoryData(view)

        return view
    }

    private fun setUpHistoryData(view: View) {
        val historyRecyclerView = view.findViewById<RecyclerView>(R.id.history_rv)
        val historyAdapter = HistoryAdapter(
            BookingHistoryDto.getHistory(),
        )

        historyRecyclerView.adapter = historyAdapter
        historyAdapter.itemViewClick = object : OnClickListener<BookingMovieDto> {
            override fun onClick(item: BookingMovieDto) {
                val intent = Intent(context, TicketActivity::class.java)
                intent.putExtra(BOOKING_MOVIE_KEY, item)
                startActivity(intent)
            }
        }
    }

    companion object {
        private const val BOOKING_MOVIE_KEY = "booking_movie"
    }
}
