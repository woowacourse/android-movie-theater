package woowacourse.movie.presentation.ui.detail

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.Quantity
import woowacourse.movie.presentation.ui.utils.DummyData.MOVIE_ID
import woowacourse.movie.presentation.ui.utils.DummyData.THEATER_ID
import woowacourse.movie.presentation.ui.utils.DummyData.dummyScreen
import woowacourse.movie.presentation.ui.utils.DummyData.dummyScreenDetail

@ExtendWith(MockKExtension::class)
class ScreenDetailPresenterTest {
    @MockK
    private lateinit var view: ScreenDetailContract.View

    private lateinit var presenter: ScreenDetailContract.Presenter

    @MockK
    private lateinit var repository: ScreenRepository

    @BeforeEach
    fun setUp() {
        presenter = ScreenDetailPresenter(view, repository)
    }

    @Test
    fun `영화와 상영관 id로 상영 세부 정보를 찾아 뷰에 넘겨준다`() {
        // given
        every { repository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { view.showScreenDetail(dummyScreenDetail) } just runs

        // when
        presenter.loadScreenDetail(0, THEATER_ID)

        // then
        verify { view.showScreenDetail(dummyScreenDetail) }
    }

    @Test
    fun `상영 세부 정보를 찾을 수 없으면 예외를 전달한다`() {
        // given
        val throwable: Throwable = NoSuchElementException()
        every { repository.findByScreenId(THEATER_ID, MOVIE_ID) } returns Result.failure(throwable)
        every { view.showToastMessage(e = throwable) } just runs
        every { view.back() } just runs

        // when
        presenter.loadScreenDetail(THEATER_ID, MOVIE_ID)

        // then
        verify { view.showToastMessage(e = throwable) }
        verify { view.back() }
    }

    @Test
    fun `티켓 수량이 1일 때 증가 시키면, 티켓 수량 2를 뷰에 전달한다`() {
        // given
        val resultQuantity = Quantity(2)
        every { view.updateTicketQuantity(resultQuantity) } just runs

        // when
        val initialQuantity = Quantity(1)
        presenter.increaseQuantity(initialQuantity)

        // then
        verify { view.updateTicketQuantity(resultQuantity) }
    }

    @Test
    fun `티켓 수량이 10일 때 감소 시키면, 티켓 수량 9를 뷰에 전달한다`() {
        // given
        val resultQuantity = Quantity(9)
        every { view.updateTicketQuantity(resultQuantity) } just runs

        // when
        val initialQuantity = Quantity(10)
        presenter.decreaseQuantity(initialQuantity)

        // then
        verify { view.updateTicketQuantity(resultQuantity) }
    }

    @Test
    fun `티켓 수량이 10일 때 증가 시키면, 최대 수량을 넘길 수 없음을 뷰에 알려준다`() {
        // given
        every { view.showSnackBar(MessageType.TicketMaxCountMessage(10)) } just runs

        // when
        val initialQuantity = Quantity(10)
        presenter.increaseQuantity(initialQuantity)

        // then
        verify { view.showSnackBar(MessageType.TicketMaxCountMessage(10)) }
    }

    @Test
    fun `티켓 수량이 1일 때 감소 시키면, 최소 수량보다 작을 수 없음을 뷰에 알려준다`() {
        // given
        every { view.showSnackBar(MessageType.TicketMinCountMessage(1)) } just runs

        // when
        val initialQuantity = Quantity(1)
        presenter.decreaseQuantity(initialQuantity)

        // then
        verify { view.showSnackBar(MessageType.TicketMinCountMessage(1)) }
    }
}
