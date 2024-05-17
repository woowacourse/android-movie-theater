package woowacourse.movie.movieList

import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate

class MovieListPresenter(
    private val view: MovieListView,
    private val theaters: List<Theater> =
        generateTheaters(
            GENERATE_DUMMY_DATA_NUM,
        ),
) {
    init {
        loadMovies()
    }

    fun onDetailButtonClicked(position: Int) {
        val theater = theaters[position]
        view.navigateToCinemaView(theater)
    }

    private fun loadMovies() {
        val displayData = theaters.toMovieDisplays()
        view.updateAdapter(displayData)
    }

    companion object {
        const val GENERATE_DUMMY_DATA_NUM = 10000

        private fun generateTheaters(count: Int): List<Theater> {
            return makeDummyData(count)
        }

        private fun makeDummyData(count: Int): List<Theater> {
            return (1..count).map { i ->
                val movie =
                    MovieInfo(
                        Title("차람과 하디의 진지한 여행기 $i"),
                        MovieDate(LocalDate.of(2024, 2, 25).plusDays(i.toLong())),
                        RunningTime(100 + i % 150),
                        Synopsis("Synopsis for movie $i"),
                    )
                Theater(movie, listOf())
            }
        }
    }
}
