package woowacourse.movie.model

import java.time.LocalDate
import kotlin.time.Duration.Companion.minutes

sealed interface ScreenView

data class Movie(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: ImageUrl = ImageUrl.none(),
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: RunningTime,
) : ScreenView {
    companion object {
        val STUB =
            Movie(
                id = 1,
                title = "해리 포터와 마법사의 돌",
                description =
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든," +
                        " 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                imageUrl = ImageUrl.none(),
                startDate = LocalDate.of(2024, 3, 1),
                endDate = LocalDate.of(2024, 3, 31),
                runningTime = RunningTime(152.minutes),
            )
    }
}

data class Advertisement(
    val imageUrl: ImageUrl = ImageUrl.none(),
) : ScreenView
