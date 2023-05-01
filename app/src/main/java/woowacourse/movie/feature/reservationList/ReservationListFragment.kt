package woowacourse.movie.feature.reservationList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.feature.confirm.ReservationConfirmActivity
import woowacourse.movie.feature.reservationList.adapter.ReservationListAdapter
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.model.TicketsState

class ReservationListFragment : Fragment() {

    private lateinit var reservationRecyclerView: RecyclerView
    private lateinit var adapter: ReservationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reservationRecyclerView = view.findViewById(R.id.reservation_rv)
        adapter = ReservationListAdapter(getTicketsItemModel())
        reservationRecyclerView.adapter = adapter
    }

    fun updateData() {
        adapter.setItemChanged(getTicketsItemModel())
    }

    private fun getTicketsItemModel(): List<TicketsItemModel> {
        return TicketsRepository.allTickets().map {
            it.convertToItemModel { position ->
                navigateReservationConfirm(adapter.reservations[position].ticketsState)
            }
        }
    }

    private fun navigateReservationConfirm(ticketsState: TicketsState) {
        val intent = ReservationConfirmActivity.getIntent(requireContext(), ticketsState)
        startActivity(intent)
    }
}
