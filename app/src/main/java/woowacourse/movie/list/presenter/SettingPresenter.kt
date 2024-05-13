package woowacourse.movie.list.presenter

import android.content.Context
import woowacourse.movie.database.NotificationSharedPreference
import woowacourse.movie.list.contract.SettingContract

class SettingPresenter(
    private val view: SettingContract.View
) : SettingContract.Presenter {
    override fun saveNotificationGranted(isGranted: Boolean) {
        val sharedPreference = NotificationSharedPreference(view as Context)
        sharedPreference.save(isGranted)
    }
}
