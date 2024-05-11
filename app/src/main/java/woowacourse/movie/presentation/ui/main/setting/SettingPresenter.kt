package woowacourse.movie.presentation.ui.main.setting

import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.domain.repository.PreferenceRepository
import woowacourse.movie.presentation.model.message.NotificationMessageType.NotificationFailureMessage
import woowacourse.movie.presentation.model.message.NotificationMessageType.NotificationSuccessMessage
import woowacourse.movie.presentation.model.message.PermissionMessageType

class SettingPresenter(
    private val view: SettingContract.View,
    private val repository: PreferenceRepository,
) : SettingContract.Presenter {
    override fun loadNotificationMode() {
        repository.getNotificationMode().onSuccess { mode ->
            view.showNotificationMode(mode)
        }.onFailure { e ->
            view.showSnackBar(e)
        }
    }

    override fun changeNotificationMode(mode: Boolean) {
        view.checkNotificationPermissions(!mode)
    }

    override fun saveNotificationMode(mode: Boolean) {
        repository.saveNotificationMode(mode).onSuccess {
            val message =
                if (mode) {
                    NotificationSuccessMessage
                } else {
                    NotificationFailureMessage
                }
            view.showNotificationMode(mode)
            view.showSnackBar(message)
        }.onFailure { e ->
            view.showSnackBar(e)
        }
    }

    override fun requestNotificationPermission(
        permissionText: String,
        action: Snackbar.() -> Snackbar,
    ) {
        view.showSnackBar(PermissionMessageType.RequestPermissionMessage(permissionText), action)
    }
}
