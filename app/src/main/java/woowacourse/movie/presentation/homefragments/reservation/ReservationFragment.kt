package woowacourse.movie.presentation.homefragments.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationBinding
import woowacourse.movie.model.Reservation

class ReservationFragment : Fragment(), ReservationContract.View {
    private var _binding: FragmentReservationBinding? = null
    val binding get() = _binding!!
    private val presenter by lazy { ReservationPresenter(this) }
    private val reservationAdapter: ReservationAdapter by lazy { ReservationAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReservations.adapter = reservationAdapter
        presenter.loadReservations()
    }

    override fun displayReservations(reservations: List<Reservation>) {
        reservationAdapter.updateReservations(reservations)
    }
}
