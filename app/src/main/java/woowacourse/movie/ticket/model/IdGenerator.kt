package woowacourse.movie.ticket.model

object IdGenerator {
    private var currentId = 0

    fun generateId(): Int {
        return ++currentId
    }
}
