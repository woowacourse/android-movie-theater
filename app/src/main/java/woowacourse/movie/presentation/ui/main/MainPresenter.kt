package woowacourse.movie.presentation.ui.main

import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.domain.repository.PreferenceRepository
import woowacourse.movie.presentation.model.message.NotificationMessageType
import woowacourse.movie.presentation.model.message.PermissionMessageType.RequestPermissionMessage

class MainPresenter(
    private val view: MainContract.View,
    private val repository: PreferenceRepository,
) : MainContract.Presenter {
    override fun changeNotificationMode(mode: Boolean) {
        repository.saveNotificationMode(mode).onSuccess {
            val message =
                if (mode) {
                    NotificationMessageType.NotificationSuccessMessage
                } else {
                    NotificationMessageType.NotificationFailureMessage
                }
            view.showSnackBar(message)
        }.onFailure { e ->
            view.showSnackBar(e)
        }
    }

    override fun requestNotificationPermission(
        permissionText: String,
        action: Snackbar.() -> Snackbar,
    ) {
        view.showSnackBar(RequestPermissionMessage(permissionText), action)
    }
}
