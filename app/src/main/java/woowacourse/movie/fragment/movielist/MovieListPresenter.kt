package woowacourse.movie.fragment.movielist

import woowacourse.movie.datasource.MockAdDataSource
import woowacourse.movie.datasource.MockMovieDataSource
import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy
import woowacourse.movie.domain.advertismentPolicy.MovieAdvertisementPolicy
import woowacourse.movie.domain.repository.AdRepository
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.view.data.MovieListViewData
import woowacourse.movie.view.data.MovieListViewType
import woowacourse.movie.view.data.MovieViewDatas
import woowacourse.movie.view.mapper.AdvertisementMapper.toView
import woowacourse.movie.view.mapper.MovieMapper.toView

class MovieListPresenter(
    private val view: MovieListContract.View
) : MovieListContract.Presenter {

    private val movieRepository: MovieRepository = MovieRepository(MockMovieDataSource())
    private val adRepository: AdRepository = AdRepository(MockAdDataSource())
    override fun makeMovieRecyclerView() {
        val loadMovieViewDatas: MovieViewDatas = loadMovieListData()
        view.makeMovieRecyclerView(loadMovieViewDatas)
    }

    override fun setOnClickListener(data: MovieListViewData) {
        when (data.viewType) {
            MovieListViewType.MOVIE -> view.setOnMovieClickListener(data)
            MovieListViewType.ADVERTISEMENT -> Unit
        }
    }

    private fun loadMovieListData(): MovieViewDatas {
        val movies = movieRepository.getData()
        val advertisements = adRepository.getData()
        val advertisementPolicy = MovieAdvertisementPolicy(MOVIE_COUNT, ADVERTISEMENT_COUNT)
        return mutableListOf<MovieListViewData>().apply {
            for (index in movies.indices) {
                if (checkAdvertisementTurn(index, advertisementPolicy)) {
                    add(advertisements[0].toView())
                }
                add(movies[index].toView())
            }
        }.let { MovieViewDatas(it) }
    }

    private fun checkAdvertisementTurn(
        index: Int,
        advertisementPolicy: AdvertisementPolicy
    ): Boolean = index > 0 && index % advertisementPolicy.movieCount == 0

    companion object {
        private const val MOVIE_COUNT = 3
        private const val ADVERTISEMENT_COUNT = 1
    }
}
