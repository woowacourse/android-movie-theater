package woowacourse.movie.model.data.remote

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalTime

class DummyMovieTheaterStorageTest {
    @Test
    fun `movie id 1번을 검색하면 선릉 강남을 반환한다`() {
        // given
        val movieTheaterStorage = DummyMovieTheaterStorage()

        // when
        val expected = listOf<String>("seolleung", "gangnam")

        // then
        assertEquals(expected, movieTheaterStorage.getTheatersByMovieId(1))
    }

    @Test
    fun `movie id 2번을 검색하면 선릉 강남 잠실을 반환한다`() {
        // given
        val movieTheaterStorage = DummyMovieTheaterStorage()

        // when
        val expected = listOf<String>("seolleung", "gangnam", "gamsil")

        // then
        assertEquals(expected, movieTheaterStorage.getTheatersByMovieId(2))
    }

    @Test
    fun `movie id 1번과 선릉역을 입력한다면 그에맞는 시간표를 반환한다`() {
        // given
        val movieTheaterStorage = DummyMovieTheaterStorage()

        // when
        val expected = listOf(
            LocalTime.of(9, 0),
            LocalTime.of(11, 0),
            LocalTime.of(13, 0),
            LocalTime.of(15, 0),
            LocalTime.of(17, 0),
            LocalTime.of(19, 0),
            LocalTime.of(21, 0)
        )

        // then
        assertEquals(expected, movieTheaterStorage.getTheaterTimeTableByMovieId(1, "seolleung"))
    }
}
