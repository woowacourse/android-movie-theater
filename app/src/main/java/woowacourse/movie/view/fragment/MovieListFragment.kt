package woowacourse.movie.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.MovieListItemViewData
import woowacourse.movie.data.MovieListViewType
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.MoviesViewData
import woowacourse.movie.domain.AdvertisementMock
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.advertismentPolicy.MovieAdvertisementPolicy
import woowacourse.movie.view.activity.MovieReservationActivity
import woowacourse.movie.view.adapter.MovieAdapter
import woowacourse.movie.view.repository.MovieListRepository

class MovieListFragment : Fragment() {
    private val movieListRepository: MovieListRepository = MovieListRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        val movies = movieListRepository.requestMovie()
        makeMovieRecyclerView(view, movies)
        return view
    }

    private fun makeMovieRecyclerView(view: View, movies: List<Movie>) {
        val advertisementsData = AdvertisementMock.createAdvertisements()
        val advertisementPolicy = MovieAdvertisementPolicy(MOVIE_COUNT, ADVERTISEMENT_COUNT)

        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.movie_list_recycler)
        movieRecyclerView.adapter = MovieAdapter(
            movies, advertisementsData, advertisementPolicy, ::onClickItem
        )
    }

    private fun onClickItem(data: MovieListItemViewData) {
        when (data.viewType) {
            MovieListViewType.MOVIE -> MovieReservationActivity.from(
                requireContext(), data as MovieViewData
            ).run {
                startActivity(this)
            }
            MovieListViewType.ADVERTISEMENT -> Unit
        }
    }

    companion object {
        private const val MOVIE_COUNT = 3
        private const val ADVERTISEMENT_COUNT = 1

        fun from(moviesViewData: MoviesViewData) = Bundle().apply {
            putSerializable(MoviesViewData.MOVIES_EXTRA_NAME, moviesViewData)
        }
    }
}
