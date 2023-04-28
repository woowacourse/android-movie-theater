package woowacourse.movie.system

interface Setting {
    fun getValue(key: String, default: Boolean = false): Boolean
    fun setValue(key: String, value: Boolean)
}
