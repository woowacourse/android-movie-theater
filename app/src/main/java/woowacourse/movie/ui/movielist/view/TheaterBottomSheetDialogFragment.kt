package woowacourse.movie.ui.movielist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.sample.DUMMY_THEATERS
import woowacourse.movie.ui.booking.view.BookingActivity

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_theater_bottom_sheet_dialog,
                container,
                false,
            )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            TheaterAdapter { theater ->
                startActivity(BookingActivity.newIntent(view.context, theater))
            }
        binding.theatersRecyclerView.adapter = adapter
        val theaters = Theaters(DUMMY_THEATERS.theaters)
        adapter.submitList(theaters.theaters.toList())
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TheaterBottomSheetDialogFragment().apply {
                arguments = Bundle()
            }
    }
}
