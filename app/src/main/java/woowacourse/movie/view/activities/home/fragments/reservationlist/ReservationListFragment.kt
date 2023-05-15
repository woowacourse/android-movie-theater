package woowacourse.movie.view.activities.home.fragments.reservationlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import woowacourse.movie.data.reservation.ReservationDbHelper
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.view.activities.reservationresult.ReservationResultActivity

class ReservationListFragment : Fragment(), ReservationListContract.View {

    private val binding: FragmentReservationListBinding by lazy {
        FragmentReservationListBinding.inflate(layoutInflater)
    }

    private val presenter: ReservationListContract.Presenter by lazy {
        ReservationListPresenter(
            this,
            ReservationRepositoryImpl(ReservationDbHelper.getDbInstance(requireContext()))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadReservations()
    }

    override fun setReservationList(reservationListViewItemUIStates: List<ReservationListViewItemUIState>) {
        val reservationListView = binding.rvReservationList
        reservationListView.adapter =
            ReservationListAdapter(reservationListViewItemUIStates) { reservationId ->
                ReservationResultActivity.startActivity(reservationListView.context, reservationId)
            }
    }
}
