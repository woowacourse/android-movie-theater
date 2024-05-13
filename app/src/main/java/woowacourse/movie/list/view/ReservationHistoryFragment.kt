package woowacourse.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.list.adapter.ReservationHistoryAdapter
import woowacourse.movie.list.adapter.ReservationOnItemClickListener
import woowacourse.movie.list.contract.ReservationHistoryContract
import woowacourse.movie.list.presenter.ReservationHistoryPresenter
import woowacourse.movie.ticket.model.TicketEntity
import woowacourse.movie.ticket.view.MovieTicketActivity

class ReservationHistoryFragment :
    Fragment(),
    ReservationHistoryContract.View,
    ReservationOnItemClickListener {
    private lateinit var binding: FragmentReservationHistoryBinding
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter
    private var presenter = ReservationHistoryPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReservationHistoryBinding.inflate(inflater, container, false)
        presenter.loadDataFromDb()
        return binding.root
    }

    override fun showData(tickets: List<TicketEntity>) {
        reservationHistoryAdapter = ReservationHistoryAdapter(tickets, this)
        binding.reservationHistoryRecyclerView.adapter = reservationHistoryAdapter
    }

    override fun onClick(id: Long) {
        val intent = MovieTicketActivity.newTicketActivityInstance(requireContext(), id)
        startActivity(intent)
    }
}
