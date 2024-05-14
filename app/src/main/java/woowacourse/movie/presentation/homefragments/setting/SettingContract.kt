package woowacourse.movie.presentation.homefragments.setting

interface SettingContract {
    interface View {
        fun showSavedSetting(pushSetting: Boolean)

        fun saveSetting(pushSetting: Boolean)
    }

    interface Presenter {
        fun loadSetting(savedSetting: Boolean)

        fun settingAlarm(pushSetting: Boolean)
    }
}
