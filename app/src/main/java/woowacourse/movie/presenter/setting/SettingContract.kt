package woowacourse.movie.presenter.setting

import android.content.Context

interface SettingContract {
    interface View {
        fun showSavedSetting(isPushSetting: Boolean)

        fun saveSetting(isPushSetting: Boolean)
    }

    interface Presenter {
        fun loadSavedSetting(isPushSetting: Boolean)

        fun settingAlarm(
            context: Context,
            isPushSetting: Boolean,
        )
    }
}
