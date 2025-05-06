package woowacourse.movie.domain.model

class Advertisement(
    private val itemsBeforeAd: Int,
    val image: Int,
) {
    fun isInsertAdvertise(count: Int): Boolean {
        return count == itemsBeforeAd
    }
}
