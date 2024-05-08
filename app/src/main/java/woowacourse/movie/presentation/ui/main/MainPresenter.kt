package woowacourse.movie.presentation.ui.main

import woowacourse.movie.domain.repository.PreferenceRepository
import woowacourse.movie.presentation.model.MessageType

class MainPresenter(
    private val view: MainContract.View,
    private val repository: PreferenceRepository,
) : MainContract.Presenter {
    override fun changeNotificationMode(mode: Boolean) {
        repository.saveNotificationMode(mode).onSuccess {
            view.showSnackBar(MessageType.NotificationSuccessMessage)
        }.onFailure { e ->
            view.showSnackBar(e)
        }
    }
}
