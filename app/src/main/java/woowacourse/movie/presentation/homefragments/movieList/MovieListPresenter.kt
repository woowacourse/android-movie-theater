package woowacourse.movie.presentation.homefragments.movieList

import woowacourse.movie.presentation.homefragments.movieList.fragment.TheaterBottomDialogFragment
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.repository.TheaterListRepository

class MovieListPresenter(private val view: MovieListContract.View) :
    MovieListContract.Presenter {
    private val movieRepository = MovieRepository()

    override fun loadMovies() {
        val movies = movieRepository.getAllMovies()
        view.displayMovies(movies)
    }

    override fun onTicketingButtonClick(
        theaterListRepository: TheaterListRepository,
        movieId: Long,
    ) {
        val bottomSheetFragment = TheaterBottomDialogFragment(theaterListRepository, movieId)
        view.showBottomSheetFragment(bottomSheetFragment)
    }
}
