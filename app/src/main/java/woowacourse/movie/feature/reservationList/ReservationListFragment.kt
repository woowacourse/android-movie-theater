package woowacourse.movie.feature.reservationList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.domain.usecase.GetAllReservationTicketsUseCase
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepositoryImpl
import woowacourse.movie.data.sqlite.ReservationTicketsDao
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.feature.common.OnDataUpdate
import woowacourse.movie.feature.common.Toaster
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

    private lateinit var reservationTicketsDao: ReservationTicketsDao

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
        reservationTicketsDao = ReservationTicketsDao(requireContext())
        presenter =
            ReservationPresenter(
                this,
                GetAllReservationTicketsUseCase(
                    TicketsRepositoryImpl(
                        reservationTicketsDao
                    )
                )
            )
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

    override fun errorLoadReservationTicketsData() {
        Toaster.showToast(requireContext(), getString(R.string.error_load_reservation_tickets_data))
    }

    override fun onUpdateData() {
        presenter.loadTicketsItemList()
    }

    override fun onDestroy() {
        super.onDestroy()
        reservationTicketsDao.close()
    }
}
