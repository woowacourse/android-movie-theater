
package woowacourse.movie.data.dao

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.movie.data.entity.TicketBundleEntity
import woowacourse.movie.data.entity.TicketEntity

data class TicketBundleDao(
    @Embedded val bundle: TicketBundleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "bundleId",
    )
    val tickets: List<TicketEntity>,
)
