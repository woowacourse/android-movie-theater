package woowacourse.movie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Mapper.toUiModel
import woowacourse.movie.reservation.ReservationRepository
import woowacourse.movie.ui.completed.CompletedActivity

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
        adapter.initList(ReservationRepository.getReservations().map { it.toUiModel() })
    }

    private fun itemClicked(id: Long) {
        val intent = CompletedActivity.getIntent(
            context = requireActivity(),
            reservation = ReservationRepository.getReservation(id).toUiModel(),
        )
        startActivity(intent)
    }
}
