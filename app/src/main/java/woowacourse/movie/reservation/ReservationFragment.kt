package woowacourse.movie.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.Reservation
import woowacourse.movie.databinding.FragmentReservationBinding
import woowacourse.movie.moviebooked.MovieBookedActivity

class ReservationFragment : Fragment() {
    private var _binding: FragmentReservationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showReservation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showReservation() {
        val reservation = listOf(Reservation(100,"제목", "2025.4.1", "13:00", 2, "A1, A2", "선릉", 13000))
        binding.recyclerviewReservation.adapter =
            ReservationListAdapter(
                reservation,
                object : ReservationClickListener {
                    override fun clickReservation(reservation: Reservation) {
                        navigateToReservation(reservation.uid)
                    }
                }
            )
    }

    private fun navigateToReservation(id: Long) {
        val intent = MovieBookedActivity.newIntent(requireContext(), id)
        requireContext().startActivity(intent)
    }
}
