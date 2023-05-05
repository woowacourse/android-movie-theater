package woowacourse.movie.feature.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import woowacourse.movie.data.AdvRepositoryImpl
import woowacourse.movie.data.MovieRepositoryImpl
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.feature.adv.AdvDetailActivity
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.detail.MovieDetailActivity
import woowacourse.movie.feature.movieList.adapter.MovieListAdapter
import woowacourse.movie.feature.movieList.bottomSheet.TheaterBottomSheetFragment
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.TheaterMovieState
import woowacourse.movie.util.getParcelableCompat

class MovieListFragment : Fragment(), MovieListContract.View {

    private lateinit var adapter: MovieListAdapter

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding
        get() = _binding!!

    private lateinit var presenter: MovieListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(THEATER_SELECT_KEY) { requestKey, bundle ->
            val theaterMovie = bundle.getParcelableCompat<TheaterMovieState>(THEATER_MOVIE_KEY)
                ?: return@setFragmentResultListener
            presenter.receiveTheaterInfo(theaterMovie)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MoviesPresenter(this, MovieRepositoryImpl, AdvRepositoryImpl)
        adapter = MovieListAdapter(listOf())
        presenter.loadMovieAndAdvItemList() // 뷰가 그려질때마다 데이터 다시 불러옴. 캐싱 적용 안함
        binding.rvMovie.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun navigateMovieDetail(theaterMovie: TheaterMovieState) {
        val intent = MovieDetailActivity.getIntent(requireContext(), theaterMovie)
        startActivity(intent)
    }

    override fun showBottomSheetDialog(movie: MovieState) {
        val bottomSheetFragment = TheaterBottomSheetFragment.newInstance(movie)
        bottomSheetFragment.show(parentFragmentManager, THEATER_BOTTOM_SHEET_KEY)
    }

    override fun navigateAdbDetail(adv: AdvState) {
        val intent = AdvDetailActivity.getIntent(requireContext(), adv)
        startActivity(intent)
    }

    override fun updateItems(items: List<ItemModel>) {
        adapter.setItems(items)
    }

    companion object {
        internal const val THEATER_SELECT_KEY = "theater_select_key"
        internal const val THEATER_MOVIE_KEY = "theater_movie_key"
        private const val THEATER_BOTTOM_SHEET_KEY = "theater_bottom_sheet_key"
    }
}
