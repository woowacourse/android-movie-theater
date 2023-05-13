import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.DataRepository
import woowacourse.movie.activity.main.MainContract
import woowacourse.movie.activity.main.MainPresenter

class MainPresenterTest {
    private lateinit var view: MainContract.View
    private lateinit var dataRepository: DataRepository
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        view = mockk()
        dataRepository = mockk()
        presenter = MainPresenter(view, dataRepository)
    }

    @Test
    fun `스위치의 값을 저장할 수 있다`() {
        // given
        val slot1 = slot<Boolean>()
        val slot2 = slot<Boolean>()
        every { dataRepository.setBooleanValue(any()) } just runs
        every { view.updatePushAlarmSwitch(any()) } just runs

        // when
        presenter.saveSwitchData(true)

        // then
        verify { dataRepository.setBooleanValue(capture(slot1)) }
        verify { view.updatePushAlarmSwitch(capture(slot2)) }
        assertEquals(true, slot1.captured)
        assertEquals(true, slot2.captured)
    }
}
