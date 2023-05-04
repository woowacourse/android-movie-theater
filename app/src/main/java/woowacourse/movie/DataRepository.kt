package woowacourse.movie

interface DataRepository {
    fun setBooleanValue(value: Boolean)

    fun getBooleanValue(default: Boolean): Boolean
}
