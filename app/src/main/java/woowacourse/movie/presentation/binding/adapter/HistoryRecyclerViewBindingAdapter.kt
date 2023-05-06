package woowacourse.movie.presentation.binding.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.views.main.fragments.history.contract.HistoryContract
import woowacourse.movie.presentation.views.main.fragments.history.recyclerview.HistoryListAdapter
import woowacourse.movie.presentation.views.ticketingresult.TicketingResultActivity

@BindingAdapter("app:presenter")
fun RecyclerView.setPresenter(presenter: HistoryContract.Presenter) {
    if (adapter == null) {
        val historyAdapter = HistoryListAdapter { item ->
            context.startActivity(TicketingResultActivity.getIntent(context, item))
        }
        adapter = historyAdapter
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        historyAdapter.appendAll(presenter.loadHistories())
    }
}
