package woowacourse.movie.model.data

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.model.makeTheater
import woowacourse.movie.model.theater

class TheatersImplTest {
    @BeforeEach
    fun setUp() {
        TheatersImpl.deleteAll()
    }

    @Test
    fun `극장_정보를_저장한다`() {
        // given

        // when
        val id = TheatersImpl.save(theater)
        val actual = TheatersImpl.find(id)

        // then
        assertThat(actual).isEqualTo(theater.copy(id = id))
    }

    @Test
    fun `특정 id의 극장 정보를 가져온다`() {
        // given
        TheatersImpl.save(makeTheater("잠실"))
        val id = TheatersImpl.save(makeTheater("강남"))

        // when
        val actual = TheatersImpl.find(id)

        // then
        assertThat(actual.name).isEqualTo("강남")
    }

    @Test
    fun `유효하지 않은 id인 경우 예외가 발생한다`() {
        assertThatThrownBy { TheatersImpl.find(-1) }
            .isExactlyInstanceOf(NoSuchElementException::class.java)
            .hasMessage("Movie not found with id: -1")
    }

    @Test
    fun `모든 극장 정보를 가져온다`() {
        // given
        TheatersImpl.save(makeTheater("강남"))
        TheatersImpl.save(makeTheater("잠실"))

        // when
        val actual = TheatersImpl.findAll()

        // then
        assertThat(actual.size).isEqualTo(2)
        assertThat(actual[0].name).isEqualTo("강남")
        assertThat(actual[1].name).isEqualTo("잠실")
    }

    @Test
    fun `모든 극장 정보를 삭제한다`() {
        // given
        TheatersImpl.save(makeTheater("강남")) // id : 0
        TheatersImpl.save(makeTheater("잠실")) // id : 1

        // when
        TheatersImpl.deleteAll()
        val actual = TheatersImpl.findAll()

        // then
        assertThat(actual).isEmpty()
    }
}
