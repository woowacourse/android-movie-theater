package woowacourse.movie

import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.ui.booking.BookingContract
import woowacourse.movie.ui.booking.BookingPresenter
import java.time.LocalDate

fun BookingPresenter(view: BookingContract.View): BookingPresenter {

    return BookingPresenter(
        bookingView = view,
        movie = MovieUiModel(
            id = 1,
            title = "해리 포터와 마법사의 돌",
            startDate = LocalDate.of(2023, 4, 26),
            endDate = LocalDate.of(2023, 4, 30),
            runningTime = 152,
            description = "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            thumbnail = R.drawable.harry_potter_thumbnail,
            poster = R.drawable.harry_potter_poster
        )
    )
}