package woowacourse.movie.presentation.activities.main.fragments.setting

import com.woowacourse.data.local.PreferenceManager.setBoolean

class SettingPresenter(
    val view: SettingContract.View,
) : SettingContract.Presenter {
    override fun setPushAllowPreference(key: String, value: Boolean) {
        view.getSharedPreference().setBoolean(key, value)
    }

    override fun getPushAllowPreference(key: String, defaultValue: Boolean): Boolean {
        return view.getSharedPreference().getBoolean(key, defaultValue)
    }

    override fun onSwitchChanged(isPermittedPushPermission: Boolean, isAllowed: Boolean) {
        if (isAllowed && !isPermittedPushPermission) {
            view.showPushPermissionDialog()
        } else {
            setPushAllowPreference(SettingFragment.PUSH_ALLOW_KEY, isAllowed)
        }
    }
}
