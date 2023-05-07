package woowacourse.movie.presentation.bookedticketlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.bookedticket.MockBookedTicketsData
import woowacourse.movie.data.movie.MockMovieData
import woowacourse.movie.databinding.FragmentBookedTicketsBinding
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketsFragment : Fragment(), BookedTicketsContract.View {

    private var _binding: FragmentBookedTicketsBinding? = null
    private val binding get() = _binding!!

    override val presenter: BookedTicketsContract.Presenter by lazy {
        BookedTicketsPresenter(this, MockBookedTicketsData, MockMovieData)
    }
    private val bookedTicketsAdapter by lazy {
        BookedTicketsAdapter(
            ::bookedTicketsItemClickListener,
            presenter::getMovieModel,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookedTicketsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setBookedTickets()
    }

    override fun setBookedTicketsAdapter(tickets: List<TicketModel>) {
        binding.recyclerBookedTickets.adapter =
            bookedTicketsAdapter
        bookedTicketsAdapter.submitList(tickets)
    }

    private fun bookedTicketsItemClickListener(ticketModel: TicketModel) {
        val intent = CompleteActivity.getIntent(requireActivity(), ticketModel)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
