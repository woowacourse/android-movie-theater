package woowacourse.movie.ui.activity.detail

interface MovieDetailContract {
    interface Presenter {
        fun setPeopleCount(count: Int)

        fun decreasePeopleCount()

        fun increasePeopleCount()
    }

    interface View {
        var presenter: Presenter

        fun setPeopleCount(count: Int)
    }
}
