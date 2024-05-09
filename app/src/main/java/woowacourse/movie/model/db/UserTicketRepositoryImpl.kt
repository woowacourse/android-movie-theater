package woowacourse.movie.model.db

class UserTicketRepositoryImpl private constructor(private val userTicketDao: UserTicketDao) :
    UserTicketRepository {
        override fun find(id: Long): UserTicket = userTicketDao.find(id)

        override fun findAll(): List<UserTicket> = userTicketDao.findAll()

        override fun insert(userTicket: UserTicket): Long = userTicketDao.insert(userTicket)

        override fun delete(userTicket: UserTicket) = userTicketDao.delete(userTicket)

        override fun deleteAll() = userTicketDao.deleteAll()

        companion object {
            private const val ERROR_REPOSITORY = "레포지토리가 초기화 되지 않았습니다."

            private var instance: UserTicketRepository? = null

            fun initializeRepository(userTicketDao: UserTicketDao) {
                if (instance == null) instance = UserTicketRepositoryImpl((userTicketDao))
            }

            fun get(): UserTicketRepository {
                return instance ?: throw IllegalStateException(ERROR_REPOSITORY)
            }
        }
    }
