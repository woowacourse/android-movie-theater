package woowacourse.movie.model.movie

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.movie.model.movieContent

class MoviesTest {
    @Test
    fun `중복된 영화 객체를 가질 경우 예외를 발생시킨다`() {
        assertThrows<IllegalArgumentException>("중복된 영화를 보유할 수 없습니다.") {
            Movies(listOf(movieContent, movieContent))
        }
    }
}
