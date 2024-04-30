package woowacourse.movie.main.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.data.MovieRepository.getMovieById
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.detail.view.MovieDetailActivity
import woowacourse.movie.main.view.adapter.theater.TheaterAdapter
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID

class TheaterSelectionFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTheaterSelectionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)

        val movieId = arguments?.getLong(KEY_MOVIE_ID) ?: INVALID_VALUE_MOVIE_ID

        val theaters = getMovieById((movieId))?.theaters ?: emptyList()

        binding.theaterRecyclerview.adapter = TheaterAdapter(theaters) {
            Intent(requireActivity(), MovieDetailActivity::class.java).apply {
                putExtra(KEY_MOVIE_ID, movieId)
                startActivity(this)
            }
        }

        return binding.root
    }
}