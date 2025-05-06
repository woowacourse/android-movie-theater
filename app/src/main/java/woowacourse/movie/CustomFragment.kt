package woowacourse.movie

import androidx.fragment.app.Fragment
import woowacourse.movie.movie.MovieFragment

enum class CustomFragment(
    val fragment: Fragment,
) {
    BOOKING(BookingFragment()),
    HOME(MovieFragment()),
    SETTING(SettingFragment());
}
