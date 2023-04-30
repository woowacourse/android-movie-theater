package woowacourse.movie.view.moviemain.reservationlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.ReservationMockRepository
import woowacourse.movie.view.ReservationCompletedActivity
import woowacourse.movie.view.mapper.toUiModel

class ReservationListFragment : Fragment(R.layout.fragment_reservation_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val reservations = ReservationMockRepository.findAll().map { it.toUiModel() }
        recyclerView.adapter = ReservationListAdapter(reservations) { reservation ->
            val intent = ReservationCompletedActivity.newIntent(requireContext(), reservation)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG_RESERVATION_LIST = "RESERVATION_LIST"
    }
}
