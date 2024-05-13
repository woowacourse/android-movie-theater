package woowacourse.movie.notification

class CurrentTimeProvider : TimeProvider {
    override fun getTimeMill(): Long {
        return System.currentTimeMillis()
    }
}
