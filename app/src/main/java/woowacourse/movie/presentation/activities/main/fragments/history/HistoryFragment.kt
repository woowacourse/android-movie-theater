package woowacourse.movie.presentation.activities.main.fragments.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.history.contract.HistoryContract
import woowacourse.movie.presentation.activities.main.fragments.history.contract.presenter.HistoryPresenter
import woowacourse.movie.presentation.activities.main.fragments.history.recyclerview.HistoryListAdapter
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.model.movieitem.ListItem

class HistoryFragment : Fragment(R.layout.fragment_history), HistoryContract.View {
    override val presenter: HistoryContract.Presenter = HistoryPresenter()

    private val historyAdapter = HistoryListAdapter(presenter::onClickItem)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        presenter.attach(this)
    }

    private fun initRecyclerView() {
        val historyRecyclerView =
            view?.findViewById<RecyclerView>(R.id.history_recycler_view) ?: return

        historyRecyclerView.adapter = historyAdapter
        historyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun showExtraHistories(items: List<ListItem>) {
        historyAdapter.appendAll(items)
    }

    override fun showDetails(item: ListItem) {
        startActivity(TicketingResultActivity.getIntent(requireContext(), item))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    companion object {
        private val historyFragment = HistoryFragment()

        fun newInstance(): HistoryFragment = historyFragment
    }
}
