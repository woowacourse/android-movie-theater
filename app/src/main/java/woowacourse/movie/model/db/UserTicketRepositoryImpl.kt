package woowacourse.movie.model.db

class UserTicketRepositoryImpl private constructor(private val userTicketDao: UserTicketDao) :
    UserTicketRepository {
        override fun find(id: Long): UserTicket = userTicketDao.find(id)

        override fun findAll(): List<UserTicket> = userTicketDao.findAll()

        override fun insert(userTicket: UserTicket): Long = userTicketDao.insert(userTicket)

        override fun delete(userTicket: UserTicket) = userTicketDao.delete(userTicket)

        override fun deleteAll() = userTicketDao.deleteAll()

        companion object {
            private var instance: UserTicketRepository? = null

            fun get(userTicketDao: UserTicketDao): UserTicketRepository {
                return instance ?: synchronized(this) {
                    instance ?: UserTicketRepositoryImpl(userTicketDao).also { instance = it }
                }
            }
        }
    }
