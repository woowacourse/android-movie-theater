package activity

import com.woowacourse.domain.movie.Movie
import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieBookingSeatInfo
import com.woowacourse.domain.seat.Seat
import com.woowacourse.domain.seat.SeatColumn
import com.woowacourse.domain.seat.SeatGroup
import com.woowacourse.domain.seat.SeatRow
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.seatPicker.SeatPickerContract
import woowacourse.movie.activity.seatPicker.SeatPickerPresenter
import java.time.LocalDate

class SeatPickerPresenterTest {
    private lateinit var presenter: SeatPickerPresenter
    private lateinit var view: SeatPickerContract.View

    @Before
    fun setUp() {
        val movieData =
            Movie(0, "title", 200, "synopsis", LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 5))
        val movieBookingInfo = MovieBookingInfo(movieData, "2023.5.2", "09:00", 2)
        view = mockk()
        presenter = SeatPickerPresenter(view, movieBookingInfo)
    }

    @Test
    fun 영화제목을_전달한다() {
        // given
        val slot = slot<String>()
        every { view.initMovieTitle(capture(slot)) } answers { nothing }

        // when
        presenter.initMovieTitle()

        // then
        val actual = slot.captured
        assertEquals("title", actual)
    }

    @Test
    fun MovieBookingSeatInfo를_전달한다() {
        // given
        val slot = slot<MovieBookingSeatInfo>()
        every { view.onClickDoneBtn(capture(slot)) } answers { nothing }
        val expect = MovieBookingSeatInfo(presenter.movieBookingInfo, listOf(), "10000")

        // when
        presenter.getMovieBookingSeatInfo("10000")

        // then
        val actual = slot.captured
        assertEquals(expect, actual)
    }

    @Test
    fun seatGroup을_초기화하고_자리이름들을_전달한다() {
        // given
        val slot = slot<List<String>>()
        every { view.setSeatGroup(capture(slot)) } answers { nothing }

        // when
        presenter.setSeatGroup(SeatGroup(listOf(Seat(SeatRow(0), SeatColumn(0)))))

        // then
        val actual = slot.captured
        assertEquals(listOf("A1"), actual)
    }

    @Test
    fun 선택한_좌석이_이미_선택되어있다면_해당좌석을_삭제한다() {
        // given

        // when

        // then
    }
}
