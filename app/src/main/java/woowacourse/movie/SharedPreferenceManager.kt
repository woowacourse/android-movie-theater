package woowacourse.movie

interface SharedPreferenceManager {
    fun changeData()
    fun getData(): Boolean
}
