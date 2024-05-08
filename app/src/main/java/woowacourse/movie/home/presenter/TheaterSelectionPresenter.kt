package woowacourse.movie.home.presenter

import woowacourse.movie.data.repository.HomeContentRepository
import woowacourse.movie.home.presenter.contract.TheaterSelectionContract

class TheaterSelectionPresenter(private val theaterSelectionView: TheaterSelectionContract.View) :
    TheaterSelectionContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        val movieData = HomeContentRepository.getMovieById(movieId)
        movieData?.let { movie ->
            val theaters = movie.theaters
            theaterSelectionView.setUpTheaterAdapter(theaters)
        }
    }
}
