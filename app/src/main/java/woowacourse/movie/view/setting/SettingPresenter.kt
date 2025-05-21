package woowacourse.movie.view.setting

import woowacourse.movie.TicketProvider
import woowacourse.movie.data.NotificationRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val repository: NotificationRepository,
) : SettingContract.Presenter {
    override fun initBinding(hasDeviceAlarmPermission: Boolean) {
        view.setCheckNotification(hasDeviceAlarmPermission && repository.getNotificationEnabled())
    }

    override fun notification(
        isChecked: Boolean,
        isDeviceAlarmPermission: Boolean,
        isNotificationPermissionGranted: Boolean,
    ) {
        if (isChecked) {
            handleSwitchOn(isDeviceAlarmPermission, isNotificationPermissionGranted)
        } else {
            repository.setNotificationEnabled(false)
        }
    }

    private fun handleSwitchOn(
        isDeviceAlarmPermission: Boolean,
        isNotificationPermissionGranted: Boolean,
    ) {
        when {
            !isDeviceAlarmPermission -> {
                view.showPermissionAlarmDialog()
                view.setCheckNotification(false)
            }
            !isNotificationPermissionGranted -> {
                view.showPermissionNotificationDialog()
                view.setCheckNotification(false)
            }
            else -> {
                repository.setNotificationEnabled(true)
            }
        }
    }

    companion object {
        fun provideFactory(
            view: SettingContract.View,
            repository: NotificationRepository = TicketProvider.notificationRepository,
        ): SettingContract.Presenter = SettingPresenter(view, repository)
    }
}
