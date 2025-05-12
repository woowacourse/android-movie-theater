package woowacourse.movie

enum class TabFragmentId(
    val value: Int
) {
    BOOKING(R.id.navigation_booking),
    HOME(R.id.navigation_home),
    SETTING(R.id.navigation_settings);

    companion object {
        fun from(tabFragmentId: TabFragmentId): CustomFragment =
            when (tabFragmentId) {
                BOOKING -> CustomFragment.BOOKING
                HOME -> CustomFragment.HOME
                SETTING -> CustomFragment.SETTING
            }

        fun from(value: Int): TabFragmentId? =
            entries.find { it.value == value }
    }
}
