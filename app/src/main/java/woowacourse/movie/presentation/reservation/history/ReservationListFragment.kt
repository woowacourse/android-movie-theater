package woowacourse.movie.presentation.reservation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.db.ReservationTicketDatabase
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.presentation.reservation.history.adapter.ReservationListAdapter
import woowacourse.movie.presentation.reservation.result.ReservationResultActivity
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.uimodel.TicketUiModel

class ReservationListFragment :
    Fragment(),
    ReservationListContract.View,
    ReservationListContract.ItemListener {
    private var _binding: FragmentReservationListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ReservationListAdapter
    private lateinit var presenter: ReservationListPresenterImpl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            FragmentReservationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val ticketDao = ReservationTicketDatabase.getInstance(requireContext()).reservationDao()
        presenter = ReservationListPresenterImpl(ticketDao = ticketDao, view = this)
        adapter = ReservationListAdapter(emptyList(), this)
        binding.reservationRecyclerView.adapter = adapter

        presenter.loadReservationTickets()
    }

    override fun showReservationTickets(tickets: List<TicketUiModel>) {
        activity?.runOnUiThread {
            adapter.ticketList = tickets
            adapter.notifyDataSetChanged()
        }
    }

    override fun moveToReservationResult(movieTicketUiModel: MovieTicketUiModel) {
        val intent =
            ReservationResultActivity.getIntent(requireContext())
                .apply { putExtra(ReservationResultActivity.INTENT_TICKET, movieTicketUiModel) }
        startActivity(intent)
    }

    override fun onClick(ticketId: Long) {
        presenter.ticketInfo(ticketId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
