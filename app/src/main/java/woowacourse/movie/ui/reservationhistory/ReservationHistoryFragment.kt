package woowacourse.movie.ui.reservationhistory

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.db.reservationhistory.ReservationHistory
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase
import woowacourse.movie.ui.reservationhistory.adapter.ReservationHistoryAdapter
import woowacourse.movie.ui.reservationhistorydetail.ReservationHistoryDetailActivity

interface ReservationHistoryActionHandler {
    fun moveToReservationHistoryDetailActivity(reservationHistoryId: Long)
}

class ReservationHistoryFragment : Fragment(), ReservationHistoryContract.View, ReservationHistoryActionHandler {
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding: FragmentReservationHistoryBinding
        get() = requireNotNull(_binding)
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter

    private val presenter: ReservationHistoryContract.Presenter by lazy {
        ReservationHistoryPresenter(this, ReservationHistoryDatabase.getInstance(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        presenter.loadReservationHistories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showReservationHistories(reservationHistories: List<ReservationHistory>) {
        reservationHistoryAdapter.submitList(reservationHistories)
    }

    private fun initAdapter() {
        reservationHistoryAdapter =
            ReservationHistoryAdapter(this)

        binding.adapter = reservationHistoryAdapter
        binding.reservationHistoryList.addItemDecoration(DividerItemDecoration(requireContext(), HORIZONTAL))
    }

    override fun moveToReservationHistoryDetailActivity(reservationHistoryId: Long) {
        startActivity(
            ReservationHistoryDetailActivity.newIntent(
                requireContext(),
                reservationHistoryId,
            ),
        )
    }
}
