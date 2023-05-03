package woowacourse.movie.presentation.activities.main.fragments.setting.contract.presenter

import com.woowacourse.data.cache.CacheDataStore
import woowacourse.movie.presentation.activities.main.fragments.setting.contract.SettingContract

class SettingPresenter(
    view: SettingContract.View,
    private val cacheDataStore: CacheDataStore,
) : SettingContract.Presenter(view) {

    override fun fetchPushSwitchState() {
        val pushState = cacheDataStore.getBoolean(PUSH_ALLOW_KEY, false)
        view.changePushSwitchState(pushState)
    }

    override fun updatePushAllow(newState: Boolean) {
        cacheDataStore.setBoolean(PUSH_ALLOW_KEY, newState)
        view.changePushSwitchState(newState)
    }

    override fun onPushSwitchClicked(newState: Boolean) {
        if (view.checkPushPermission()) {
            updatePushAllow(newState)
        } else {
            view.showPushPermissionDialog()
        }
    }

    companion object {
        internal const val PUSH_ALLOW_KEY = "push_allow_key"
    }
}
