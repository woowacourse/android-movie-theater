package woowacourse.movie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import domain.Reservation
import woowacourse.movie.MockReservationsFactory
import woowacourse.movie.R
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.adapter.ReservationAdapter
import woowacourse.movie.view.mapper.ReservationMapper

class ReservationListFragment : Fragment(R.layout.fragment_reservation_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reservations = MockReservationsFactory.makeReservations()
        val recyclerView: RecyclerView = view.findViewById(R.id.reservation_recycler_view)
        recyclerView.adapter = ReservationAdapter(reservations, ::reservationItemClick)
    }

    private fun reservationItemClick(reservation: Reservation) {
        val reservationUiModel = ReservationMapper.toUi(reservation)
        val movieUiModel = reservationUiModel.movie
        val ticketsUiModel = reservationUiModel.tickets
        ReservationResultActivity.start(
            requireContext(),
            movieUiModel = movieUiModel,
            ticketsUiModel = ticketsUiModel
        )
    }
}
