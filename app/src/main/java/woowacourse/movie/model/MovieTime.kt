package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalDateTime

class MovieTime {
    var value: Int = 0
        private set

    fun getTotalTimeSlotCount(
        theater: Theater,
        movie: Movie,
        endDate: LocalDate,
    ): Int {
        val now = LocalDateTime.now()
        var date = now.toLocalDate()
        var count = 0
        while (!date.isAfter(endDate)) {
            count += getTimeTable(now, date, getScreenTimes(theater.name, movie.title)).size
            date = date.plusDays(1)
        }
        return count
    }

    private fun getScreenTimes(
        theaterName: String,
        movieName: String,
    ): List<Int> {
        val screenings =
            mapOf(
                "선릉" to
                    mapOf(
                        "이미테이션게임" to listOf(10, 13, 15, 18),
                        "라라랜드" to listOf(11, 14, 17, 20),
                        "승부" to listOf(9, 12, 19),
                        "줄무늬 파자마를 입은 소년" to listOf(16, 19),
                    ),
                "잠실" to
                    mapOf(
                        "라라랜드" to listOf(9, 12, 15, 18),
                        "AI" to listOf(10, 13, 16, 19),
                        "월플라워" to listOf(11, 14, 17),
                        "야당" to listOf(20),
                    ),
                "강남" to
                    mapOf(
                        "이미테이션게임" to listOf(9, 12, 15),
                        "승부" to listOf(11, 14, 17),
                        "AI" to listOf(10, 13, 16, 20),
                        "월플라워" to listOf(18, 21),
                        "줄무늬 파자마를 입은 소년" to listOf(19),
                        "야당" to listOf(10, 14),
                    ),
            )
        return screenings[theaterName]?.get(movieName) ?: emptyList()
    }

    fun getTimeTable(
        now: LocalDateTime,
        selectedDate: LocalDate,
        screenTimes: List<Int>,
    ): List<Int> {
        if (now.toLocalDate() == selectedDate) {
            return screenTimes.timeTable(now.hour)
        }
        return screenTimes
    }

    fun updateTime(newTime: Int) {
        value = newTime
    }

    private fun List<Int>.timeTable(nowHour: Int): List<Int> {
        forEachIndexed { index, time ->
            if (time > nowHour) {
                return slice(index..<size)
            }
        }
        return emptyList()
    }
}
