package woowacourse.movie.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.ReservationModel
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter
import woowacourse.movie.ui.seat.SeatSelectionActivity
import woowacourse.movie.ui.ticket.MovieTicketActivity

class ReservationFragment : Fragment() {
    private lateinit var reservationView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reservation, container, false)

        setTextOnEmptyState(view)
        initAdapter(view)

        return view
    }

    private fun initAdapter(view: View) {
        reservationView = view.findViewById(R.id.reservation_recyclerview)
        reservationView.adapter = ReservationAdapter(ReservationModel.tickets) {
            moveToMovieTicketActivity(it)
        }
    }

    private fun setTextOnEmptyState(view: View) {
        if (ReservationModel.tickets.isNotEmpty()) {
            view.findViewById<TextView>(R.id.reservation_empty).visibility = View.GONE
        }
    }

    private fun moveToMovieTicketActivity(position: Int) {
        val intentToMovieTicket = Intent(context, MovieTicketActivity::class.java).apply {
            putExtra(SeatSelectionActivity.KEY_TICKET, ReservationModel.tickets[position])
        }
        startActivity(intentToMovieTicket)
    }
}
