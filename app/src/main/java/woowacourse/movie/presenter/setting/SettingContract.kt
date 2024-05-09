package woowacourse.movie.presenter.setting

import android.content.Context

interface SettingContract {
    interface View {
        fun showSavedSetting(isPushSetting: Boolean)

        fun showPushSettingOnToast()
        fun showPushSettingOffToast()
    }

    interface Presenter {
        fun loadSavedSetting()

        fun settingPushAlarmState(isPushSetting: Boolean)
    }
}
