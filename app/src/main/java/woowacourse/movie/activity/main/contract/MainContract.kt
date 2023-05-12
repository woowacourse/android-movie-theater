package woowacourse.movie.activity.main.contract

interface MainContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        fun saveSettingData(data: Boolean)
    }
}
