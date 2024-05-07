package woowacourse.movie.reservationhistory.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationHistoryBinding

class ReservationHistoryFragment : Fragment() {
    private lateinit var binding: FragmentReservationHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentReservationHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}
