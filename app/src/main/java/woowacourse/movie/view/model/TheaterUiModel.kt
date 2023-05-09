package woowacourse.movie.view.model

typealias row = Int
typealias colorId = Int

data class TheaterUiModel(
    val name: String,
    val maxRow: Int,
    val maxCol: Int,
    val colorOfRow: Map<row, colorId>,
)
