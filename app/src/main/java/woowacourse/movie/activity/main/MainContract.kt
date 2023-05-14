package woowacourse.movie.activity.main

interface MainContract {
    interface View {
        var presenter: Presenter

        fun updatePushAlarmSwitch(wantChecked: Boolean)
    }

    interface Presenter {
        fun saveSwitchData(isChecked: Boolean)
    }
}
