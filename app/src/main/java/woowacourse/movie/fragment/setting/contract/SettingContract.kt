package woowacourse.movie.fragment.setting.contract

interface SettingContract {
    interface View {
        val presenter: Presenter
        fun setSwitchState(value: Boolean)
        fun onSwitchChangeListener()
    }
    interface Presenter {
        fun onSaveData(data: Boolean)
        fun onLoadData()
    }
}
