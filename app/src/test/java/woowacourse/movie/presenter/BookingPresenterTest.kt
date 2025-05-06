 package woowacourse.movie.presenter

 import io.mockk.mockk
 import io.mockk.verify
 import java.time.LocalDate
 import java.time.LocalTime
 import org.junit.jupiter.api.BeforeEach
 import org.junit.jupiter.api.Test
 import woowacourse.movie.ui.booking.contract.BookingContract
 import woowacourse.movie.ui.booking.presenter.BookingPresenter

 class BookingPresenterTest {
    private lateinit var view: BookingContract.View
    private lateinit var presenter: BookingPresenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = BookingPresenter(view)
        presenter.loadBookingInfos(theater = null, movieId = 1L)
    }

     @Test
     fun `프레젠터가 인텐트 정보를 받아오면 뷰는 영화, 인원수, 스피너들을 보여준다`(){
         verify {
             view.showHeadCount(any())
             view.showHeadCount(any())
             view.displayScreeningDateSpinner(any())
             view.displayScreeningTimeSpinner(any())
         }
     }

    @Test
    fun `인원 수가 증가하면 인원 수 텍스트가 업데이트 된다`() {
        presenter.increaseHeadcount()
        verify { view.showHeadCount(any()) }
    }

    @Test
    fun `인원 수가 감소하면 인원 수 텍스트가 업데이트 된다`() {
        presenter.decreaseHeadcount()
        verify { view.showHeadCount(any()) }
    }

    @Test
    fun `날짜 스피너를 업데이트하면 뷰에 반영된다`() {
        presenter.updateScreeningDate(LocalDate.now())
        verify { view.showScreeningDate(any()) }
    }

    @Test
    fun `시간 스피너를 업데이트하면 뷰에 반영된다`() {
        presenter.updateScreeningTime(LocalTime.now())
        verify { view.showScreeningTime(any()) }
    }

    @Test
    fun `날짜 스피너를 업데이트하면 선택된 시간에 따라 시간 스피너가 업데이트 된다`() {
        presenter.updateScreeningDate(LocalDate.now())
        verify { view.displayScreeningTimeSpinnerItems(any()) }
    }

    @Test
    fun `날짜 스피너의 목록 포지션이 바뀌면 날짜 스피너가 업데이트 된다`() {
        presenter.updateScreeningDate(LocalDate.now())
        verify { view.showScreeningDate(any()) }
    }
 }
