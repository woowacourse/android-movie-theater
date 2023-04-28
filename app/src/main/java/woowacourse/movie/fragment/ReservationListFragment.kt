package woowacourse.movie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import domain.Reservation
import woowacourse.movie.MockReservationsFactory
import woowacourse.movie.R
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.adapter.ReservationAdapter
import woowacourse.movie.view.mapper.ReservationMapper

class ReservationListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val reservations = MockReservationsFactory.makeReservations()
        val view = inflater.inflate(R.layout.fragment_reservation_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.reservation_recycler_view)
        recyclerView.adapter = ReservationAdapter(reservations, ::reservationItemClick)
        return view
    }

    private fun reservationItemClick(reservation: Reservation) {
        val reservationUiModel = ReservationMapper.toUi(reservation)
        val movieUiModel = reservationUiModel.movie
        val ticketsUiModel = reservationUiModel.tickets
        val context = context ?: throw IllegalStateException(CONTEXT_NOT_FOUND)
        ReservationResultActivity.start(
            context,
            movieUiModel = movieUiModel,
            ticketsUiModel = ticketsUiModel
        )
    }

    companion object {
        private const val CONTEXT_NOT_FOUND = "context를 찾을 수 없습니다."
    }
}
