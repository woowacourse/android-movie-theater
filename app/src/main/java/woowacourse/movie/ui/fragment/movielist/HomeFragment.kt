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
import woowacourse.movie.data.entity.Ads
import woowacourse.movie.data.entity.Movies
import woowacourse.movie.ui.activity.detail.view.MovieDetailActivity
import woowacourse.movie.ui.fragment.movielist.adapter.MovieAdapter
import woowacourse.movie.ui.model.AdModel
import woowacourse.movie.ui.model.MovieModel

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviesView = view.findViewById<RecyclerView>(R.id.rv_movie)
        setMovieList(moviesView)
    }

    private fun setMovieList(moviesView: RecyclerView) {
        moviesView.adapter = MovieAdapter(
            movies = Movies().getAll(),
            ads = Ads().getAll(),
            onMovieItemClick = { moveToDetailActivity(it) },
            onAdItemClick = { openAdvertiseUrl(it) }
        )
    }

    private fun moveToDetailActivity(movie: MovieModel) {
        val intent = MovieDetailActivity.createIntent(requireActivity(), movie)
        startActivity(intent)
    }

    private fun openAdvertiseUrl(it: AdModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
        startActivity(intent)
    }
}
