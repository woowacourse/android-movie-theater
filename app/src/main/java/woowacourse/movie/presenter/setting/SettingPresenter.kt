package woowacourse.movie.presenter.setting

import woowacourse.movie.data.storage.NotificationPermissionStorage

class SettingPresenter(
    private val view: SettingContracts.View,
    private val notificationPermissionStorage: NotificationPermissionStorage,
) : SettingContracts.Presenter {
    override fun updateNotificationPermission() {
        view.showNotificationPermission(notificationPermissionStorage.notificationPermission)
    }

    override fun updateNotificationPermission(isGranted: Boolean) {
        notificationPermissionStorage.updateNotificationPermission(isGranted)
        view.showNotificationPermission(notificationPermissionStorage.notificationPermission)
    }
}
