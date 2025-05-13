package woowacourse.movie

import androidx.fragment.app.Fragment
import woowacourse.movie.movie.MovieFragment
import woowacourse.movie.reservationfragment.ReservationFragment

enum class CustomFragment(
    val fragment: Fragment,
) {
    BOOKING(ReservationFragment()),
    HOME(MovieFragment()),
    SETTING(SettingFragment());
}
