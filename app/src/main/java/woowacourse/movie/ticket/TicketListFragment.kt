package woowacourse.movie.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.R
import woowacourse.movie.RepositoryProvider
import woowacourse.movie.booking.complete.BookingCompleteActivity
import woowacourse.movie.databinding.FragmentTicketListBinding
import woowacourse.movie.ui.model.TicketUiModel

class TicketListFragment : Fragment(), TicketListContract.View {
    private val presenter = TicketListPresenter(this, RepositoryProvider.movieDatabase)
    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket_list, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter.initializeData()
    }

    override fun setUpReservationList(reservations: List<TicketUiModel>) {
        binding.rvReservation.adapter =
            TicketAdapter(reservations) { ticket ->
                val intent = BookingCompleteActivity.newIntent(requireContext(), ticket)
                startActivity(intent)
            }
        binding.rvReservation.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
