package woowacourse.movie.database

interface SharedPreferenceManager {
    fun changeData()
    fun getData(): Boolean
}
