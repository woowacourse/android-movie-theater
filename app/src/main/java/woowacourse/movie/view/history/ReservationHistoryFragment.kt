package woowacourse.movie.view.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.model.ticket.toTicket
import woowacourse.movie.presenter.history.ReservationHistoryContract
import woowacourse.movie.presenter.history.ReservationHistoryPresenter
import woowacourse.movie.repository.ReservationTicketRepositoryImpl
import woowacourse.movie.view.result.ReservationResultActivity
import woowacourse.movie.view.history.adapter.ReservationTicketAdapter
import woowacourse.movie.view.reservation.ReservationDetailActivity

class ReservationHistoryFragment : Fragment(), ReservationHistoryContract.View {
    private lateinit var presenter : ReservationHistoryPresenter
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding: FragmentReservationHistoryBinding get() = _binding!!
    private lateinit var reservationTicketAdapter: ReservationTicketAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = ReservationHistoryPresenter(
            this@ReservationHistoryFragment,
            ReservationTicketRepositoryImpl(context),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_reservation_history,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReservationTicketRecyclerView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initReservationTicketRecyclerView(){
        reservationTicketAdapter =
            ReservationTicketAdapter { reservationTicket ->
                presenter.loadReservationTicket(reservationTicket)
            }
    }

    override fun navigateToDetail(reservationTicket: ReservationTicket) {
        Intent(context, ReservationResultActivity::class.java).apply {
            putExtra(ReservationDetailActivity.TICKET, reservationTicket.toTicket())
            startActivity(this)
        }
    }

    override fun showReservationHistory(tickets: List<ReservationTicket>) {
        reservationTicketAdapter.initTickets(tickets)
    }
}
