package woowacourse.movie.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.CopyOnWriteArraySet

class CustomLiveData<T> {
    private var value: T? = null
    private val subscriber: CopyOnWriteArraySet<Observer<T>> = CopyOnWriteArraySet()
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun put(data: T) {
        synchronized(this) {
            value = data
            subscriber.forEach {
                handler.post {
                    it.update(data)
                }
            }
        }
    }

    fun subscribe(observer: Observer<T>) {
        subscriber.add(observer)
    }
}
