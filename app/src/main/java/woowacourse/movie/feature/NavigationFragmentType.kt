package woowacourse.movie.feature

import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.feature.home.movie.MovieHomeFragment
import woowacourse.movie.feature.reservationlist.ReservationListFragment
import woowacourse.movie.feature.setting.SettingFragment
import java.lang.IllegalArgumentException

enum class NavigationFragmentType(val tag: String) {
    RESERVATION_LIST("reservationListFragment"),
    MOVIE_HOME("movieHomeFragment"),
    SETTING("settingFragment"),
    ;

    fun newInstance(): Fragment {
        return when (this) {
            RESERVATION_LIST -> ReservationListFragment()
            MOVIE_HOME -> MovieHomeFragment()
            SETTING -> SettingFragment()
        }
    }

    companion object {
        private const val INVALID_MENU_ITEM_ID_MESSAGE = "해당하는 아이디의 아이템이 존재하지 않습니다."

        fun from(menuItemId: Int): NavigationFragmentType {
            return when (menuItemId) {
                R.id.reservation_list_item -> RESERVATION_LIST
                R.id.home_item -> MOVIE_HOME
                R.id.setting_item -> SETTING
                else -> throw IllegalArgumentException(INVALID_MENU_ITEM_ID_MESSAGE)
            }
        }
    }
}
