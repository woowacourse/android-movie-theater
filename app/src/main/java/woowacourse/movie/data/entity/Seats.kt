package woowacourse.movie.data.entity

import woowacourse.movie.ui.model.seat.ColumnModel
import woowacourse.movie.ui.model.seat.RankModel
import woowacourse.movie.ui.model.seat.RowModel
import woowacourse.movie.ui.model.seat.SeatModel

class Seats {
    val rowSize = 5
    val columnSize = 4
    private val ranks = listOf(RankModel.B, RankModel.B, RankModel.S, RankModel.S, RankModel.A)
    private val seats =
        RowModel.createRows(rowSize).mapIndexed { rowIndex, row ->
            ColumnModel.createColumns(columnSize)
                .map { column -> SeatModel(row, column, ranks[rowIndex]) }
        }

    fun getAll(): Set<SeatModel> = seats.flatten().toSet()

    fun getSeat(rowIndex: Int, columnIndex: Int) = seats[rowIndex][columnIndex]
}
