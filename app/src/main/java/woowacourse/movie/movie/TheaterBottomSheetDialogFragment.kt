package woowacourse.movie.movie

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.Theaters
import woowacourse.movie.moviebooking.MovieBookingActivity

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.fragment_theater_bottom_sheet_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val movie: Movie =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(KEY_MOVIE, Movie::class.java)
                    ?: throw IllegalArgumentException("$KEY_MOVIE 객체가 전달되지 않았습니다.")
            } else {
                arguments?.getParcelable(KEY_MOVIE)
                    ?: throw IllegalArgumentException("$KEY_MOVIE 객체가 전달되지 않았습니다.")
            }

        val adapter = TheaterListAdapter(Theaters.theaters, movie) { theater -> navigateToTheater(theater, movie) }
        binding.therters.apply {
            this.adapter = adapter
        }
    }

    private fun navigateToTheater(
        theater: Theater,
        movie: Movie,
    ) {
        val intent = MovieBookingActivity.movieBookingIntent(requireContext(), movie, theater)
        startActivity(intent)
    }

    companion object {
        private const val KEY_MOVIE = "movie"

        fun newInstance(movie: Movie): TheaterBottomSheetDialogFragment {
            val dialogFragment = TheaterBottomSheetDialogFragment()
            val bundle =
                Bundle().apply {
                    putParcelable(KEY_MOVIE, movie)
                }
            dialogFragment.arguments = bundle
            return dialogFragment
        }
    }
}
