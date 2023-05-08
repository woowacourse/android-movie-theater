package woowacourse.movie.presentation.ui.main.fragments.history

import android.os.Bundle
import android.view.View
import com.woowacourse.data.database.reservation.ReservationDatabase
import com.woowacourse.data.database.reservation.history.dao.ReservationDao
import com.woowacourse.data.datasource.history.local.LocalHistoryDataSource
import com.woowacourse.data.repository.history.local.LocalHistoryRepository
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.ui.main.fragments.history.contract.HistoryContract
import woowacourse.movie.presentation.ui.main.fragments.history.contract.presenter.HistoryPresenter
import woowacourse.movie.presentation.ui.main.fragments.history.recyclerview.HistoryListAdapter
import woowacourse.movie.presentation.ui.ticketingresult.TicketingResultActivity

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(), HistoryContract.View {
    override val layoutResId: Int = R.layout.fragment_history
    override val presenter: HistoryContract.Presenter by lazy {
        HistoryPresenter(
            view = this,
            historyRepository = LocalHistoryRepository(
                LocalHistoryDataSource(ReservationDao(ReservationDatabase(requireContext())))
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.presenter = presenter
        binding.rvAdapter = HistoryListAdapter()
    }

    fun addHistory(reservation: Reservation?) {
        reservation?.let { presenter.addHistory(it) }
    }

    override fun showMoreHistory(item: ListItem) {
        binding.rvAdapter?.append(item)
    }

    override fun showMoreHistories(items: List<ListItem>) {
        binding.rvAdapter?.appendAll(items)
    }

    override fun showTicketingResultScreen(item: ListItem) {
        requireContext().startActivity(TicketingResultActivity.getIntent(requireContext(), item))
    }

    companion object {
        internal const val TAG = "HistoryFragment"
    }
}
