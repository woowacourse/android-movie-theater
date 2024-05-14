package woowacourse.movie.list.presenter

import androidx.fragment.app.Fragment
import woowacourse.movie.database.NotificationSharedPreference
import woowacourse.movie.list.contract.SettingContract

class SettingPresenter(
    private val view: SettingContract.View
) : SettingContract.Presenter {
    override fun saveNotificationGranted(isGranted: Boolean) {
        val applicationContext = (view as Fragment).requireContext().applicationContext
        val sharedPreference = NotificationSharedPreference(applicationContext)
        sharedPreference.save(isGranted)
    }
}
