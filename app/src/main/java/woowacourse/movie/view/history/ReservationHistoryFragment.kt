package woowacourse.movie.view.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.presenter.history.ReservationHistoryContract
import woowacourse.movie.presenter.history.ReservationHistoryPresenter
import woowacourse.movie.repository.ReservationTicketRepositoryImpl
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.history.adapter.ReservationTicketAdapter
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.RESERVATION_TICKET_ID

class ReservationHistoryFragment : Fragment(), ReservationHistoryContract.View {
    private lateinit var presenter: ReservationHistoryPresenter
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding: FragmentReservationHistoryBinding get() = _binding!!
    private lateinit var reservationTicketAdapter: ReservationTicketAdapter
    private lateinit var mActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MainActivity
        presenter =
            ReservationHistoryPresenter(
                this@ReservationHistoryFragment,
                ReservationTicketRepositoryImpl(context),
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_reservation_history,
                container,
                false,
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initReservationTicketRecyclerView()
        presenter.loadReservationTickets()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initReservationTicketRecyclerView() {
        reservationTicketAdapter =
            ReservationTicketAdapter { reservationTicket ->
                presenter.loadReservationTicket(reservationTicket)
            }
        binding.recyclerViewHistory.apply {
            adapter = reservationTicketAdapter
        }
    }

    override fun navigateToDetail(reservationTicket: ReservationTicket) {
        val intent =
            mActivity.intent.apply {
                putExtra(RESERVATION_TICKET_ID, reservationTicket.ticketId)
            }
        startActivity(intent)
    }

    override fun showReservationHistory(tickets: List<ReservationTicket>) {
        view?.post {
            reservationTicketAdapter.updateTickets(tickets)
        }
    }
}
