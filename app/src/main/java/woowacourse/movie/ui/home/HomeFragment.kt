package woowacourse.movie.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.model.MovieListModel
import woowacourse.movie.ui.home.adapter.HomeAdapter
import woowacourse.movie.ui.home.adapter.ItemClickListener
import woowacourse.movie.ui.home.presenter.HomeContract
import woowacourse.movie.ui.home.presenter.HomePresenter
import woowacourse.movie.ui.moviedetail.MovieDetailActivity

class HomeFragment : Fragment(), HomeContract.View {
    private lateinit var homeAdapter: HomeAdapter
    override lateinit var movieWithAdvertisement: List<MovieListModel>
    override val presenter: HomeContract.Presenter by lazy { HomePresenter(this) }
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getMovieWithAdvertisement()
        initAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        homeAdapter = HomeAdapter(movieWithAdvertisement, setEventOnClickListener())
        binding.mainMovieList.adapter = homeAdapter
    }

    private fun setEventOnClickListener(): ItemClickListener = object : ItemClickListener {
        override fun onMovieItemClick(movie: MovieListModel.MovieModel) {
            moveToDetailActivity(movie)
        }

        override fun onAdItemClick(ad: MovieListModel.AdModel) {
            moveToWebPage(ad)
        }
    }

    private fun moveToDetailActivity(movie: MovieListModel.MovieModel) {
        val intent =
            Intent(context, MovieDetailActivity::class.java).apply { putExtra(KEY_MOVIE, movie) }
        startActivity(intent)
    }

    private fun moveToWebPage(ad: MovieListModel.AdModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ad.url))
        startActivity(intent)
    }

    companion object {
        const val KEY_MOVIE = "movie"
    }
}
