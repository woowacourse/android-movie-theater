package woowacourse.movie

enum class ItemId(
    val itemId: Int
) {
    BOOKING(R.id.navigation_booking),
    HOME(R.id.navigation_home),
    SETTING(R.id.navigation_settings);

    companion object {
        fun from(itemId: ItemId): CustomFragment =
            when (itemId) {
                BOOKING -> CustomFragment.BOOKING
                HOME -> CustomFragment.HOME
                SETTING -> CustomFragment.SETTING
            }

        fun from(itemId: Int): ItemId? =
            entries.find { it.itemId == itemId }
    }
}
