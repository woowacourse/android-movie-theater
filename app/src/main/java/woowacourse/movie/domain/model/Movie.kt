package woowacourse.movie.domain.model

import java.io.Serializable

data class Movie(
    val id: Int,
    val title: String,
    val runningTime: Int,
    val description: String,
) : Serializable {
    companion object {
        val NULL =
            Movie(
                id = -1,
                title = "",
                runningTime = -1,
                description = "",
            )
    }
}
