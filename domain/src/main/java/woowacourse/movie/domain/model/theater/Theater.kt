package woowacourse.movie.domain.model.theater

import woowacourse.movie.domain.model.movie.MovieDate
import woowacourse.movie.domain.model.movie.MovieTime
import java.time.LocalDate

typealias DomainTheater = Theater

data class Theater(
    val name: String,
    val date: MovieDate = MovieDate(LocalDate.now()),
    val runningTimes: List<MovieTime>,
) {
    val movieTimesCount: Int = runningTimes.size
}
