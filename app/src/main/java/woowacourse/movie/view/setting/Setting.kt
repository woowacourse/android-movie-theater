package woowacourse.movie.view.setting

interface Setting {
    fun getValue(key: String, default: Boolean = false): Boolean
    fun setValue(key: String, value: Boolean)
}
