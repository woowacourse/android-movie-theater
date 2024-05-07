package woowacourse.movie.model.data

import woowacourse.movie.model.movie.MovieContent
import java.time.LocalDate

val HARRY_PORTER_WIZARD =
    MovieContent(
        imageId = "thumbnail_movie1",
        title = "해리 포터와 마법사의 돌",
        openingMovieDate = LocalDate.of(2024, 3, 1),
        endingMoviesDate = LocalDate.of(2024, 3, 28),
        runningTime = 152,
        synopsis =
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

                "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
        theaterIds = listOf(0L, 1L, 2L),
    )

val HARRY_PORTER_SECRET =
    MovieContent(
        imageId = "thumbnail_movie2",
        title = "해리 포터와 비밀의 방",
        openingMovieDate = LocalDate.of(2024, 4, 1),
        endingMoviesDate = LocalDate.of(2024, 4, 28),
        runningTime = 162,
        synopsis = "《해리 포터와 비밀의 방》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, ",
        theaterIds = listOf(3L, 4L, 5L),
    )

val HARRY_PORTER_PRISONER =
    MovieContent(
        imageId = "thumbnail_movie3",
        title = "해리 포터와 아즈카반의 죄수",
        openingMovieDate = LocalDate.of(2024, 5, 1),
        endingMoviesDate = LocalDate.of(2024, 5, 31),
        runningTime = 141,
        synopsis =
            "《해리 포터와 아즈카반의 죄수》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

                "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
        theaterIds = listOf(8L),
    )

val HARRY_PORTER_FIRE_GLASS =
    MovieContent(
        imageId = "thumbnail_movie4",
        title = "해리 포터와 불의 잔",
        openingMovieDate = LocalDate.of(2024, 6, 1),
        endingMoviesDate = LocalDate.of(2024, 6, 30),
        runningTime = 157,
        synopsis =
            "《해리 포터와 불의 잔》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, " +

                "판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다. ",
        theaterIds = listOf(9L, 10L),
    )
