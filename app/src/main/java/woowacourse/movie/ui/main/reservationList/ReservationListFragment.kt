package woowacourse.movie.ui.main.reservationList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.adapter.ReservationListAdapter
import woowacourse.movie.ui.confirm.ReservationConfirmActivity
import woowacourse.movie.ui.itemModel.TicketsItemModel

class ReservationListFragment : Fragment(), ReservationListContract.View {
    private lateinit var presenter: ReservationListContract.Presenter
    private lateinit var binding: FragmentReservationListBinding

    private lateinit var adapter: ReservationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpBinding()
        setUpPresenter()
        return binding.root
    }

    private fun setUpBinding() {
        binding = FragmentReservationListBinding.inflate(layoutInflater)
    }

    private fun setUpPresenter() {
        presenter = ReservationListPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReservationListAdapter(
            presenter.getReservationList().map(::TicketsItemModel)
        ) { ticketsItemModel ->
            navigateReservationConfirm(ticketsItemModel.ticketsState)
        }
        binding.reservationList.adapter = adapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        adapter.setItemChanged(
            TicketsRepository.allTickets().map(::TicketsItemModel)
        )
    }

    private fun navigateReservationConfirm(ticketsState: TicketsState) {
        ReservationConfirmActivity.startActivity(activity as Context, ticketsState)
    }
}
