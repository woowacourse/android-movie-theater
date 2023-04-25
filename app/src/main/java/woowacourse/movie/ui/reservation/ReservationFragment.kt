package woowacourse.movie.ui.reservation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.ReservationModel
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter
import woowacourse.movie.ui.seat.SeatSelectionActivity
import woowacourse.movie.ui.ticket.MovieTicketActivity

class ReservationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reservation, container, false)

        if (ReservationModel.tickets.isNotEmpty()) view.findViewById<TextView>(R.id.reservation_empty).visibility = View.GONE

        val reservationView: RecyclerView = view.findViewById(R.id.reservation_recyclerview)
        reservationView.adapter = ReservationAdapter(ReservationModel.tickets) {
            val intentToMovieTicket = Intent(context, MovieTicketActivity::class.java).apply {
                putExtra(SeatSelectionActivity.KEY_TICKET, ReservationModel.tickets[it])
            }
            startActivity(intentToMovieTicket)
        }

        return view
    }
}