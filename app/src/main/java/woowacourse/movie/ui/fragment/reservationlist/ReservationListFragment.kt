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
import woowacourse.movie.contract.reservationlist.ReservationListContract
import woowacourse.movie.presenter.reservationlist.ReservationListPresenter
import woowacourse.movie.ui.activity.MovieTicketActivity
import woowacourse.movie.ui.entity.Reservations
import woowacourse.movie.ui.fragment.reservationlist.adapter.ReservationAdapter
import woowacourse.movie.ui.model.MovieTicketModel

class ReservationListFragment : Fragment(), ReservationListContract.View {
    private lateinit var reservationView: RecyclerView
    private lateinit var reservationAdapter: ReservationAdapter

    override lateinit var presenter: ReservationListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(KEY_UPDATE_RESERVATION_ITEM) { _, _ ->
            if (::presenter.isInitialized) presenter.setItemsInsertion()
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

        presenter = ReservationListPresenter(this)

        reservationView = view.findViewById(R.id.rv_reservation)
        presenter.setupReservations(Reservations.getAll())
        presenter.loadReservations()
    }

    override fun setReservationView(movieTickets: List<MovieTicketModel>) {
        reservationView.addItemDecoration(
            DividerItemDecoration(
                reservationView.context,
                LinearLayoutManager.VERTICAL
            )
        )
        reservationAdapter = ReservationAdapter(movieTickets) { moveToTicketActivity(it) }
        reservationView.adapter = reservationAdapter
    }

    override fun updateReservationViewItem(itemSize: Int, diffSize: Int) {
        reservationAdapter.notifyItemRangeChanged(itemSize, diffSize)
    }

    private fun moveToTicketActivity(ticketModel: MovieTicketModel) {
        val intent = MovieTicketActivity.createIntent(requireActivity(), ticketModel)
        startActivity(intent)
    }

    companion object {
        internal const val KEY_UPDATE_RESERVATION_ITEM = "update_reservation"
    }
}
