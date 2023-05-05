package woowacourse.movie.ui.fragment.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.contract.movielist.MovieListContract
import woowacourse.movie.presenter.movielist.MovieListPresenter
import woowacourse.movie.ui.activity.MovieDetailActivity
import woowacourse.movie.ui.entity.Ads
import woowacourse.movie.ui.entity.Movies
import woowacourse.movie.ui.fragment.movielist.adapter.MovieAdapter
import woowacourse.movie.ui.model.AdModel
import woowacourse.movie.ui.model.MovieModel

class MovieListFragment : Fragment(), MovieListContract.View {
    private lateinit var moviesView: RecyclerView

    override lateinit var presenter: MovieListContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MovieListPresenter(this)

        moviesView = view.findViewById(R.id.rv_movie)
        presenter.setupMovieList(Movies().getAll(), Ads().getAll())
        presenter.loadMovieList()
    }

    override fun setMovieList(movies: List<MovieModel>, ads: List<AdModel>) {
        moviesView.adapter = MovieAdapter(
            movies = movies,
            ads = ads,
            onMovieItemClick = { moveToDetailActivity(it) },
            onAdItemClick = { openAdvertiseUrl(it) }
        )
    }

    private fun moveToDetailActivity(movie: MovieModel) {
        val intent = MovieDetailActivity.createIntent(requireActivity(), movie)
        startActivity(intent)
    }

    private fun openAdvertiseUrl(ad: AdModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ad.url))
        startActivity(intent)
    }
}
