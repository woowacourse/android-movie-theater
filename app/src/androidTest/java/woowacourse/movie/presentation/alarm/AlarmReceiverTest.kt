package woowacourse.movie.presentation.alarm

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.NotificationRepository
import woowacourse.movie.presentation.alarm.AlarmHelper
import woowacourse.movie.presentation.alarm.AlarmReceiver
import woowacourse.movie.presentation.common.model.TicketUiModel
import java.time.LocalDateTime

class AlarmReceiverTest {
    private lateinit var context: Context
    private lateinit var intent: Intent
    private lateinit var repository: NotificationRepository
    private lateinit var receiver: AlarmReceiver
    private val ticket =
        TicketUiModel(
            "테스트 티켓",
            "선릉 극장",
            LocalDateTime.now(),
            emptyList(),
            0,
            0,
        )

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        intent = Intent(context, AlarmReceiver::class.java).putExtra(AlarmHelper.KEY_TICKET, ticket)

        repository =
            object : NotificationRepository {
                private var fakeIsEnabled = false

                override fun updateNotificationEnabled(isEnabled: Boolean) {
                    fakeIsEnabled = isEnabled
                }

                override fun notificationEnabled(): Boolean = fakeIsEnabled
            }
        receiver = AlarmReceiver(repository)
    }

    @Test
    fun `알림_수신이_비활성화된_경우_알림이_생성되지_않는다`() {
        repository.updateNotificationEnabled(false)

        receiver.onReceive(context, intent)

        val manager = context.getSystemService(NotificationManager::class.java)
        val result = manager.activeNotifications.find { it.id == ticket.hashCode() }

        assertThat(result).isNull()
    }

    @Test
    fun `알림_수신이_활성화된_경우_알림이_생성된다`() {
        repository.updateNotificationEnabled(true)

        receiver.onReceive(context, intent)

        val manager = context.getSystemService(NotificationManager::class.java)
        val result = manager.activeNotifications.find { it.id == ticket.hashCode() }

        assertThat(result).isNotNull()
    }
}
