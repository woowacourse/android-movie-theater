package woowacourse.movie.domain

import kotlin.concurrent.thread

interface Callback<T> {
    fun onSuccess(data: T)

    fun onError(e: Throwable)
}

fun <T> execute(
    task: () -> T,
    callback: Callback<T>,
) {
    thread {
        runCatching {
            task()
        }.onSuccess {
            callback.onSuccess(it)
        }.onFailure {
            callback.onError(it)
        }
    }
}
