package woowacourse.movie.data.dummy

import woowacourse.movie.domain.model.Cinema

object DummyCinema {
    val dummyCinemas =
        listOf(
            Cinema(1, "선릉"),
            Cinema(2, "잠실"),
            Cinema(3, "강남"),
        )
}
