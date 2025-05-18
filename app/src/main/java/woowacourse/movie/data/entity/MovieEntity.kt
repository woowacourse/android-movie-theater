package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster") val poster: Int,
    @ColumnInfo(name = "movieStartDateYear") val movieStartDateYear: Int,
    @ColumnInfo(name = "movieStartDateMonth") val movieStartDateMonth: Int,
    @ColumnInfo(name = "movieStartDateDay") val movieStartDateDay: Int,
    @ColumnInfo(name = "movieEndDateYear") val movieEndDateYear: Int,
    @ColumnInfo(name = "movieEndDateMonth") val movieEndDateMonth: Int,
    @ColumnInfo(name = "movieEndDateDay") val movieEndDateDay: Int,
    @ColumnInfo(name = "runningTime") val runningTime: Int,
)
