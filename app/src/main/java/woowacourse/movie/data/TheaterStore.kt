package woowacourse.movie.data

import woowacourse.movie.domain.model.theater.Screening
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TheaterStore(
    private val movies: MovieStore,
) {
    fun theaters() =
        Theaters(
            listOf(
                Theater(
                    name = "선릉 극장",
                    movieSchedules =
                        generateScreenings(
                            theaterStartDate = LocalDate.of(2025, 5, 4),
                            screeningDays = 40,
                            openTime = LocalTime.of(8, 0),
                            closeTime = LocalTime.of(23, 0),
                        ),
                ),
                Theater(
                    name = "잠실 극장",
                    movieSchedules =
                        generateScreenings(
                            theaterStartDate = LocalDate.of(2025, 5, 1),
                            screeningDays = 30,
                            openTime = LocalTime.of(8, 0),
                            closeTime = LocalTime.of(23, 0),
                        ),
                ),
                Theater(
                    name = "잠실 극장",
                    movieSchedules =
                        generateScreenings(
                            theaterStartDate = LocalDate.of(2025, 5, 1),
                            screeningDays = 30,
                            openTime = LocalTime.of(7, 0),
                            closeTime = LocalTime.of(23, 0),
                        ),
                ),
            ),
        )

    private fun generateScreenings(
        theaterStartDate: LocalDate,
        screeningDays: Long,
        openTime: LocalTime,
        closeTime: LocalTime,
    ): List<Screening> {
        val theaterEndDate = theaterStartDate.plusDays(screeningDays)

        return movies.getAll().flatMap { movie ->
            val actualStart = maxOf(theaterStartDate, movie.screeningStartDate)
            val actualEnd = minOf(theaterEndDate, movie.screeningEndDate)

            if (actualStart > actualEnd) return@flatMap emptyList()

            val dateRange = generateDateRange(actualStart, actualEnd)

            val timeSlot = generateTimeSlots(movie.runningTime, openTime, closeTime)

            generateDateSlot(movie.id, dateRange, timeSlot)
        }
    }

    private fun generateDateRange(
        start: LocalDate,
        endInclusive: LocalDate,
    ): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()
        var current = start
        while (!current.isAfter(endInclusive)) {
            dates.add(current)
            current = current.plusDays(1)
        }
        return dates
    }

    private fun generateDateSlot(
        movieId: Int,
        dateRange: List<LocalDate>,
        timeSlot: List<LocalTime>,
    ): List<Screening> {
        return dateRange.flatMap { date ->
            generateScreenings(movieId, date, timeSlot)
        }
    }

    private fun generateScreenings(
        movieId: Int,
        date: LocalDate,
        timeSlot: List<LocalTime>,
    ): List<Screening> {
        return timeSlot.map { time ->
            Screening(movieId, LocalDateTime.of(date, time))
        }
    }

    private fun generateTimeSlots(
        movieRunningTime: Int,
        openTime: LocalTime,
        closeTime: LocalTime,
    ): List<LocalTime> {
        val slots = mutableListOf<LocalTime>()

        val totalScreeningTime = movieRunningTime + MOVIE_SCREENING_INTERVAL
        val latestStartTime = closeTime.minusMinutes(totalScreeningTime)

        var current = openTime
        while (current <= latestStartTime) {
            slots.add(current)
            current = current.plusMinutes(totalScreeningTime)
        }
        return slots
    }

    companion object {
        private const val MOVIE_SCREENING_INTERVAL = 30L
    }
}
