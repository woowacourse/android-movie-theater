package woowacourse.movie.domain.model.seat

import woowacourse.movie.domain.model.theater.BookedTicket

interface AlarmScheduler {
    fun scheduleBookingAlarm(bookedTicket: BookedTicket)
}
