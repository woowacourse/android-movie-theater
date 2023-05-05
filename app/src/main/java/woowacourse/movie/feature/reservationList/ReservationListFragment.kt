package woowacourse.movie.feature.reservationList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.TicketsRepositoryImpl
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.feature.common.OnDataUpdate
import woowacourse.movie.feature.common.adapter.CommonAdapter
import woowacourse.movie.feature.confirm.ReservationConfirmActivity
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.model.TicketsState

class ReservationListFragment : Fragment(), ReservationListContract.View, OnDataUpdate {

    private var _binding: FragmentReservationListBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var presenter: ReservationListContract.Presenter

    private lateinit var adapter: CommonAdapter

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
        presenter = ReservationPresenter(this, TicketsRepositoryImpl)
        adapter = CommonAdapter()
        binding.rvReservation.adapter = adapter
        presenter.loadTicketsItemList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun navigateReservationConfirm(ticketsState: TicketsState) {
        val intent = ReservationConfirmActivity.getIntent(requireContext(), ticketsState)
        startActivity(intent)
    }

    override fun updateItems(items: List<TicketsItemModel>) {
        adapter.setItems(items)
    }

    override fun onUpdateData() {
        presenter.loadTicketsItemList()
    }
}
