package woowacourse.movie.model.db

import android.content.Context
import androidx.room.Room

class UserTicketRepository private constructor(context: Context) {
    private val database: UserTicketDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            UserTicketDatabase::class.java,
            "user_ticket_db",
        ).build()

    private val userTicketDao = database.userTicketDao()

    fun getUserTicket(id: Long): UserTicket = userTicketDao.find(id)

    fun getUserTickets(): List<UserTicket> = userTicketDao.findAll()

    fun insertUserTicket(userTicket: UserTicket) = userTicketDao.insert(userTicket)

    fun deleteUserTicket(userTicket: UserTicket) = userTicketDao.delete(userTicket)

    fun deleteAll() = userTicketDao.deleteAll()

    companion object {
        private const val ERROR_INSTANCE = "인스턴스가 초기화 되지 않았습니다."

        private var instance: UserTicketRepository? = null

        fun initialize(context: Context) {
            if (instance == null) instance = UserTicketRepository(context)
        }

        fun instance(): UserTicketRepository {
            return instance ?: throw IllegalStateException(ERROR_INSTANCE)
        }
    }
}
