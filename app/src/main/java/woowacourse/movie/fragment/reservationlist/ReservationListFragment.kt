package woowacourse.movie.fragment.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.view.adapter.ReservationAdapter
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.ReservationsViewData

class ReservationListFragment : Fragment(), ReservationListContract.View {
    override lateinit var presenter: ReservationListContract.Presenter
    private lateinit var reservations: ReservationsViewData
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ReservationListPresenter(this)
        presenter.loadReservationData()

        makeReservationRecyclerView(view, reservations)
    }

    override fun setReservationData(reservationsViewData: ReservationsViewData) {
        this.reservations = reservationsViewData
    }

    private fun makeReservationRecyclerView(view: View, reservations: ReservationsViewData) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.reservation_list_recycler)
        recyclerView.adapter = ReservationAdapter(reservations, ::onClickItem)
        val decoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    private fun onClickItem(view: View, reservation: ReservationViewData) {
        startActivity(
            ReservationResultActivity.from(
                view.context, reservation
            )
        )
    }

    companion object {
        fun from(reservationsViewData: ReservationsViewData) = Bundle().apply {
            putSerializable(
                ReservationsViewData.RESERVATIONS_VIEW_DATA_EXTRA_NAME, reservationsViewData
            )
        }
    }
}
