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
import woowacourse.movie.fragment.reservationlist.adapter.ReservationAdapter
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.ReservationsViewData

class ReservationListFragment : Fragment(), ReservationListContract.View {
    override lateinit var presenter: ReservationListContract.Presenter
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
        presenter.initReservationRecyclerView()
    }

    override fun initReservationRecyclerView(reservationsViewData: ReservationsViewData) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.reservation_list_recycler)
        recyclerView.adapter =
            ReservationAdapter(reservationsViewData) { presenter.onItemClick(it) }
        val decoration =
            DividerItemDecoration(requireView().context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    override fun onItemClick(reservationViewData: ReservationViewData) {
        startActivity(
            ReservationResultActivity.from(
                requireContext(), reservationViewData
            )
        )
    }
}
