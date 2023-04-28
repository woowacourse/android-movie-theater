package woowacourse.movie.ui.entity

import woowacourse.movie.ui.model.seat.ColumnModel
import woowacourse.movie.ui.model.seat.RankModel
import woowacourse.movie.ui.model.seat.RowModel
import woowacourse.movie.ui.model.seat.SeatModel

class Seats {
    private val ranks = listOf(RankModel.B, RankModel.B, RankModel.S, RankModel.S, RankModel.A)
    private val seats =
        RowModel.createRows(ROW_SIZE).flatMapIndexed { rowIndex, row ->
            ColumnModel.createColumns(COLUMN_SIZE)
                .map { column -> SeatModel(row, column, ranks[rowIndex]) }
        }

    fun getAll(): Set<SeatModel> = seats.toSet()

    companion object {
        private const val ROW_SIZE = 5
        private const val COLUMN_SIZE = 4
    }
}
