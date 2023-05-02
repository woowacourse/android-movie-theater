package woowacourse.movie.contract

interface MovieListContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter
}
