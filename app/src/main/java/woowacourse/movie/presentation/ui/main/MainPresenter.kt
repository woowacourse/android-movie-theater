package woowacourse.movie.presentation.ui.main

import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.domain.repository.PreferenceRepository
import woowacourse.movie.presentation.model.MessageType

class MainPresenter(
    private val view: MainContract.View,
    private val repository: PreferenceRepository,
) : MainContract.Presenter {
    override fun changeNotificationMode(mode: Boolean) {
        repository.saveNotificationMode(mode).onSuccess {
            val message =
                if (mode) {
                    MessageType.NotificationSuccessMessage
                } else {
                    MessageType.NotificationFailureMessage
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
        view.showSnackBar(MessageType.RequestPermissionMessage(permissionText), action)
    }
}
