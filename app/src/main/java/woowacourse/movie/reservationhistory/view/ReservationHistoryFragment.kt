package woowacourse.movie.reservationhistory.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.data.db.ReservationHistoryDatabase
import woowacourse.movie.data.db.ReservationHistoryEntity
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.reservationhistory.presenter.ReservationHistoryContract
import woowacourse.movie.reservationhistory.presenter.ReservationHistoryPresenter
import woowacourse.movie.reservationhistory.view.adapter.ReservationHistoryAdapter
import woowacourse.movie.reservationhistory.view.listener.ReservationHistoryClickListener
import woowacourse.movie.result.view.MovieResultActivity

class ReservationHistoryFragment :
    Fragment(),
    ReservationHistoryContract.View,
    ReservationHistoryClickListener {
    private lateinit var binding: FragmentReservationHistoryBinding
    private lateinit var reservationHistoryPresenter: ReservationHistoryPresenter
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentReservationHistoryBinding.inflate(inflater, container, false)

        initializeReservationHistoryFragment()
        return binding.root
    }

    private fun initializeReservationHistoryFragment() {
        reservationHistoryAdapter = ReservationHistoryAdapter(this)
        binding.recyclerViewHistory.adapter = reservationHistoryAdapter

        val divider = DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        binding.recyclerViewHistory.addItemDecoration(divider)

        reservationHistoryPresenter =
            ReservationHistoryPresenter(
                this,
                ReservationHistoryDatabase.getInstance(requireActivity()),
            )
        reservationHistoryPresenter.loadReservationHistories()
    }

    override fun onReservationHistoryClick(ticketId: Long) {
        val intent =
            MovieResultActivity.createIntent(
                requireActivity(),
                ticketId,
            )
        startActivity(intent)
    }

    override fun displayReservationHistories(reservationHistories: List<ReservationHistoryEntity>) {
        reservationHistoryAdapter.updateReservationHistory(reservationHistories)
    }
}
