package woowacourse.movie.utils

import woowacourse.movie.R
import woowacourse.movie.uimodel.MovieListModel
import woowacourse.movie.uimodel.TheaterModel
import java.time.LocalDate
import java.time.LocalTime

object MockData {
    private val movies = List(300) {
        MovieListModel.MovieModel(
            R.drawable.about_time,
            "About Time $it",
            LocalDate.of(2023, 4, 26),
            LocalDate.of(2023, 4, 30),
            123,
            "아버지에게 가문 대대로 시간을 돌리는 능력을 타고났다는 사실을 들은 팀. 우연히 만난 메리에게 반한 팀은 완벽한 사랑을 위해 능력을 마음껏 사용하고, 그럴 때마다 주변 상황들이 점점 어긋나기 시작한다.",
        )
    }

    private val ad = MovieListModel.AdModel(
        R.drawable.ad,
        "https://github.com/woowacourse",
    )

    fun getMoviesWithAds(): List<MovieListModel> {
        val items = mutableListOf<MovieListModel>()

        movies.forEachIndexed { index, movieModel ->
            items.add(movieModel)

            if (isAdPosition(index)) {
                items.add(ad)
            }
        }

        return items
    }

    fun getTheaterList(): List<TheaterModel> = listOf(
        TheaterModel(
            "잠실",
            3,
            mapOf<LocalDate, List<LocalTime>>(
                LocalDate.of(2023, 5, 8) to
                    listOf(LocalTime.of(11, 0), LocalTime.of(17, 0)),
                LocalDate.of(2023, 5, 9) to
                    listOf(LocalTime.of(14, 0)),
            ),
        ),
        TheaterModel(
            "선릉",
            2,
            mapOf<LocalDate, List<LocalTime>>(
                LocalDate.of(2023, 5, 8) to
                    listOf(LocalTime.of(19, 0)),
                LocalDate.of(2023, 5, 9) to
                    listOf(LocalTime.of(10, 0)),
            ),
        ),
    )

    private fun isAdPosition(index: Int) = index % 3 == 2
}
