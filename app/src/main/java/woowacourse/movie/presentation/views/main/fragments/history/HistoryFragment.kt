package woowacourse.movie.presentation.views.main.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

class HistoryFragment : Fragment(R.layout.fragment_history), HistoryContract.View {
    override val presenter: HistoryContract.Presenter by lazy {
        HistoryPresenter(
            historyRepository = LocalHistoryRepository(
                LocalHistoryDataSource(ReservationDao(requireContext()))
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
        presenter.attach(this)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.presenter = presenter
        return binding.root
    }

    fun addHistory(reservation: Reservation?) {
        reservation?.let { presenter.addHistory(it) }
    }

    override fun showMoreHistory(item: ListItem) {
        (binding.historyRecyclerView.adapter as HistoryListAdapter).append(item)
    }

    override fun onDestroyView() {
        _binding = null
        presenter.detach()
        super.onDestroyView()
    }

    companion object {
        internal const val TAG = "HistoryFragment"
    }
}
