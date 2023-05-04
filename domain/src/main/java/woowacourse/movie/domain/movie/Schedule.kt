package woowacourse.movie.domain.movie

data class Schedule(val schedule: Map<String, ScreeningDateTimes>) // Theater name to Map<LocalDate, List<LocalTimes>
