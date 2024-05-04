package woowacourse.movie.feature.home.theater

import woowacourse.movie.data.MovieRepository
import woowacourse.movie.feature.home.theater.TheaterSelectionContract

class TheaterSelectionPresenter(private val theaterSelectionView: TheaterSelectionContract.View) :
    TheaterSelectionContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        val theaters = MovieRepository.getMovieById(movieId)?.theaters ?: emptyList()
        theaterSelectionView.setUpTheaterAdapter(theaters)
    }
}
