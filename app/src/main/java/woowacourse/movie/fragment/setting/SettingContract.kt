package woowacourse.movie.fragment.setting

import android.content.SharedPreferences

interface SettingContract {
    interface View {
        var presenter: Presenter

        fun updateSwitch()
    }

    interface Presenter {
        fun getNotificationState(): Boolean

        fun setSwitchState(isChecked: Boolean)
    }
}
