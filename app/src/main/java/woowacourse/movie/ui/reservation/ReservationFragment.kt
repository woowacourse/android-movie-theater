package woowacourse.movie.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationBinding
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter
import woowacourse.movie.ui.reservation.presenter.ReservationContract
import woowacourse.movie.ui.reservation.presenter.ReservationContract.Presenter
import woowacourse.movie.ui.reservation.presenter.ReservationPresenter

class ReservationFragment : Fragment(), ReservationContract.View {
    override lateinit var reservationAdapter: ReservationAdapter
    override val presenter: Presenter by lazy { ReservationPresenter(this, requireContext()) }
    private var _binding: FragmentReservationBinding? = null
    private val binding: FragmentReservationBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.isEmptyMovieReservation()
        presenter.initAdapter()
        attachAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setTextOnEmptyState(isEmpty: Boolean) {
        if (!isEmpty) binding.reservationEmpty.visibility = View.GONE
    }

    private fun attachAdapter() {
        binding.reservationRecyclerview.adapter = reservationAdapter
    }
}
