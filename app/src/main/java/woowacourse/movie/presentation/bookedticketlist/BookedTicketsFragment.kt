package woowacourse.movie.presentation.bookedticketlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.bookedticket.TicketDBHelper
import woowacourse.movie.data.bookedticket.TicketsDBAdapter
import woowacourse.movie.data.movie.MockMovieData
import woowacourse.movie.databinding.FragmentBookedTicketsBinding
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.TicketModel
import java.time.LocalDateTime

class BookedTicketsFragment : Fragment(), BookedTicketsContract.View {

    private var _binding: FragmentBookedTicketsBinding? = null
    private val binding get() = _binding!!

    private var _presenter: BookedTicketsContract.Presenter? = null
    override val presenter get() = _presenter!!

    private lateinit var bookedTicketsAdapter: BookedTicketsAdapter

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

        initBookedTicketsPresenter()
        initBookedTicketAdapter()
        presenter.requestBookedTickets()
    }

    private fun initBookedTicketsPresenter() {
        _presenter = BookedTicketsPresenter(
            this,
            TicketsDBAdapter(TicketDBHelper(requireContext())),
            MockMovieData,
        )
    }

    private fun initBookedTicketAdapter() {
        bookedTicketsAdapter = BookedTicketsAdapter(
            ::bookedTicketsItemClickListener,
            presenter::getMovieModel,
        )
    }

    override fun setBookedTickets(tickets: List<TicketModel>) {
        binding.recyclerBookedTickets.adapter =
            bookedTicketsAdapter
        if (tickets == emptyList<TicketModel>()) {
            bookedTicketsAdapter.submitList(emptyTicketModel)
        } else {
            bookedTicketsAdapter.submitList(tickets)
        }
    }

    private fun bookedTicketsItemClickListener(ticketModel: TicketModel) {
        val intent = CompleteActivity.getIntent(requireActivity(), ticketModel)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _presenter = null
    }

    companion object {
        private val emptyTicketModel = listOf(
            TicketModel(
                1,
                "티켓이 하나도 없습니다",
                LocalDateTime.of(9999, 9, 9, 9, 9),
                1,
                0,
                listOf("좌석 없음"),
            ),
        )
    }
}
