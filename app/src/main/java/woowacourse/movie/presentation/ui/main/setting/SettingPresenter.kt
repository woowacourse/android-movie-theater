package woowacourse.movie.presentation.ui.main.setting

import woowacourse.movie.domain.repository.PreferenceRepository
import woowacourse.movie.presentation.model.MessageType

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
}
