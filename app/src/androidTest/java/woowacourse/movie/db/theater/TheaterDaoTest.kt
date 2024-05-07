package woowacourse.movie.db.theater

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.TestFixture.FIRST_THEATER_ITEM_POSITION
import woowacourse.movie.model.theater.Theater

@RunWith(AndroidJUnit4::class)
class TheaterDaoTest {
    private val dao = TheaterDao()
    private val theaters = TheaterDatabase.theaters

    @Test
    fun `극장_데이터베이스의_첫번째_극장_데이터를_가져온다`() {
        val actual: Theater = dao.find(FIRST_THEATER_ITEM_POSITION)
        val expected: Theater = theaters[FIRST_THEATER_ITEM_POSITION]
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `극장_데이터베이스의_모든_데이터를_가져온다`() {
        val actual = dao.findAll().size
        val expected = theaters.size
        actual shouldBe expected
    }
}
