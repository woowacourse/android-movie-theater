package woowacourse.movie.view.activities.home.fragments.reservationlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.activities.reservationresult.ReservationResultActivity

class ReservationListFragment : Fragment(), ReservationListContract.View {

    private val presenter: ReservationListContract.Presenter = ReservationListPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadReservations()
    }

    override fun setReservationList(reservationListViewItemUIStates: List<ReservationListViewItemUIState>) {
        val reservationListView = view?.findViewById<RecyclerView>(R.id.rv_reservation_list) ?: return
        reservationListView.adapter =
            ReservationListAdapter(reservationListViewItemUIStates) { reservationId ->
                ReservationResultActivity.startActivity(reservationListView.context, reservationId)
            }
    }
}
