package woowacourse.movie.ui.fragment.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.ui.activity.MovieTicketActivity
import woowacourse.movie.ui.entity.Reservations
import woowacourse.movie.ui.fragment.reservationlist.adapter.ReservationAdapter
import woowacourse.movie.ui.model.MovieTicketModel

class ReservationListFragment : Fragment() {
    private lateinit var reservationAdapter: ReservationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_reservation_list, container, false)

        val reservationView = view.findViewById<RecyclerView>(R.id.rv_reservation)
        setReservationView(reservationView)

        return view
    }

    override fun onResume() {
        super.onResume()
        reservationAdapter.notifyDataSetChanged()
    }

    private fun setReservationView(reservationView: RecyclerView) {
        reservationView.addItemDecoration(DividerItemDecoration(reservationView.context, LinearLayoutManager.VERTICAL))
        reservationAdapter = ReservationAdapter(Reservations.getAll()) { moveToTicketActivity(it) }
        reservationView.adapter = reservationAdapter
    }

    private fun moveToTicketActivity(ticketModel: MovieTicketModel) {
        val intent = MovieTicketActivity.createIntent(requireActivity(), ticketModel)
        startActivity(intent)
    }
}
