package woowacourse.movie.dto.movie

object TheaterDummy {

    val theaterDatas =
        listOf<TheaterUIModel>(
            TheaterUIModel(
                "선릉",
                getSeolleungSchedule(),
            ),
            TheaterUIModel(
                "잠실",
                getJamsilSchedule(),
            ),
            TheaterUIModel(
                "강남",
                getGangNamSchedule(),
            ),
        )

    private fun getGangNamSchedule(): Map<Int, List<String>> {
        val schedule = mutableMapOf<Int, List<String>>()
        repeat(1000) {
            schedule[it] = listOf("9:00", "11:00", "13:00", "15:00")
        }
        return schedule
    }

    private fun getSeolleungSchedule(): Map<Int, List<String>> {
        val schedule = mutableMapOf<Int, List<String>>()
        repeat(1000) {
            schedule[it] = listOf("10:00", "12:00", "13:00")
        }
        return schedule
    }

    private fun getJamsilSchedule(): Map<Int, List<String>> {
        val schedule = mutableMapOf<Int, List<String>>()
        repeat(1000) {
            schedule[it] = listOf("13:00", "15:00", "17:00", "19:00", "21:00", "23:00")
        }
        return schedule
    }
}
