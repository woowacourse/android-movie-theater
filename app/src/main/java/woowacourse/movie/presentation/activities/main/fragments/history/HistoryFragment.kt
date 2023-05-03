package woowacourse.movie.presentation.activities.main.fragments.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation

class HistoryFragment : Fragment(R.layout.fragment_history), HistoryContract.View {
    override lateinit var presenter: HistoryContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HistoryPresenter(this)
        val historyRecyclerView = view.findViewById<RecyclerView>(R.id.history_recycler_view)
        val historyAdapter = HistoryListAdapter { presenter.onClicked(it) }
        historyAdapter.appendAll(Reservation.provideDummy())
        historyRecyclerView.adapter = historyAdapter
        historyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL,
            ),
        )
    }

    override fun setAdapterListener(item: ListItem) {
        when (item) {
            is Reservation -> {
                startActivity(
                    Intent(requireContext(), TicketingResultActivity::class.java)
                        .putExtra(TicketingResultActivity.RESERVATION_KEY, item),
                )
            }
            is Ad -> {}
            is Movie -> {}
        }
    }
}
