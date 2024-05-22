package woowacourse.movie.data.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.data.model.MovieData
import woowacourse.movie.data.model.MoviePosterData
import woowacourse.movie.domain.model.Image

class MovieDataSourceTest {
    private val movieRepository2 = FakeMovieDataSource()

    @Test
    fun `영화 id 로 영화 포스터를 찾는다`() {
        val actual = movieRepository2.imageSrc2(1)
        assertThat(actual).isEqualTo(MoviePosterData(1, Image.StringImage("1")))
    }

    @Test
    fun `영화 id 로 영화를 찾는다`() {
        val actual = movieRepository2.findById(1)
        val expected =
            MovieData(
                1,
                "title1",
                1,
                "description1",
            )
        assertThat(actual).isEqualTo(expected)
    }
}
