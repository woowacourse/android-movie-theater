package woowacourse.movie.view.reservationDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationDetailsBinding

class ReservationDetailsFragment : Fragment(R.layout.fragment_reservation_details) {
    private var _binding: FragmentReservationDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReservationDetailsBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
