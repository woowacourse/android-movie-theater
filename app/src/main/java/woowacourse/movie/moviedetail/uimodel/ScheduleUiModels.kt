package woowacourse.movie.moviedetail.uimodel

class ScheduleUiModels(private val scheduleUiModels: List<ScheduleUiModel>) {
    fun timesAt(position: Int): List<String> = scheduleUiModels[position].times

    fun dates(): List<String> = scheduleUiModels.map { it.date }

    fun defaultTimes(): List<String> {
        return scheduleUiModels.first().times.toList()
    }
}
