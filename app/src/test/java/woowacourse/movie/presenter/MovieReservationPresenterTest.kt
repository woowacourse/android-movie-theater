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
import woowacourse.movie.domain.Movie
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
    fun 예매_인원이_3명일_때_더하기_버튼을_누르면_4명이_된다() {
        // given
        val peopleCountSlot = slot<Int>()
        every { view.setCounterText(capture(peopleCountSlot)) } just runs

        // when
        val expect = 4
        movieReservationPresenter.addPeopleCount(1)

        // then
        val actual = peopleCountSlot.captured
        assertEquals(expect, actual)
        verify { view.setCounterText(expect) }
    }

    @Test
    fun 예매_인원이_3명일_때_빼기_버튼을_누르면_2명이_된다() {
        // given
        val peopleCountSlot = slot<Int>()
        every { view.setCounterText(capture(peopleCountSlot)) } just runs

        // when
        val expect = 2
        movieReservationPresenter.minusPeopleCount(1)

        // then
        val actual = peopleCountSlot.captured
        assertEquals(expect, actual)
        verify { view.setCounterText(expect) }
    }

    @Test
    fun 예매_버튼을_누르면_예매_액티비티를_시작한다() {
        // given
        val reservationDetailSlot = slot<ReservationDetailViewData>()
        every {
            view.startReservationResultActivity(
                capture(reservationDetailSlot), any()
            )
        } just runs

        // when
        val movie = fakeMovie().toView()
        val date = fakeDateTime()
        val expect = fakeReservationDetail(date).toView()
        movieReservationPresenter.reserveMovie(date, movie)

        // then
        val actual = reservationDetailSlot.captured

        assertEquals(expect, actual)
        verify { view.startReservationResultActivity(expect, any()) }
    }

    @Test
    fun 날짜_스피너를_선택하면_시간_정책에_의해_시간_스피너를_설정한다() {
        // given
        every { view.setTimeSpinner(any()) } just runs

        // when
        val date = fakeDate()
        movieReservationPresenter.selectDate(date)

        // then
        verify { view.setTimeSpinner(any()) }
    }

    @Test
    fun 카운터_인원수를_저장한다() {
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
    fun 카운터_인원수를_불러온다() {
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

    private fun fakeMovie(): Movie = MovieMock.createMovie()
    private fun fakeDateTime(): LocalDateTime = LocalDateTime.now()
    private fun fakeDate(): LocalDate = LocalDate.now()
    private fun fakeReservationDetail(date: LocalDateTime): ReservationDetail = ReservationDetail(
        date, 3
    )
}
