package woowacourse.movie.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import domain.BookingMovie
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.ticket.model.BookingMovieModel
import woowacourse.movie.mapper.movie.mapToDomain
import woowacourse.movie.mapper.movie.mapToUIModel
import woowacourse.movie.movielist.OnClickListener
import woowacourse.movie.ticket.view.TicketActivity

class HistoryFragment : Fragment(), HistoryContract.View {
    private lateinit var presenter: HistoryContract.Presenter
    private lateinit var binding: FragmentHistoryBinding

    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setUpBinding()
        presenter = HistoryPresenter(this)
        setUpHistoryData(presenter.getHistory())
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        presenter.getHistory()
    }

    private fun setUpBinding() {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
    }

    override fun setUpHistoryData(history: List<BookingMovieModel>) {
        val historyAdapter = HistoryAdapter(history.map { it.mapToDomain() })

        adapter = historyAdapter
        binding.historyRv.adapter = adapter
        historyAdapter.itemViewClick = object : OnClickListener<BookingMovie> {
            override fun onClick(item: BookingMovie) {
                val intent = Intent(context, TicketActivity::class.java)
                intent.putExtra(BOOKING_MOVIE_KEY, item.mapToUIModel())
                startActivity(intent)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        adapter = HistoryAdapter(presenter.getHistory().map { it.mapToDomain() })
    }

    companion object {
        private const val BOOKING_MOVIE_KEY = "booking_movie"
    }
}
