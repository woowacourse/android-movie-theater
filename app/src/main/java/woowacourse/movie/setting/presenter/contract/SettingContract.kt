package woowacourse.movie.setting.presenter.contract

interface SettingContract {
    interface View {
        fun setUpAlarmSwitch(savedAlarmSetting: Boolean)
    }

    interface Presenter {
        fun loadAlarmSwitch()
    }
}
