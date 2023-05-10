package woowacourse.movie.view.main.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.database.ReservationDbHelper
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.view.main.reservationlist.adapter.ReservationAdapter
import woowacourse.movie.view.reservationresult.ReservationResultActivity

class ReservationListFragment :
    Fragment(R.layout.fragment_reservation_list),
    ReservationListContract.View {
    private lateinit var binding: FragmentReservationListBinding
    private val presenter: ReservationListContract.Presenter by lazy {
        ReservationListPresenter(
            this,
            ReservationDbHelper(requireContext()),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reservation_list,
            container,
            false,
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.updateReservationList()
    }

    override fun setReservationList(reservationUiModelList: List<ReservationUiModel>) {
        binding.reservationRecyclerView.adapter =
            ReservationAdapter(reservationUiModelList, presenter::showReservationResult)
    }

    override fun showReservationResult(
        movieUiModel: MovieUiModel,
        ticketsUiModel: TicketsUiModel,
    ) {
        ReservationResultActivity.start(
            requireContext(),
            movieUiModel = movieUiModel,
            ticketsUiModel = ticketsUiModel,
        )
    }
}
