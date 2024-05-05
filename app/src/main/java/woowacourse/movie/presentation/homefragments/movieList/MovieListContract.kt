package woowacourse.movie.presentation.homefragments.movieList

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.model.Movie
import woowacourse.movie.repository.TheaterListRepository

interface MovieListContract {
    interface View {
        fun displayMovies(movies: List<Movie>)

        fun showBottomSheetFragment(bottomSheetDialogFragment: BottomSheetDialogFragment)
    }

    interface Presenter {
        fun loadMovies()

        fun onTicketingButtonClick(
            theaterListRepository: TheaterListRepository,
            movieId: Long,
        )
    }
}
