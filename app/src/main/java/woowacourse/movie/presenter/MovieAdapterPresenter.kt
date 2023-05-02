package woowacourse.movie.presenter

import woowacourse.movie.contract.MovieAdapterContract
import woowacourse.movie.data.MovieListItemViewData
import woowacourse.movie.data.MovieListItemsViewData
import woowacourse.movie.domain.Advertisement
import woowacourse.movie.domain.AdvertisementProvider
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieProvider
import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy
import woowacourse.movie.domain.mock.AdvertisementPolicyMock
import woowacourse.movie.mapper.AdvertisementMapper.toView
import woowacourse.movie.mapper.MovieMapper.toView

class MovieAdapterPresenter(
    override val view: MovieAdapterContract.View,
    val movieProvider: MovieProvider = MovieProvider(),
    val advertisementProvider: AdvertisementProvider = AdvertisementProvider()
) : MovieAdapterContract.Presenter {
    override fun setMovieList() {
        val movies = makeMovieListViewData(
            requestMovies(), requestAdvertisements(), requestAdvertisementPolicy()
        )
        view.setAdapterData(movies)
    }

    private fun requestMovies(): List<Movie> = movieProvider.requestMovies()

    private fun requestAdvertisements(): List<Advertisement> =
        advertisementProvider.requestAdvertisements()

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
