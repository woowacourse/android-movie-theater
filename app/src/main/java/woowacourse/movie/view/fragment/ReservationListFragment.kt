package woowacourse.movie.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.contract.ReservationListContract
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.view.activity.ReservationResultActivity
import woowacourse.movie.view.adapter.ReservationAdapter
import woowacourse.movie.presenter.ReservationListPresenter
import woowacourse.movie.database.ReservationDbHelper

class ReservationListFragment : Fragment(R.layout.fragment_reservation_list),
    ReservationListContract.View {
    private lateinit var binding: FragmentReservationListBinding
    override val presenter: ReservationListContract.Presenter by lazy {
        ReservationListPresenter(
            this,
            ReservationDbHelper(requireContext())
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reservation_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.updateReservationList()
    }

    override fun setAdapter(reservationUiModelList: List<ReservationUiModel>) {
        binding.reservationRecyclerView.adapter =
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
