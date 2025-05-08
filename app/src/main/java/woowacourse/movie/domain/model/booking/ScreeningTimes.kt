package woowacourse.movie.domain.model.booking

// class ScreeningTimes(
//    private val now: LocalDateTime,
//    private val times: List<LocalTime>,
// ) : Serializable {
//    fun bookableTimes(selectedDate: LocalDate): List<LocalTime> {
//        val isToday = selectedDate.isEqual(now.toLocalDate())
//        return if (isToday) {
//            times.filter { time ->
//                time.isAfter(now.toLocalTime())
//            }
//        } else {
//            times
//        }
//    }
// }
