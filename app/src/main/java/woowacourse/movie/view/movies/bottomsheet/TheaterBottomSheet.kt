package woowacourse.movie.view.movies.bottomsheet

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetBinding
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.movies.adapter.TheaterAdapter

class TheaterBottomSheet(
    private val theaters: Theaters,
    private val movieId: Int,
    private val onclick: (Theater) -> Unit,
) : BottomSheetDialogFragment(R.layout.fragment_theater_bottom_sheet) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTheaterBottomSheetBinding.bind(view)

        val theaterAdapter = TheaterAdapter(theaters, movieId, onclick)
        binding.rv.adapter = theaterAdapter
    }
}
