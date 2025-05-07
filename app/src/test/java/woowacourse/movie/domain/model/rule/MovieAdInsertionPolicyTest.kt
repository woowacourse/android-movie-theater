package woowacourse.movie.domain.model.rule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.movie.MovieContent
import woowacourse.movie.presentation.common.fixture.dummyMovie

class MovieAdInsertionPolicyTest {
    private val adInsertionPolicy = MovieAdInsertionPolicy(2)
    private val movieAd = MovieContent.MovieAd(0)
    private val contents =
        listOf(
            createContent("A"),
            createContent("B"),
            createContent("C"),
            createContent("D"),
        )

    private fun createContent(title: String) = MovieContent.MovieEntry(dummyMovie.copy(title = title))

    @Test
    fun `3개마다 광고 삽입`() {
        val result = adInsertionPolicy.insert(contents) { movieAd }

        val expected =
            listOf(
                createContent("A"),
                createContent("B"),
                movieAd,
                createContent("C"),
                createContent("D"),
                movieAd,
            )

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `빈 리스트에는 광고를 삽입하지 않는다`() {
        val contents = emptyList<MovieContent>()

        val result = adInsertionPolicy.insert(contents) { movieAd }

        assertThat(result).isEmpty()
    }

    @Test
    fun `마지막 chunk가 frequency보다 작으면 광고 삽입하지 않음`() {
        val result = adInsertionPolicy.insert(contents.take(3)) { movieAd }

        val expected =
            listOf(
                createContent("A"),
                createContent("B"),
                movieAd,
                createContent("C"),
            )

        assertThat(result).isEqualTo(expected)
    }
}
