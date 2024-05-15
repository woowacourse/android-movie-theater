package woowacourse.movie.ui.main

import woowacourse.movie.data.preferences.PreferencesUtil
import woowacourse.movie.ui.notification.NotificationContract.KEY_RECEIVE_NOTIFICATION

class MovieMainPresenter(
    private val preferences: PreferencesUtil
) : MovieMainContract.Presenter {
    override fun saveReceiveNotificationActivation(newValue: Boolean) {
        preferences.setBoolean(KEY_RECEIVE_NOTIFICATION, newValue)
    }
}
