package woowacourse.movie.presentation.bookedticketlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.BookedTickets
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketsFragment : Fragment() {

    private val bookedTicketsAdapter by lazy { BookedTicketsAdapter(::bookedTicketsItemClickListener) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booked_tickets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("멧돼지", "테스트 들어오고있음")
        setBookedTicketsAdapter()
    }

    private fun setBookedTicketsAdapter() {
        requireActivity().findViewById<RecyclerView>(R.id.recyclerBookedTickets).adapter =
            bookedTicketsAdapter
        Log.d("멧돼지", BookedTickets.tickets.toString())
        bookedTicketsAdapter.submitList(BookedTickets.tickets)
    }

    private fun bookedTicketsItemClickListener(ticketModel: TicketModel) {
        val intent = CompleteActivity.getIntent(requireActivity(), ticketModel)
        startActivity(intent)
    }
}
