package woowacourse.movie.main.presenter

import woowacourse.movie.data.MovieRepository
import woowacourse.movie.main.presenter.contract.TheaterSelectionContract

class TheaterSelectionPresenter(private val theaterSelectionView: TheaterSelectionContract.View) :
    TheaterSelectionContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        val theaters = MovieRepository.getMovieById(movieId)?.theaters ?: emptyList()
        theaterSelectionView.setUpTheaterAdapter(theaters)
    }
}
