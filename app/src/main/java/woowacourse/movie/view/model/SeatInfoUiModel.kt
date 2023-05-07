package woowacourse.movie.view.model
typealias row = Int
typealias colorId = Int
class SeatInfoUiModel(val maxRow: Int, val maxCol: Int, val colorOfRow: Map<row, colorId>)
