package woowacourse.movie.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.movie.MovieRepositoryImpl
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.ui.home.adapter.ItemClickListener
import woowacourse.movie.ui.home.adapter.MovieListAdapter
import woowacourse.movie.ui.moviedetail.MovieDetailActivity
import woowacourse.movie.uimodel.MovieListModel

class HomeFragment : Fragment(), HomeContract.View {

    override val presenter by lazy {
        HomePresenter(this, MovieRepositoryImpl())
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setMovieList()
    }

    override fun setMovieList(movies: List<MovieListModel>) {
        binding.mainMovieList.adapter = MovieListAdapter(
            movies,
            object : ItemClickListener {
                override fun onMovieItemClick(movie: MovieListModel.MovieModel) {
                    moveToDetailActivity(movie)
                }

                override fun onAdItemClick(ad: MovieListModel.AdModel) {
                    moveToWebPage(ad)
                }
            },
        )
    }

    private fun moveToDetailActivity(movie: MovieListModel.MovieModel) {
        startActivity(MovieDetailActivity.getIntent(movie, requireContext()))
    }

    private fun moveToWebPage(ad: MovieListModel.AdModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ad.url))
        startActivity(intent)
    }
}
