package woowacourse.movie.ui.main.movies

import domain.Movies
import woowacourse.movie.data.model.mapper.MovieMapper
import woowacourse.movie.data.model.uimodel.AdvertisementUiModel
import woowacourse.movie.repository.MoviesRepository

class MoviesPresenter(
    private val view: MoviesContract.View,
    private var repository: MoviesRepository
) : MoviesContract.Presenter {
    override fun getMovies(): Movies = repository.movies

    override fun getAdvertisements(): AdvertisementUiModel = repository.advertisements

    override fun setUpClickListener() {
        setUpMovieClickListener()
        setUpAdvertisementClickListener()
    }

    private fun setUpMovieClickListener() {
        repository.movies.value.forEach { movie ->
            MovieMapper.toUi(movie).toItemModel {
                view.setOnMovieItemClick(it)
            }
        }
        view.updateMovies()
    }

    private fun setUpAdvertisementClickListener() {
        repository.advertisements.toItemModel {
            view.setOnAdvertisementItemClick(repository.advertisements)
        }
        view.updateAdvertisements()
    }
}
