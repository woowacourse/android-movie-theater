package woowacourse.movie.feature.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.AdvRepositoryImpl
import woowacourse.movie.data.MovieRepositoryImpl
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.feature.adv.AdvDetailActivity
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.detail.MovieDetailActivity
import woowacourse.movie.feature.movieList.adapter.MovieListAdapter
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

class MovieListFragment : Fragment(), MovieListContract.View {

    private lateinit var adapter: MovieListAdapter

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding
        get() = _binding!!

    private lateinit var presenter: MovieListContract.Presenter

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

    override fun navigateMovieDetail(movie: MovieState) {
        val intent = MovieDetailActivity.getIntent(requireContext(), movie)
        startActivity(intent)
    }

    override fun navigateAdbDetail(adv: AdvState) {
        val intent = AdvDetailActivity.getIntent(requireContext(), adv)
        startActivity(intent)
    }

    override fun updateItems(items: List<ItemModel>) {
        adapter.setItems(items)
    }
}
