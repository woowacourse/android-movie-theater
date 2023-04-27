package woowacourse.movie.system

interface Setting {
    fun getSettingValue(key: String, default: Boolean = false): Boolean
    fun setSettingValue(key: String, value: Boolean)
}
