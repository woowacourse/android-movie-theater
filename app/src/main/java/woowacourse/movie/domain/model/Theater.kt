package woowacourse.movie.domain.model

import java.io.Serializable

data class Theater(
    val name: String,
    val cancelableTime: Int = DEFAULT_CANCELABLE_TIME,
) : Serializable {
    companion object {
        private const val DEFAULT_CANCELABLE_TIME = 15
    }
}
