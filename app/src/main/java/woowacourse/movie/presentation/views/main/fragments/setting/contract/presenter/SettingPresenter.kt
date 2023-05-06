package woowacourse.movie.presentation.views.main.fragments.setting.contract.presenter

import com.woowacourse.data.datasource.cache.CacheDataSource
import woowacourse.movie.presentation.views.main.fragments.setting.contract.SettingContract

class SettingPresenter(
    private val cacheDataSource: CacheDataSource,
) : SettingContract.Presenter() {

    override fun getPushSwitchState(): Boolean =
        cacheDataSource.getBoolean(PUSH_ALLOW_KEY, false)

    override fun onPushSwitchClicked(newState: Boolean) {
        if (requireView().checkPushPermission()) {
            updatePushAllow(newState)
        } else {
            requireView().showPushPermissionDialog()
        }
    }

    override fun updatePushAllow(newState: Boolean) {
        cacheDataSource.setBoolean(PUSH_ALLOW_KEY, newState)
        requireView().changePushSwitchState(newState)
    }

    companion object {
        internal const val PUSH_ALLOW_KEY = "push_allow_key"
    }
}
