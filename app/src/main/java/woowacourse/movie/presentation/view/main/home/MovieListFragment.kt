package woowacourse.movie.presentation.view.main.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.view.main.home.moviedetail.MovieDetailActivity

class MovieListFragment : Fragment(R.layout.fragment_movie_list), MovieListContract.View {
    private val presenter by lazy { MovieListPresenter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getMovieData()
    }

    override fun setRecyclerView(movies: List<Movie>) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_movie_list)
        val adapter = MovieListAdapter(
            movies,
            ContextCompat.getDrawable(requireContext(), R.drawable.advertise_wooteco)!!
        ) {
            val intent = MovieDetailActivity.getIntent(requireContext())
            intent.putExtra(MovieDetailActivity.MOVIE_DATA_INTENT_KEY, it)
            requireContext().startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}
