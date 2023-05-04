package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.MovieReservationContract
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.domain.Count
import woowacourse.movie.domain.ReservationDetail
import woowacourse.movie.domain.mock.MovieMock
import woowacourse.movie.mapper.MovieMapper.toView
import woowacourse.movie.mapper.ReservationDetailMapper.toView
import woowacourse.movie.system.StateContainer
import java.time.LocalDate
import java.time.LocalDateTime

class MovieReservationPresenterTest {
    lateinit var movieReservationPresenter: MovieReservationContract.Presenter
    lateinit var view: MovieReservationContract.View

    @Before
    fun init() {
        view = mockk()
        movieReservationPresenter = MovieReservationPresenter(view, Count(3))
    }

    @Test
    fun addPeopleCount() {
        // given
        val slot = slot<Int>()
        every { view.setCounterText(capture(slot)) } just runs

        // when
        val expect = 4
        movieReservationPresenter.addPeopleCount(1)

        // then
        val actual = slot.captured
        assertEquals(expect, actual)
        verify { view.setCounterText(expect) }
    }

    @Test
    fun minusPeopleCount() {
        // given
        val slot = slot<Int>()
        every { view.setCounterText(capture(slot)) } just runs

        // when
        val expect = 2
        movieReservationPresenter.minusPeopleCount(1)

        // then
        val actual = slot.captured
        assertEquals(expect, actual)
        verify { view.setCounterText(expect) }
    }

    @Test
    fun reserveMovie() {
        // given
        val reservationDetailSlot = slot<ReservationDetailViewData>()
        every {
            view.startReservationResultActivity(
                capture(reservationDetailSlot), any()
            )
        } just runs

        // when
        val movie = MovieMock.createMovie().toView()
        val date = LocalDateTime.of(2020, 3, 1, 0, 0)
        val expect = ReservationDetail(date, 3).toView()
        movieReservationPresenter.reserveMovie(date, movie)

        // then
        val actual = reservationDetailSlot.captured

        assertEquals(expect, actual)
        verify { view.startReservationResultActivity(expect, any()) }
    }

    @Test
    fun selectDate() {
        // given
        every { view.setTimeSpinner(any()) } just runs

        // when
        val date = LocalDate.of(2020, 3, 1)
        movieReservationPresenter.selectDate(date)

        // then
        verify { view.setTimeSpinner(any()) }
    }

    @Test
    fun save() {
        // given
        val stateContainer: StateContainer = mockk()
        val slot = slot<Int>()
        every { stateContainer.save(any(), capture(slot)) } just runs

        // when
        val expect = 3
        movieReservationPresenter.save(stateContainer)

        // then
        verify { stateContainer.save(any(), expect) }
    }

    @Test
    fun load() {
        // given
        val stateContainer: StateContainer = mockk()
        every { stateContainer.load(any()) } returns 3
        val peopleCount = slot<Int>()
        every { view.setCounterText(capture(peopleCount)) } just runs

        // when
        val expect = 3
        movieReservationPresenter.load(stateContainer)

        // then
        val actual = stateContainer.load("")
        assertEquals(expect, actual)
        verify { view.setCounterText(expect) }
    }
}
