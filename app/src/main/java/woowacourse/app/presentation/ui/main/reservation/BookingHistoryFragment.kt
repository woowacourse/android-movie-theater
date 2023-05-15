package woowacourse.app.presentation.ui.main.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.data.movie.MovieDao
import woowacourse.app.data.reservation.ReservationDao
import woowacourse.app.data.reservation.ReservationRepositoryImpl
import woowacourse.app.data.reservation.SeatDao
import woowacourse.app.presentation.model.Mapper.toUiModel
import woowacourse.app.presentation.model.Mapper.toUiReservations
import woowacourse.app.presentation.ui.completed.CompletedActivity
import woowacourse.app.presentation.util.shortToast
import woowacourse.domain.reservation.Reservation
import woowacourse.movie.R

class BookingHistoryFragment : Fragment(), ReservationHistoryContract.View {

    override val presenter: ReservationHistoryContract.Presenter by lazy {
        ReservationHistoryPresenter(
            ReservationRepositoryImpl(
                ReservationDao(requireContext()),
                SeatDao(requireContext()),
                MovieDao(requireContext()),
            ),
            this,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchReservations()
    }

    override fun initAdapter(reservations: List<Reservation>) {
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerBookingHistory)
        val adapter = BookingHistoryAdapter { presenter.fetchReservation(it) }
        recyclerView.adapter = adapter
        adapter.initItems(reservations.toUiReservations())
    }

    override fun goCompletedActivity(reservation: Reservation) {
        val intent = CompletedActivity.getIntent(
            context = requireActivity(),
            reservation = reservation.toUiModel(),
        )
        startActivity(intent)
    }

    override fun showNoReservationError() {
        noSuchReservation()
    }

    private fun noSuchReservation() {
        requireContext().shortToast(R.string.error_no_such_reservation)
    }
}
