package woowacourse.movie.fragment.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.activity.reservationresult.ReservationResultActivity
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.datasource.ReservationDataSource
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.fragment.reservationlist.adapter.ReservationAdapter
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.ReservationsViewData

class ReservationListFragment : Fragment(), ReservationListContract.View {
    override lateinit var presenter: ReservationListContract.Presenter
    private val binding: FragmentReservationListBinding by lazy {
        FragmentReservationListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ReservationListPresenter(
            this,
            ReservationRepository(ReservationDataSource(this.requireContext())),
        )
        presenter.loadReservations()
    }

    override fun setReservations(reservationsViewData: ReservationsViewData) {
        binding.reservationListRecycler.adapter =
            ReservationAdapter(reservationsViewData) { presenter.onItemClick(it) }
        val decoration =
            DividerItemDecoration(requireView().context, DividerItemDecoration.VERTICAL)
        binding.reservationListRecycler.addItemDecoration(decoration)
    }

    override fun onItemClick(reservationViewData: ReservationViewData) {
        startActivity(
            ReservationResultActivity.from(
                requireContext(),
                reservationViewData,
            ),
        )
    }
}
