package woowacourse.movie.ui.home.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.data.theater.TheaterRepositoryImpl
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.ui.home.bottomsheet.adapter.TheaterListAdapter
import woowacourse.movie.ui.moviedetail.MovieDetailActivity
import woowacourse.movie.uimodel.MovieListModel
import woowacourse.movie.uimodel.TheaterModel
import woowacourse.movie.utils.MockData
import woowacourse.movie.utils.getParcelableCompat

class TheaterSelectionFragment : BottomSheetDialogFragment(), TheaterSelectionContract.View {

    override val presenter by lazy {
        TheaterSelectionPresenter(this, TheaterRepositoryImpl())
    }

    private lateinit var binding: FragmentTheaterSelectionBinding
    private lateinit var movie: MovieListModel.MovieModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)
        movie = arguments?.getParcelableCompat(MovieDetailActivity.KEY_MOVIE)
            ?: throw IllegalArgumentException()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val theaters = MockData.getTheaterList()
        binding.theaterRecyclerview.adapter = TheaterListAdapter(theaters) {
            moveToDetailActivity(theaters[it])
        }
    }

    override fun setTheaterList(theaters: List<TheaterModel>) {
        binding.theaterRecyclerview.adapter = TheaterListAdapter(theaters) {
            moveToDetailActivity(theaters[it])
        }
    }

    private fun moveToDetailActivity(theaterModel: TheaterModel) {
        startActivity(MovieDetailActivity.getIntent(movie, theaterModel, requireContext()))
    }
}
