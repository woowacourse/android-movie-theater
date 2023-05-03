package woowacourse.app.model

import woowacourse.app.ui.main.home.adapter.HomeViewType

abstract class HomeData {
    abstract val homeViewType: HomeViewType
    abstract val id: Long
}
