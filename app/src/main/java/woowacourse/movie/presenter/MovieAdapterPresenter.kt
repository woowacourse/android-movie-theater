package woowacourse.movie.presenter

import woowacourse.movie.contract.MovieAdapterContract
import woowacourse.movie.data.MovieListItemViewData
import woowacourse.movie.data.MovieListItemsViewData
import woowacourse.movie.data.repository.AdvertisementRepository
import woowacourse.movie.data.repository.MovieRepository
import woowacourse.movie.domain.Advertisement
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy
import woowacourse.movie.domain.mock.AdvertisementPolicyMock
import woowacourse.movie.mapper.AdvertisementMapper.toView
import woowacourse.movie.mapper.MovieMapper.toView

class MovieAdapterPresenter(
    override val view: MovieAdapterContract.View,
    val movieRepository: MovieRepository = MovieRepository(),
    val advertisementRepository: AdvertisementRepository = AdvertisementRepository()
) : MovieAdapterContract.Presenter {
    override fun setMovieList() {
        val movies = makeMovieListViewData(
            requestMovies(), requestAdvertisements(), requestAdvertisementPolicy()
        )
        view.setAdapterData(movies)
    }

    private fun requestMovies(): List<Movie> = movieRepository.requestMovies()

    private fun requestAdvertisements(): List<Advertisement> =
        advertisementRepository.requestAdvertisements()

    private fun requestAdvertisementPolicy(): AdvertisementPolicy =
        AdvertisementPolicyMock.createAdvertisementPolicy()

    private fun makeMovieListViewData(
        movie: List<Movie>,
        advertisement: List<Advertisement>,
        advertisementPolicy: AdvertisementPolicy
    ): MovieListItemsViewData {
        return mutableListOf<MovieListItemViewData>().apply {
            for (index in movie.indices) {
                if (index > 0 && index % advertisementPolicy.movieCount == 0) add(advertisement[0].toView())
                add(movie[index].toView())
            }
        }.let {
            MovieListItemsViewData(it)
        }
    }
}
