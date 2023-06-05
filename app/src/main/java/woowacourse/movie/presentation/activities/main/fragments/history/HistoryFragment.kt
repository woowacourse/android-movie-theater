package woowacourse.movie.presentation.activities.main.fragments.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation
import woowacourse.movie.presentation.model.item.Theater

class HistoryFragment : Fragment(), HistoryContract.View {
    private lateinit var binding: FragmentHistoryBinding
    override lateinit var presenter: HistoryContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = HistoryDbHelper(requireContext())
        presenter = HistoryPresenter(this, db)
        presenter.loadReservationData()
    }

    override fun showReservations(items: List<Reservation>) {
        val historyAdapter = HistoryListAdapter { onClick(it) }
        historyAdapter.appendAll(items)
        binding.historyRecyclerView.adapter = historyAdapter
        binding.historyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL,
            ),
        )
    }

    private fun onClick(item: ListItem) {
        when (item) {
            is Reservation -> {
                startActivity(
                    Intent(requireContext(), TicketingResultActivity::class.java)
                        .putExtra(TicketingResultActivity.RESERVATION_KEY, item),
                )
            }
            is Ad -> {}
            is Movie -> {}
            is Theater -> {}
        }
    }
}
