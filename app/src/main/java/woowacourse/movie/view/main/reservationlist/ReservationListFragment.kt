package woowacourse.movie.view.main.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.util.ExceptionMessages
import woowacourse.movie.view.main.reservationlist.adapter.ReservationListAdapter

class ReservationListFragment :
    Fragment(),
    ReservationListContract.View {
    @Suppress("ktlint:standard:backing-property-naming")
    private var _binding: FragmentReservationListBinding? = null
    private val binding: FragmentReservationListBinding
        get() =
            _binding
                ?: throw IllegalStateException(ExceptionMessages.FRAGMENT_BINDING_STATE_EXCEPTION)
    private val reservationListAdapter: ReservationListAdapter by lazy {
        ReservationListAdapter { reservationInfo ->
            Toast
                .makeText(
                    context,
                    "${reservationInfo.title} 클릭",
                    Toast.LENGTH_SHORT,
                ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentReservationListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.reservationListListLayout.adapter = reservationListAdapter
        showReservationInfos()
    }

    override fun showReservationInfos() {
        reservationListAdapter.submitList(ReservationListAdapter.dummyReservationInfos)
    }
}
