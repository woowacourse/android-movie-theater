package woowacourse.movie.system

interface StateContainer {
    fun save(key: String, value: Int)
    fun load(key: String): Int
}
