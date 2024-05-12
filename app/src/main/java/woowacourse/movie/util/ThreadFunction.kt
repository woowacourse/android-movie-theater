package woowacourse.movie.util

import java.util.concurrent.FutureTask

fun <T> runOnOtherThreadAndReturn(function: () -> T): T {
    val task = FutureTask { function() }
    Thread(task).start()
    return task.get()
}
