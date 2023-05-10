package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.main.MainData
import woowacourse.movie.ui.home.HomeContract
import woowacourse.movie.ui.home.HomePresenter
import woowacourse.movie.model.main.MainModelHandler

class HomePresenterTest {

    private lateinit var homePresenter: HomeContract.Presenter
    private lateinit var view: HomeContract.View

    @Before
    fun setUp() {
        view = mockk()
        homePresenter = HomePresenter(
            view = view,
            repository = MainModelHandler
        )
    }

    @Test
    fun `저장소로부터 광고와 영화 데이터를 받아오면 해당 데이터들을 화면에 표시한다`() {
        // given
        val data = MainModelHandler.getMainData()
        val slotData = slot<List<MainData>>()

        every { view.initAdapter(capture(slotData)) } just Runs

        // when
        homePresenter.initMainData()

        // then
        val actual = slotData.captured
        assertEquals(data, actual)
        verify { view.initAdapter(actual) }
    }
}