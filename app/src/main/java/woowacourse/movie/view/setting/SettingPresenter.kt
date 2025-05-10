package woowacourse.movie.view.setting

import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.repository.Repository

class SettingPresenter(
    val view: SettingContract.View,
    val repository: Repository<Ticket>,
) : SettingContract.Presenter {
    override fun setNotification() {
        repository.findAll()
            .onSuccess {
                view.setNotification(it)
            }
            .onFailure {
                view.showError(ERR_FAILED_TO_LOAD_TICKETS)
            }
    }

    override fun setPermissionSwitch() {
        view.setPermissionSwitch()
    }

    companion object {
        private const val ERR_FAILED_TO_LOAD_TICKETS = "티켓을 불러오는데 실패했습니다."
    }
}
