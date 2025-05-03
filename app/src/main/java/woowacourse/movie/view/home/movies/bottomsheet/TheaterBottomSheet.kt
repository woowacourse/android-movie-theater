package woowacourse.movie.view.home.movies.bottomsheet

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetBinding
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter
import woowacourse.movie.view.home.movies.model.TheaterRvItem

class TheaterBottomSheet(
    private val theaters: List<TheaterRvItem>,
    private val handler: TheaterAdapter.Handler,
) : BottomSheetDialogFragment(R.layout.fragment_theater_bottom_sheet) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTheaterBottomSheetBinding.bind(view)

        val theaterAdapter = TheaterAdapter(theaters, handler)
        binding.rv.adapter = theaterAdapter
    }
}
