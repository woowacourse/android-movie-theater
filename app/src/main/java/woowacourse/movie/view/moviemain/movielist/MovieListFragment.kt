package woowacourse.movie.view.moviemain.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.movie.MovieMockRepository
import woowacourse.movie.data.movie.MoviePosterMockRepository
import woowacourse.movie.view.model.MovieUiModel

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val presenter = MovieListPresenter(MovieMockRepository, MoviePosterMockRepository)
        val movies = presenter.getMovieListData()

        val movieAdapter = MovieListAdapter(3, movies, ::onClick)
        val movieListView = view.findViewById<RecyclerView>(R.id.movie_recyclerview)
        movieListView.adapter = movieAdapter
    }

    private fun onClick(item: MovieUiModel) {
        val bottomSheet = TheaterBottomSheetFragment(item)
        childFragmentManager.fragmentFactory = BottomSheetFragmentFactoryImpl(item)
        bottomSheet.show(childFragmentManager, TheaterBottomSheetFragment.TAG_THEATER)
    }

    companion object {
        const val TAG_MOVIE_LIST = "MOVIE_LIST"
    }
}
