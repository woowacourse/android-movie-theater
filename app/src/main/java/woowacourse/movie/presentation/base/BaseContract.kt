package woowacourse.movie.presentation.base

interface BaseContract {
    interface View {
        val presenter: Presenter<View>
    }

    abstract class Presenter<out V : View>(protected val view: V)
}
