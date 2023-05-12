package woowacourse.movie.dto.movie

import java.io.Serializable

data class TheaterUIModel(
    val name: String,
    val screeningTime: Map<Int, List<String>>,
) : Serializable {
    companion object {
        val theater = TheaterUIModel(
            name = "",
            screeningTime = mapOf(
                1 to listOf(""),
            ),
        )
    }
}
