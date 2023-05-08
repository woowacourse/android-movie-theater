package woowacourse.movie.ui.main

import woowacourse.movie.R

enum class FragmentsOnBottomNavigation(val resId: Int) {
    RESERVATION(R.id.menu_item_reservation),
    HOME(R.id.menu_item_home),
    SETTING(R.id.menu_item_setting),
    ;

    companion object {
        fun valueOf(id: Int): FragmentsOnBottomNavigation = values().find {
            it.resId == id
        } ?: throw IllegalArgumentException()
    }
}
