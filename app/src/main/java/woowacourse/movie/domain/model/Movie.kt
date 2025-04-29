package woowacourse.movie.domain.model

data class Movie(
    val title: String,
    val startDate: MovieDate,
    val endDate: MovieDate,
    val runningTime: Int,
    val availableTheaters: List<Theater>,
) {
    companion object {
        val movies: List<Movie> =
            listOf(
                Movie(
                    title = "해리 포터와 마법사의 돌",
                    startDate = MovieDate(2025, 4, 1),
                    endDate = MovieDate(2025, 4, 25),
                    runningTime = 152,
                    availableTheaters =
                        listOf(
                            Theater("선릉", listOf(MovieTime(9, 0), MovieTime(12, 0), MovieTime(15, 0))),
                            Theater("잠실", listOf(MovieTime(10, 0), MovieTime(13, 0))),
                            Theater("강남", listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0), MovieTime(20, 0))),
                        ),
                ),
                Movie(
                    title = "해리 포터와 비밀의 방",
                    startDate = MovieDate(2025, 4, 1),
                    endDate = MovieDate(2025, 4, 28),
                    runningTime = 162,
                    availableTheaters =
                        listOf(
                            Theater("성수", listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0))),
                            Theater("홍대", listOf(MovieTime(10, 0), MovieTime(12, 0), MovieTime(18, 0))),
                        ),
                ),
                Movie(
                    title = "해리 포터와 아즈카반의 죄수",
                    startDate = MovieDate(2025, 5, 1),
                    endDate = MovieDate(2025, 5, 31),
                    runningTime = 141,
                    availableTheaters =
                        listOf(
                            Theater("강남", listOf(MovieTime(9, 0), MovieTime(11, 0))),
                            Theater("성수", listOf(MovieTime(13, 0), MovieTime(15, 0))),
                            Theater("홍대", listOf(MovieTime(17, 0), MovieTime(19, 0))),
                            Theater("선릉", listOf(MovieTime(21, 0))),
                        ),
                ),
                Movie(
                    title = "해리 포터와 불의 잔",
                    startDate = MovieDate(2025, 6, 1),
                    endDate = MovieDate(2025, 6, 30),
                    runningTime = 157,
                    availableTheaters =
                        listOf(
                            Theater("홍대", listOf(MovieTime(10, 0), MovieTime(13, 0))),
                            Theater("잠실", listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0))),
                            Theater("성수", listOf(MovieTime(15, 0), MovieTime(18, 0))),
                        ),
                ),
                Movie(
                    title = "레디 플레이어 원",
                    startDate = MovieDate(2025, 5, 11),
                    endDate = MovieDate(2025, 9, 28),
                    runningTime = 140,
                    availableTheaters =
                        listOf(
                            Theater("강남", listOf(MovieTime(12, 0), MovieTime(15, 0), MovieTime(18, 0))),
                            Theater("선릉", listOf(MovieTime(10, 0), MovieTime(13, 0))),
                        ),
                ),
            )
    }
}
