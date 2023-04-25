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
import woowacourse.movie.adapter.ReservationAdapter

class ReservationListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val reservations = MockReservationsFactory.makeReservations()
        val view = inflater.inflate(R.layout.fragment_reservation_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.reservation_recycler_view)
        recyclerView.adapter = ReservationAdapter(reservations)
        return view
    }

}
