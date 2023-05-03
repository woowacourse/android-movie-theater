package woowacourse.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.model.Mapper.toUiModel
import woowacourse.app.ui.completed.CompletedActivity
import woowacourse.app.usecase.reservation.ReservationUseCase
import woowacourse.app.util.shortToast
import woowacourse.data.reservation.ReservationDatabase
import woowacourse.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.R

class BookingHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerBookingHistory)
        val adapter = BookingHistoryAdapter(::itemClicked)
        recyclerView.adapter = adapter
        adapter.initList(ReservationUseCase(ReservationRepositoryImpl(ReservationDatabase)).getReservations().map { it.toUiModel() })
    }

    private fun itemClicked(id: Long) {
        val reservation = ReservationUseCase(ReservationRepositoryImpl(ReservationDatabase)).getReservation(id)
        if (reservation == null) {
            noSuchElement()
            return
        }
        val intent = CompletedActivity.getIntent(
            context = requireActivity(),
            reservation = reservation.toUiModel(),
        )
        startActivity(intent)
    }

    private fun noSuchElement() {
        requireContext().shortToast(R.string.error_no_such_reservation)
    }
}
