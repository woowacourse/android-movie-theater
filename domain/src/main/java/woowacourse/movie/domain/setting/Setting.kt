package woowacourse.movie.domain.setting

interface Setting {
    fun getValue(key: String, default: Boolean = false): Boolean
    fun setValue(key: String, value: Boolean)
}
