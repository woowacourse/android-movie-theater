package woowacourse.movie.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movielist.OnClickListener
import woowacourse.movie.ticket.TicketActivity

class HistoryFragment : Fragment(), HistoryContract.View {
    override lateinit var presenter: HistoryContract.Presenter
    private lateinit var binding: FragmentHistoryBinding

    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setUpBinding()
        presenter = HistoryPresenter(this)
        presenter.initFragment()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        presenter.initFragment()
    }

    private fun setUpBinding() {
        // binding = DataBindingUtil.setContentView(, R.layout.fragment_history)
        binding = FragmentHistoryBinding.inflate(layoutInflater)
    }

    override fun setUpHistoryData(history: List<BookingMovieEntity>) {
        val historyAdapter = HistoryAdapter(history)

        adapter = historyAdapter
        binding.historyRv.adapter = adapter
        historyAdapter.itemViewClick = object : OnClickListener<BookingMovieEntity> {
            override fun onClick(item: BookingMovieEntity) {
                val intent = Intent(context, TicketActivity::class.java)
                intent.putExtra(BOOKING_MOVIE_KEY, item)
                startActivity(intent)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        adapter = HistoryAdapter(presenter.getHistory())
    }

    companion object {
        private const val BOOKING_MOVIE_KEY = "booking_movie"

        const val TAG_HISTORY_FRAGMENT = "history_fragment"
    }
}
