package woowacourse.movie.presentation.activities.main.fragments.history

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.woowacourse.data.database.reservation.history.dao.ReservationDao
import com.woowacourse.data.datasource.history.local.LocalHistoryDataSource
import com.woowacourse.data.repository.history.local.LocalHistoryRepository
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.history.contract.HistoryContract
import woowacourse.movie.presentation.activities.main.fragments.history.contract.presenter.HistoryPresenter
import woowacourse.movie.presentation.activities.main.fragments.history.recyclerview.HistoryListAdapter
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem

class HistoryFragment : Fragment(R.layout.fragment_history), HistoryContract.View {
    override val presenter: HistoryContract.Presenter by lazy { makePresenter() }

    private val historyAdapter by lazy { HistoryListAdapter(presenter::onClickItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attach(this)
        addHistoryFromBundle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun addHistoryFromBundle() {
        arguments?.getParcelableCompat<Reservation>(RESERVATION_KEY)?.let {
            presenter.addHistory(it)
        }
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

    override fun showHistories(items: List<ListItem>) {
        historyAdapter.appendAll(items)
    }

    override fun showMoreHistory(item: ListItem) {
        historyAdapter.append(item)
    }

    override fun showDetails(item: ListItem) {
        startActivity(TicketingResultActivity.getIntent(requireContext(), item))
    }

    private fun makePresenter(): HistoryContract.Presenter = HistoryPresenter(
        historyRepository = LocalHistoryRepository(
            LocalHistoryDataSource(ReservationDao(requireContext()))
        )
    )

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    companion object {
        private val historyFragment = HistoryFragment()
        private const val RESERVATION_KEY = "reservation_key"

        fun newInstance(reservation: Reservation? = null): HistoryFragment = historyFragment.apply {
            arguments = bundleOf(RESERVATION_KEY to reservation)
        }
    }
}
