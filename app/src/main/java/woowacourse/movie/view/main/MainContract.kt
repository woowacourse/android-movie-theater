package woowacourse.movie.view.main

interface MainContract {
    interface View

    interface Presenter {
        fun updatePermission(isGranted: Boolean)
    }
}
