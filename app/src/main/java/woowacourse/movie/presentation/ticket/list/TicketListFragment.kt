package woowacourse.movie.presentation.ticket.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.MovieApplication
import woowacourse.movie.databinding.FragmentTicketListBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.presentation.ticket.detail.TicketDetailActivity
import woowacourse.movie.presentation.ticket.list.adapter.TicketAdapter

class TicketListFragment :
    Fragment(),
    TicketListContract.View {
    private var _binding: FragmentTicketListBinding? = null
    private val binding: FragmentTicketListBinding get() = _binding!!
    private lateinit var presenter: TicketListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter =
            TicketListPresenter(
                this,
                (requireActivity().application as MovieApplication).ticketRepository,
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadTicketList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showTicketList(items: List<Ticket>) {
        val adapter = TicketAdapter(items, { presenter.selectTicket(it) })
        binding.recyclerviewTickets.adapter = adapter
        binding.recyclerviewTickets.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayout.VERTICAL,
            ),
        )
    }

    override fun navigateToTicketDetail(ticket: Ticket) {
        val intent = TicketDetailActivity.newIntent(context, ticket)
        startActivity(intent)
    }
}
