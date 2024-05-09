package woowacourse.movie.model.db

interface UserTicketRepository {
    fun find(id: Long): UserTicket

    fun findAll(): List<UserTicket>

    fun insert(userTicket: UserTicket): Long

    fun delete(userTicket: UserTicket)

    fun deleteAll()
}
