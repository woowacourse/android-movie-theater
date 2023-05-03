package woowacourse.movie.feature.reservationList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.TicketsRepositoryImpl
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.feature.common.OnDataUpdate
import woowacourse.movie.feature.confirm.ReservationConfirmActivity
import woowacourse.movie.feature.reservationList.adapter.ReservationListAdapter
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.model.TicketsState

class ReservationListFragment : Fragment(), OnDataUpdate {

    private var _binding: FragmentReservationListBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var adapter: ReservationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ReservationListAdapter()
        // TODO: 화면 회전 시 데이터 불러와야 함
        binding.rvReservation.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTicketsItemModel(): List<TicketsItemModel> {
        return TicketsRepositoryImpl.allTickets().map {
            it.convertToItemModel { tickets -> navigateReservationConfirm(tickets) }
        }
    }

    private fun navigateReservationConfirm(ticketsState: TicketsState) {
        val intent = ReservationConfirmActivity.getIntent(requireContext(), ticketsState)
        startActivity(intent)
    }

    override fun onUpdateData() {
        adapter.setItemChanged(getTicketsItemModel())
    }
}
