package woowacourse.movie.view.movies.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.BottomSheetFragmentTheaterBinding
import woowacourse.movie.model.theater.MovieScreeningInfoByTheater
import kotlin.properties.Delegates

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetFragmentTheaterBinding
    private var movieId by Delegates.notNull<Long>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.bottom_sheet_fragment_theater,
                container,
                false,
            )
        movieId = arguments?.getLong("movieId") ?: 0

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.theaters.adapter =
            TheaterAdapter(movieScreeningInfoByTheaters = MovieScreeningInfoByTheater.values)
    }
}
