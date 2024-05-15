package woowacourse.movie.list.presenter

import woowacourse.movie.database.NotificationSharedPreference
import woowacourse.movie.list.contract.SettingContract

class SettingPresenter(
    private val view: SettingContract.View,
    private val sharedPreference: NotificationSharedPreference
) : SettingContract.Presenter {
    override fun saveNotificationGranted(isGranted: Boolean) {
        sharedPreference.save(isGranted)
    }
}
