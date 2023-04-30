package woowacourse.movie.ui.fragment.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.entity.Reservations
import woowacourse.movie.ui.activity.MovieTicketActivity
import woowacourse.movie.ui.fragment.reservationlist.adapter.ReservationAdapter
import woowacourse.movie.ui.model.MovieTicketModel

class ReservationListFragment : Fragment() {
    private lateinit var reservationAdapter: ReservationAdapter
    private var itemsCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_KEY_ITEM_INSERTION) { _, _ ->
            applyItemsInsertion()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_reservation_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reservationView = view.findViewById<RecyclerView>(R.id.rv_reservation)
        setReservationView(reservationView)
    }

    private fun setReservationView(reservationView: RecyclerView) {
        reservationView.addItemDecoration(DividerItemDecoration(reservationView.context, LinearLayoutManager.VERTICAL))
        reservationAdapter = ReservationAdapter(Reservations.getAll()) { moveToTicketActivity(it) }
        reservationView.adapter = reservationAdapter
    }

    private fun moveToTicketActivity(ticketModel: MovieTicketModel) {
        val intent = MovieTicketActivity.createIntent(requireActivity(), ticketModel)
        startActivity(intent)
    }

    private fun applyItemsInsertion() {
        val countDifference = Reservations.getSize() - itemsCount
        if (countDifference > 0) {
            reservationAdapter.notifyItemRangeInserted(itemsCount, countDifference)
            itemsCount = Reservations.getSize()
        }
    }

    companion object {
        const val REQUEST_KEY_ITEM_INSERTION = "item_insertion"
    }
}
