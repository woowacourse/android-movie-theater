package woowacourse.movie.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.ui.model.TicketUiModel

class TicketListFragment : Fragment(), TicketListContract.View {
    private val presenter = TicketListPresenter(this)
    private var _binding: FragmentReservationListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation_list, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter.initializeData(requireActivity().applicationContext)
    }

    override fun setUpReservationList(reservations: List<TicketUiModel>) {
        binding.rvReservation.adapter = TicketAdapter(reservations)
        binding.rvReservation.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager(context).orientation))
    }
}
