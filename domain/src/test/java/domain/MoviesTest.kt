package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month
import java.util.*

class MoviesTest {
    @Test
    internal fun `영화들 중에 제목이 일치하는 영화를 찾을 수 있다`() {
        // given
        val movies = makeMovies()
        // when
        val actual = movies.findMovieByTitle("해리 포터0")?.title
        // then
        val expected = generateMovie(0).title
        assertThat(actual).isEqualTo(expected)
    }

    private fun makeMovies(): Movies {
        return Movies(
            List(1000) { (generateMovie(it)) }
        )
    }

    private fun generateMovie(number: Int): Movie {
        return Movie(
            "",
            "해리 포터${number}",
            generateTheaters(),
            153,
            "《해리 포터》(Harry Potter)는 1997년부터 2016년까지 연재된 영국의 작가 J. K. 롤링의 판타지 소설 시리즈이다. 유일한 친척인 이모네 집 계단 밑 벽장에서 생활하던 11살 소년 해리 포터가 호그와트 마법학교에 다니면서 겪게 되는 판타지 이야기를 그리고 있다.",
        )
    }

    private fun generateTheaters(): Theaters {
        return Theaters(
            listOf(
                generateTheater("선릉", 5),
                generateTheater("잠실", 4),
                generateTheater("강남", 5)
            )
        )
    }

    private fun generateTheater(theaterName: String, timeCount: Int): Theater {
        val timeList = List(timeCount) { randomLocalDateTime() }
        return Theater(theaterName, timeList)
    }

    private fun randomLocalDateTime(): LocalDateTime {
        val random = Random()
        val year = 2023 // 년도 설정
        val month = Month.values()[4] // 월 무작위 생성
        val dayOfMonth = random.nextInt(month.maxLength()) + 1 // 해당 월의 일 무작위 생성
        val hour = random.nextInt(24) // 시간 무작위 생성
        val minute = 0
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute)
    }
}
