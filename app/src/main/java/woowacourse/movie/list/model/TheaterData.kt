package woowacourse.movie.list.model

import woowacourse.movie.common.MovieDataSource
import woowacourse.movie.reservation.model.DataResource.screeningTimesWeekends

object TheaterData {
    val theaters =
        listOf(
            Theater(
                "선릉",
                mapOf(
                    MovieDataSource.movieList[0] to screeningTimesWeekends,
                    MovieDataSource.movieList[1] to screeningTimesWeekends,
//                MovieDataSource.movieList[2] to screeningTimesWeekends,
                    MovieDataSource.movieList[3] to screeningTimesWeekends,
                    MovieDataSource.movieList[4] to screeningTimesWeekends,
                    MovieDataSource.movieList[5] to screeningTimesWeekends,
                    MovieDataSource.movieList[6] to screeningTimesWeekends,
                    MovieDataSource.movieList[7] to screeningTimesWeekends,
                ),
            ),
            Theater(
                "잠실",
                mapOf(
                    MovieDataSource.movieList[0] to screeningTimesWeekends,
                    MovieDataSource.movieList[1] to screeningTimesWeekends,
//                MovieDataSource.movieList[2] to screeningTimesWeekends,
                    MovieDataSource.movieList[3] to screeningTimesWeekends,
                    MovieDataSource.movieList[4] to screeningTimesWeekends,
                    MovieDataSource.movieList[5] to screeningTimesWeekends,
                    MovieDataSource.movieList[6] to screeningTimesWeekends,
                    MovieDataSource.movieList[7] to screeningTimesWeekends,
                ),
            ),
            Theater(
                "강남",
                mapOf(
                    MovieDataSource.movieList[0] to screeningTimesWeekends,
                    MovieDataSource.movieList[1] to screeningTimesWeekends,
//                MovieDataSource.movieList[2] to screeningTimesWeekends,
                    MovieDataSource.movieList[3] to screeningTimesWeekends,
                    MovieDataSource.movieList[4] to screeningTimesWeekends,
                    MovieDataSource.movieList[5] to screeningTimesWeekends,
                    MovieDataSource.movieList[6] to screeningTimesWeekends,
                    MovieDataSource.movieList[7] to screeningTimesWeekends,
                ),
            ),
        )
}
