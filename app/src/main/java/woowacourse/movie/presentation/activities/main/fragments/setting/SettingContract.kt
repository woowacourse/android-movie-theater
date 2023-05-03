package woowacourse.movie.presentation.activities.main.fragments.setting

import android.content.SharedPreferences

interface SettingContract {
    interface View {
        val presenter: Presenter

        fun getSharedPreference(): SharedPreferences
        fun showPushPermissionDialog()
    }

    interface Presenter {
        fun setPushAllowPreference(key: String, value: Boolean)
        fun getPushAllowPreference(key: String, defaultValue: Boolean): Boolean
        fun onCheckedChangeListener(isPermittedPushPermission: Boolean, isAllowed: Boolean)
    }
}
