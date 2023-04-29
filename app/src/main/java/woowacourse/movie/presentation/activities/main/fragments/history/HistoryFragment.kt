package woowacourse.movie.presentation.activities.main.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyRecyclerView = view.findViewById<RecyclerView>(R.id.history_recycler_view)
        val historyAdapter = HistoryListAdapter(::onClickHistoryItem)
        historyAdapter.appendAll(Reservation.provideDummy())
        historyRecyclerView.adapter = historyAdapter
        historyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun onClickHistoryItem(item: ListItem) {
        when (item) {
            is Reservation -> startResultActivity(item)
        }
    }

    private fun startResultActivity(item: Reservation) {
        startActivity(TicketingResultActivity.intent(requireContext(), item))
    }

    companion object {
        private val historyFragment = HistoryFragment()

        fun newInstance(): HistoryFragment = historyFragment
    }
}
