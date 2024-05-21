package woowacourse.movie.data.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.source.MovieDataSource
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.Movie

class DefaultMovieRepositoryTest {
    private lateinit var dataSource: MovieDataSource
    private lateinit var repository: DefaultMovieRepository

    @BeforeEach
    fun setUp() {
        dataSource = FakeMovieDataSource()
        repository = DefaultMovieRepository(dataSource)
    }

    @Test
    fun loadMovie() {
        // given
        val movieId = 1

        // when
        val movie = repository.loadMovie(movieId)

        // then
        assertThat(movie).isEqualTo(
            Movie(
                1,
                "title1",
                1,
                "description1",
                Image.StringImage("1"),
            ),
        )
    }
}
