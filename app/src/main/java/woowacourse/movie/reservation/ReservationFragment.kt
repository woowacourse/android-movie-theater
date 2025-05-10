package woowacourse.movie.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationBinding
import woowacourse.movie.domain.ReservationInfo
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
        showReservationInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showReservationInfo() {
        val reservationInfomation = listOf(ReservationInfo("제목", "2025.4.1", "13:00", 2, listOf("A1", "A2"), "선릉", 13000))
        binding.recyclerviewReservation.adapter =
            ReservationListAdapter(
                reservationInfomation,
                object : ReservationClickListener {
                    override fun clickReservation(reservationInfo: ReservationInfo) {
                        navigateToReservation(reservationInfo)
                    }
                }
            )
    }

    private fun navigateToReservation(reservationInfo: ReservationInfo) {
        val intent = MovieBookedActivity.newIntent(requireContext(), reservationInfo)
        requireContext().startActivity(intent)
    }
}
