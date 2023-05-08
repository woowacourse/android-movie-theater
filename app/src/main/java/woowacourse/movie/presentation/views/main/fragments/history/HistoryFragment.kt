package woowacourse.movie.presentation.views.main.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woowacourse.data.database.reservation.ReservationDatabase
import com.woowacourse.data.database.reservation.history.dao.ReservationDao
import com.woowacourse.data.datasource.history.local.LocalHistoryDataSource
import com.woowacourse.data.repository.history.local.LocalHistoryRepository
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.views.main.fragments.history.contract.HistoryContract
import woowacourse.movie.presentation.views.main.fragments.history.contract.presenter.HistoryPresenter
import woowacourse.movie.presentation.views.main.fragments.history.recyclerview.HistoryListAdapter
import woowacourse.movie.presentation.views.ticketingresult.TicketingResultActivity

class HistoryFragment : Fragment(R.layout.fragment_history), HistoryContract.View {
    override val presenter: HistoryContract.Presenter by lazy {
        HistoryPresenter(
            view = this,
            historyRepository = LocalHistoryRepository(
                LocalHistoryDataSource(ReservationDao(ReservationDatabase(requireContext())))
            )
        )
    }

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.presenter = presenter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAdapter = HistoryListAdapter()
    }

    fun addHistory(reservation: Reservation?) {
        reservation?.let { presenter.addHistory(it) }
    }

    override fun showMoreHistory(item: ListItem) {
        (binding.historyRecyclerView.adapter as HistoryListAdapter).append(item)
    }

    override fun showMoreHistories(items: List<ListItem>) {
        (binding.rvAdapter as HistoryListAdapter).appendAll(items)
    }

    override fun showTicketingResultScreen(item: ListItem) {
        requireContext().startActivity(TicketingResultActivity.getIntent(requireContext(), item))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        internal const val TAG = "HistoryFragment"
    }
}
