package domain

import java.time.LocalDate
import java.time.LocalDateTime

data class Theater(val name: String, val screenTimes: List<LocalDateTime>){
    val startDate: LocalDate = screenTimes.min().toLocalDate()
    val endDate: LocalDate = screenTimes.max().toLocalDate()
}
