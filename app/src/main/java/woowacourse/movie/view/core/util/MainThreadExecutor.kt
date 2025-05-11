package woowacourse.movie.view.core.util

fun interface MainThreadExecutor {
    fun execute(block: () -> Unit)
}
