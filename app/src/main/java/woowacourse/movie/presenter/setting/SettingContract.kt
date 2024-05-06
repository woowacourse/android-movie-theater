package woowacourse.movie.presenter.setting

interface SettingContract {
    interface View{
        fun showSavedSetting(isPushSetting: Boolean)

    }

    interface Presenter{
        fun loadSavedSetting(isPushSetting: Boolean)
    }
}
