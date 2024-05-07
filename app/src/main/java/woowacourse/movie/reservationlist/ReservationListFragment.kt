package woowacourse.movie.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.reservationresult.ReservationResultActivity
import woowacourse.movie.screeningmovie.AdapterClickListener

class ReservationListFragment : Fragment(), AdapterClickListener {
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

        binding.rcvReservationList.adapter =
            ReservationListAdapter(this).apply {
                this.submitList(
                    listOf(
                        ReservationListUiModel(0, "2024.3.2", "17:00", "선릉 극장", "해리 포터와 마법사의 돌"),
                        ReservationListUiModel(1, "2024.3.2", "17:00", "선릉 극장", "해리 포터와 마법사의 돌"),
                    ),
                )
            }
    }

    override fun onClick(id: Long) {
        startActivity(ReservationResultActivity.getIntent(requireContext(), 0))
    }
}
