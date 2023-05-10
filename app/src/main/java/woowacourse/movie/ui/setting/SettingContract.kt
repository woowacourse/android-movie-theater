package woowacourse.movie.ui.setting

interface SettingContract {

    interface View {

        fun setSwitch(isChecked: Boolean)
    }

    interface Presenter {

        fun saveSetting(isAvailable: Boolean)
        fun loadSetting()
    }
}
