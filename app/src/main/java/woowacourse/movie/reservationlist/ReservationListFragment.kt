package woowacourse.movie.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.RoomMovieRepository
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.reservationresult.ReservationResultActivity
import woowacourse.movie.screeningmovie.AdapterClickListener

class ReservationListFragment : Fragment(), AdapterClickListener, ReservationListContract.View {
    private lateinit var presenter: ReservationListContract.Presenter

    private var _binding: FragmentReservationListBinding? = null
    val binding: FragmentReservationListBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter =
            ReservationListPresenter(
                this,
                RoomMovieRepository.instance(),
            )
        presenter.loadReservationList()
    }

    override fun onClick(id: Long) {
        startActivity(ReservationResultActivity.getIntent(requireContext(), id))
    }

    override fun showReservationList(reservations: List<ReservationListUiModel>) {
        binding.rcvReservationList.adapter =
            ReservationListAdapter(this).apply {
                this.submitList(
                    reservations,
                )
            }
    }
}
