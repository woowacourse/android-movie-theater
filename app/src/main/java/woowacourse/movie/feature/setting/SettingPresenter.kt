package woowacourse.movie.feature.setting

import woowacourse.movie.data.setting.AlarmSetting

class SettingPresenter(
    val view: SettingContract.View,
    val userSetting: AlarmSetting
) : SettingContract.Presenter {

    override fun setMovieReminderChecked(hasPermission: Boolean) {
        view.setMovieReminderChecked(userSetting.isEnable)

        if (!hasPermission) {
            userSetting.isEnable = false
            view.setMovieReminderChecked(false)
            view.requestPermission()
        }
    }

    override fun changeMovieReminderChecked(hasPermission: Boolean, switchChecked: Boolean) {
        if (!userSetting.isEnable && !hasPermission) {
            view.setMovieReminderChecked(false)
            view.requestPermission()
            return
        }

        userSetting.isEnable = switchChecked
    }
}
