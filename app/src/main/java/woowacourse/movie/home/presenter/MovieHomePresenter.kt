package woowacourse.movie.home.presenter

import android.util.Log
import woowacourse.movie.data.MovieRepository.getAllAdvertisements
import woowacourse.movie.data.MovieRepository.getAllMovies
import woowacourse.movie.home.presenter.contract.MovieHomeContract
import woowacourse.movie.home.view.adapter.movie.HomeContent

class MovieHomePresenter(private val movieHomeContractView: MovieHomeContract.View) :
    MovieHomeContract.Presenter {
    override fun loadHomeContents() {
        val movies = getAllMovies()
        val advertisements = getAllAdvertisements()

        val homeContents = mutableListOf<HomeContent>()
        var advertisementIndex = 0

        movies.forEachIndexed { index, movie ->
            if (index > 0 && index % 3 == 0) {
                if (advertisementIndex >= advertisements.size) advertisementIndex = 0
                homeContents.add(advertisements[advertisementIndex++])
            }
            homeContents.add(movie)
        }

        Log.e("a", homeContents.toString())

        movieHomeContractView.displayHomeContents(homeContents)
    }
}
