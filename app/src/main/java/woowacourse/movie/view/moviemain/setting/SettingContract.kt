package woowacourse.movie.view.moviemain.setting

import androidx.activity.result.ActivityResultLauncher

interface SettingContract {
    interface View {
        var presenter: Presenter
        val requestPermissionLauncher: ActivityResultLauncher<String>
        fun setToggle(isOn: Boolean)
        fun requestNotificationPermission(): Boolean
    }

    interface Presenter {
        fun initToggle()
        fun onClick(isOn: Boolean)
    }
}
