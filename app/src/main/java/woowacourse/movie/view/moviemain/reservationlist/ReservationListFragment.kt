package woowacourse.movie.view.moviemain.reservationlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.reservation.ReservationDbRepository
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.reservationcompleted.ReservationCompletedActivity

class ReservationListFragment :
    Fragment(R.layout.fragment_reservation_list),
    ReservationListContract.View {
    override lateinit var presenter: ReservationListContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ReservationListPresenter(this, ReservationDbRepository(requireContext()))
        presenter.fetchReservations()
    }

    override fun showReservations(reservations: List<ReservationUiModel>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView?.adapter = ReservationListAdapter(reservations) { reservation ->
            val intent = ReservationCompletedActivity.newIntent(requireContext(), reservation)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG_RESERVATION_LIST = "RESERVATION_LIST"
        fun of(supportFragmentManager: FragmentManager): ReservationListFragment {
            return supportFragmentManager.findFragmentByTag(TAG_RESERVATION_LIST) as? ReservationListFragment
                ?: ReservationListFragment()
        }
    }
}
