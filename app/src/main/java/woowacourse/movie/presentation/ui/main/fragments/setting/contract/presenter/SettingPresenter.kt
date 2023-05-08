package woowacourse.movie.presentation.ui.main.fragments.setting.contract.presenter

import com.woowacourse.data.datasource.cache.CacheDataSource
import woowacourse.movie.presentation.ui.main.fragments.setting.contract.SettingContract

class SettingPresenter(
    view: SettingContract.View,
    private val cacheDataSource: CacheDataSource,
) : SettingContract.Presenter(view) {

    override fun getPushSwitchState(): Boolean =
        cacheDataSource.getBoolean(PUSH_ALLOW_KEY, false)

    override fun changePushState(newState: Boolean) {
        if (view.checkPushPermission()) {
            updatePushAllow(newState)
        } else {
            view.showPushPermissionDialog()
        }
    }

    override fun updatePushAllow(newState: Boolean) {
        cacheDataSource.setBoolean(PUSH_ALLOW_KEY, newState)
        view.changePushSwitchState(newState)
    }

    companion object {
        internal const val PUSH_ALLOW_KEY = "push_allow_key"
    }
}
