package woowacourse.movie.data.mock

import domain.DateRange
import domain.Movie
import domain.Movies
import woowacourse.movie.R
import java.time.LocalDate

object MockMoviesFactory {
    val movies = makeMovies()

    fun makeMovies(): Movies {
        return Movies(
            List(5) { (generateMovie(it)) }
        )
    }

    private fun generateMovie(number: Int): Movie {
        return Movie(
            R.drawable.poster_harrypotter.toString(),
            "해리 포터$number",
            DateRange(
                LocalDate.of(2023, 4, 23),
                LocalDate.of(2023, 4, 27),
            ),
            153,
            "《해리 포터》(Harry Potter)는 1997년부터 2016년까지 연재된 영국의 작가 J. K. 롤링의 판타지 소설 시리즈이다. 유일한 친척인 이모네 집 계단 밑 벽장에서 생활하던 11살 소년 해리 포터가 호그와트 마법학교에 다니면서 겪게 되는 판타지 이야기를 그리고 있다.",
        )
    }
}
