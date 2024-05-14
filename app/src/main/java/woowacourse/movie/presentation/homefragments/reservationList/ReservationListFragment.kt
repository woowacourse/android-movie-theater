package woowacourse.movie.presentation.homefragments.reservationList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.database.ReservationDatabase
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.model.Ticket
import woowacourse.movie.presentation.homefragments.reservationList.uiModel.ReservationItemUiModel
import woowacourse.movie.presentation.ticketingResult.TicketingResultActivity
import woowacourse.movie.repository.DummyTheaterList

class ReservationListFragment :
    Fragment(),
    ReservationListContract.View,
    ReservationListClickListener {
    private var _binding: FragmentReservationListBinding? = null
    val binding: FragmentReservationListBinding
        get() = _binding!!

    private val presenter: ReservationListContract.Presenter =
        ReservationListPresenter(this, DummyTheaterList)

    private val reservationDatabase by lazy { ReservationDatabase.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        if (reservationDatabase != null) {
            presenter.loadReservations(reservationDatabase!!)
        }
    }

    override fun displayReservations(reservations: List<ReservationItemUiModel>) {
        binding.rvReservations.adapter = ReservationListAdapter(reservations, this)
        binding.rvReservations.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayout.VERTICAL,
            ),
        )
    }

    override fun showErrorToast() {
        Toast.makeText(context, "해당 티켓 정보를 찾을 수 없습니다...", Toast.LENGTH_LONG).show()
    }

    override fun reservationItemClickListener(reservationId: Long) {
        if (reservationDatabase != null) {
            presenter.navigate(reservationId, reservationDatabase!!)
        }
    }

    override fun navigate(ticket: Ticket) {
        startActivity(
            TicketingResultActivity.createIntent(
                requireContext(),
                ticket,
            ),
        )
    }
}
