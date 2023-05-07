package woowacourse.movie.view

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.moviereservation.MovieReservationContract
import woowacourse.movie.activity.moviereservation.MovieReservationPresenter
import woowacourse.movie.view.data.DateRangeViewData
import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.TheaterMovieViewData
import woowacourse.movie.view.widget.SaveState
import java.time.LocalDate

class MovieReservationPresenterTest {

    private lateinit var presenter: MovieReservationContract.Presenter
    private lateinit var view: MovieReservationContract.View
    private lateinit var dateSpinnerSaveState: SaveState
    private lateinit var timeSpinnerSaveState: SaveState

    @Before
    fun setUp() {
        view = mockk()
        dateSpinnerSaveState = mockk()
        timeSpinnerSaveState = mockk()

        presenter = MovieReservationPresenter(
            view, dateSpinnerSaveState, timeSpinnerSaveState
        )
    }

    @Test
    fun `시작 날짜와 종료 날짜를 받으면, 기간 내의 모든 날짜와, savedInstance에 저장된 선택된 스피너 인덱스 값을 꺼내서 view 에 넘겨준다`() {
        val theaterMovieViewData: TheaterMovieViewData = mockk()
        // given: 영화의 상영 시작 날짜와 종료 날짜, savedInstance 저장, 로딩 클래스를 mockking 한다
        every {
            theaterMovieViewData.movie.date
        } returns DateRangeViewData(LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 4))
        every { dateSpinnerSaveState.load(null) } returns 2

        // given: view 에 전달되는 매개변수를 capture 한다
        val capturedSpinnerIndex = slot<Int>()
        val capturedFormattedDates = slot<MutableList<LocalFormattedDate>>()
        every {
            view.initDateSpinner(
                capture(capturedSpinnerIndex), capture(capturedFormattedDates)
            )
        } answers { nothing }

        // when: presenter 에게 DateSpinner 초기화를 요청하면
        presenter.initDateSpinner(theaterMovieViewData, null)

        // then: view 는 savedInstance 에 저장된 선택된 스피너 인덱스 값과, 시작 날짜 종료 날짜 사이의 모든 날짜를 받는다.
        val actualIndex = capturedSpinnerIndex.captured
        val actualFormattedDates = capturedFormattedDates.captured
        assertEquals(2, actualIndex)
        assertEquals(
            mutableListOf(
                LocalFormattedDate(LocalDate.of(2023, 5, 1)),
                LocalFormattedDate(LocalDate.of(2023, 5, 2)),
                LocalFormattedDate(LocalDate.of(2023, 5, 3)),
                LocalFormattedDate(LocalDate.of(2023, 5, 4))
            ),
            actualFormattedDates
        )
    }
}
