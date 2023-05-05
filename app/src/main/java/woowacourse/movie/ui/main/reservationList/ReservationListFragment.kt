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
        initBinding()
        initPresenter()
        return binding.root
    }

    private fun initBinding() {
        binding = FragmentReservationListBinding.inflate(layoutInflater)
    }

    private fun initPresenter() {
        presenter = ReservationListPresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getReservationList()
    }

    override fun setAdapter(tickets: List<TicketsState>) {
        adapter = ReservationListAdapter(
            tickets.map(::TicketsItemModel)
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
