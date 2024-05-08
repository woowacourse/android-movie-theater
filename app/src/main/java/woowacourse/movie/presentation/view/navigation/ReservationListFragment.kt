package woowacourse.movie.presentation.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.repository.ReservationMovieInfoRepositoryImpl
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.domain.model.reservation.ReservationInfo

class ReservationListFragment : Fragment() {
    private lateinit var adapter: ReservationListAdapter
    private lateinit var binding: FragmentReservationListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentReservationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reservationRecyclerView.adapter = adapter
    }
}
