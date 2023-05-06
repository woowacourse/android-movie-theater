package woowacourse.movie.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.contract.ReservationListContract
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.view.activity.ReservationResultActivity
import woowacourse.movie.view.adapter.ReservationAdapter
import woowacourse.movie.presenter.ReservationListPresenter
import woowacourse.movie.sql.ReservationDbHelper

class ReservationListFragment : Fragment(R.layout.fragment_reservation_list),
    ReservationListContract.View {
    override val presenter: ReservationListContract.Presenter by lazy {
        ReservationListPresenter(
            this,
            ReservationDbHelper(requireContext())
        )
    }

    private lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.reservation_recycler_view)
        presenter.updateReservationList()
    }

    override fun setAdapter(reservationUiModelList: List<ReservationUiModel>) {
        recyclerView.adapter =
            ReservationAdapter(reservationUiModelList, presenter::reservationItemClick)
    }

    override fun startReservationResultActivity(
        movieUiModel: MovieUiModel,
        ticketsUiModel: TicketsUiModel
    ) {
        ReservationResultActivity.start(
            requireContext(),
            movieUiModel = movieUiModel,
            ticketsUiModel = ticketsUiModel
        )
    }
}
