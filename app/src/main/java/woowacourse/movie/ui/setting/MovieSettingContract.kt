package woowacourse.movie.ui.setting

import android.content.Context

interface MovieSettingContract {
    interface View {
        fun showNotificationStatus()
    }

    interface Presenter {
        fun loadNotificationStatus()

        fun cancelNotification(context: Context)
    }
}
