package domain

import java.time.LocalDate

class Theaters(val list: List<Theater>) {
    fun getStartDate(): LocalDate {
        return list.minBy { it.startDate }.startDate
    }

    fun getEndDate(): LocalDate {
        return list.maxBy { it.endDate }.endDate
    }
}
