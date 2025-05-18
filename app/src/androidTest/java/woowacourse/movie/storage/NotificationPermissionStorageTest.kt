package woowacourse.movie.storage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.storage.DefaultNotificationPermissionStorage
import woowacourse.movie.data.storage.NotificationPermissionStorage
import woowacourse.movie.fakeContext

class NotificationPermissionStorageTest {
    private lateinit var storage: NotificationPermissionStorage

    @BeforeEach
    fun setup() {
        storage = DefaultNotificationPermissionStorage(fakeContext)
        storage.updateNotificationPermission(false)
    }

    @Test
    fun 알람_권한_허용_초기_상태는_false이다() {
        assertThat(storage.notificationPermission).isFalse
    }

    @Test
    fun 알람_권한을_true로_업데이트하면_알람_권한_허용_상태_값은_true이다() {
        // given:
        // when:
        storage.updateNotificationPermission(true)

        // then:
        assertThat(storage.notificationPermission).isTrue()
    }

    @Test
    fun 알람_권한을_false로_업데이트하면_알람_권한_허용_상태_값은_false이다() {
        // given:
        // when:
        storage.updateNotificationPermission(false)

        // then:
        assertThat(storage.notificationPermission).isFalse()
    }

    @AfterEach
    fun finish() {
    }
}
