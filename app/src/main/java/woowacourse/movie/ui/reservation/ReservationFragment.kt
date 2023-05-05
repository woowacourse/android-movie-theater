package woowacourse.movie.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationBinding
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.ReservationTicketMachine
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter
import woowacourse.movie.ui.reservation.presenter.ReservationContract
import woowacourse.movie.ui.reservation.presenter.ReservationContract.Presenter
import woowacourse.movie.ui.reservation.presenter.ReservationPresenter
import woowacourse.movie.ui.seat.SeatSelectionActivity
import woowacourse.movie.ui.ticket.MovieTicketActivity

class ReservationFragment : Fragment(), ReservationContract.View {
    private lateinit var reservationAdapter: ReservationAdapter
    override lateinit var reservationTicket: List<MovieTicketModel>
    override val presenter: Presenter by lazy { ReservationPresenter(this) }
    private var _binding: FragmentReservationBinding? = null
    private val binding: FragmentReservationBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.isEmptyMovieReservation()
        presenter.getReservationTickets()
        initAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        reservationAdapter = ReservationAdapter(reservationTicket, ::moveToMovieTicketActivity)
        binding.reservationRecyclerview.adapter = reservationAdapter
    }

    private fun moveToMovieTicketActivity(position: Int) {
        val intentToMovieTicket = Intent(context, MovieTicketActivity::class.java).apply {
            putExtra(SeatSelectionActivity.KEY_TICKET, ReservationTicketMachine.tickets[position])
        }
        startActivity(intentToMovieTicket)
    }

    override fun setTextOnEmptyState(isEmpty: Boolean) {
        if (!isEmpty) binding.reservationEmpty.visibility = View.GONE
    }
}
