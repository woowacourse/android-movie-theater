package woowacourse.movie.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.ui.Ads
import woowacourse.movie.ui.Movies
import woowacourse.movie.ui.activity.MovieDetailActivity
import woowacourse.movie.ui.adapter.MovieAdapter
import woowacourse.movie.ui.model.AdModel
import woowacourse.movie.ui.model.MovieModel

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val moviesView = view.findViewById<RecyclerView>(R.id.main_movie_list)
        setMovieList(moviesView)

        return view
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