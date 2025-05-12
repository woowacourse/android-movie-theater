package woowacourse.movie.presenter.home

import io.kotest.core.spec.style.AnnotationSpec.After
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.storage.NotificationPermissionStorage

class HomePresenterTest {
    private lateinit var presenter: HomePresenter
    private lateinit var view: HomeContracts.View
    private lateinit var notificationPermissionStorage: NotificationPermissionStorage

    @BeforeEach
    fun setup() {
        view = mockk()
        notificationPermissionStorage = mockk()
        presenter = HomePresenter(view, notificationPermissionStorage)
    }

    @Test
    fun `뷰를 초기화하면 영화 목록이 보인다`() {
        // given:
        every { view.showMovies(any()) } just Runs

        // when:
        presenter.updateView()

        // then:
        verify { view.showMovies(any()) }
    }

    @Test
    fun `광고를 클릭하면 광고가 보인다`() {
        // given:
        every { view.showAdvertisement(any()) } just Runs

        // when:
        presenter.updateAdvertisement("https://navar.com")

        // then:
        verify { view.showAdvertisement(any()) }
    }

    @Test
    fun `영화를 클릭하면 극장들이 보인다`() {
        // given
        every { view.showTheaters(any()) } just Runs

        // when
        presenter.updateTheater(1L)

        // then
        verify { view.showTheaters(any()) }
    }

    @Test
    fun `알람 권한을 허용하지 않음으로 업데이트하면, 저장소의 알람 권한이 false로 업데이트 된다`() {
        // given:
        every { notificationPermissionStorage.updateNotificationPermission(any()) } just Runs

        // when:
        presenter.updateNotificationPermission(false)

        // then:
        verify { notificationPermissionStorage.updateNotificationPermission(false) }
    }

    @Test
    fun `알람 권한을 허용함으로 업데이트하면, 저장소의 알람 권한이 true로 업데이트 된다`() {
        // given:
        every { notificationPermissionStorage.updateNotificationPermission(any()) } just Runs

        // when:
        presenter.updateNotificationPermission(true)

        // then:
        verify { notificationPermissionStorage.updateNotificationPermission(true) }
    }

    @After
    fun finish() {
        clearAllMocks()
    }
}
