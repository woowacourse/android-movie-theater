package woowacourse.movie.view.state.dataSource

import java.io.Serializable

interface DataSource<T> : Serializable {
    val value: List<T>
    fun add(t: T)
}
