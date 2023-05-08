package woowacourse.app.data.theater

data class TheaterEntity(
    val id: Long,
    val movieIds: String,
    val rowSize: Int,
    val columnSize: Int,
    val sRankRange: String,
    val aRankRange: String,
    val bRankRange: String,
) {
    companion object {
        fun convertToSeatRanges(range: String): List<IntRange> {
            val ranges = range.split(",")
            return ranges.map {
                val minMax = it.split("-")
                val min = minMax.first().trim().toInt()
                val max = minMax.last().trim().toInt()
                IntRange(min, max)
            }
        }

        fun convertToMovieIds(movieIds: String): List<Long> {
            val ids = movieIds.split(",")
            return ids.map {
                it.trim().toLong()
            }
        }
    }
}
