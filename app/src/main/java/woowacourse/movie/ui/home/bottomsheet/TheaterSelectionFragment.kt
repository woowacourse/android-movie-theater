package woowacourse.movie.ui.home.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.ui.moviedetail.MovieDetailActivity
import woowacourse.movie.uimodel.MovieListModel
import woowacourse.movie.utils.MockData
import woowacourse.movie.utils.getParcelableCompat

class TheaterSelectionFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTheaterSelectionBinding
    private lateinit var movie: MovieListModel.MovieModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)
        movie = arguments?.getParcelableCompat<MovieListModel.MovieModel>("key")
            ?: throw IllegalArgumentException()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.theaterRecyclerview.adapter = TheaterListAdapter(
            MockData.getTheaterList(),
        ) {
            moveToDetailActivity()
        }
    }

    private fun moveToDetailActivity() {
        startActivity(MovieDetailActivity.getIntent(movie, requireContext()))
    }
}
