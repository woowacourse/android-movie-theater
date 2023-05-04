package woowacourse.movie.view.moviemain.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.reservation.ReservationActivity

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val presenter = MovieListPresenter()
        val movies = presenter.getMovieListData()

        val movieAdapter = MovieListAdapter(3, movies, ::onClick)
        val movieListView = view.findViewById<RecyclerView>(R.id.movie_recyclerview)
        movieListView.adapter = movieAdapter
    }

    private fun onClick(item: MovieUiModel) {
        val intent = ReservationActivity.newIntent(requireContext(), item)
        startActivity(intent)
    }

    companion object {
        const val TAG_MOVIE_LIST = "MOVIE_LIST"
    }
}
