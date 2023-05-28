package woowacourse.movie.presentation.bookedticketlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.data.local.preference.bookedticket.BookedTicketPreference
import woowacourse.movie.model.data.remote.DummyMovieStorage
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketsFragment() : Fragment(), BookedTicketsContract.View {

    override lateinit var presenter: BookedTicketsContract.Presenter

    private lateinit var bookedTicketsAdapter: BookedTicketsAdapter

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
        initPresenter()
        initAdapter()
        setBookedTicketsAdapter()
    }

    private fun initPresenter() {
        presenter =
            BookedTicketPresenter(DummyMovieStorage(), BookedTicketPreference(requireContext()))
    }

    private fun initAdapter() {
        bookedTicketsAdapter = BookedTicketsAdapter(::bookedTicketsItemClickListener, presenter)
    }

    private fun setBookedTicketsAdapter() {
        requireActivity().findViewById<RecyclerView>(R.id.recyclerBookedTickets).adapter =
            bookedTicketsAdapter
        bookedTicketsAdapter.submitList(presenter.getBookedTickets())
    }

    private fun bookedTicketsItemClickListener(ticketModel: TicketModel) {
        val intent = CompleteActivity.getIntent(requireActivity(), ticketModel)
        startActivity(intent)
    }
}
