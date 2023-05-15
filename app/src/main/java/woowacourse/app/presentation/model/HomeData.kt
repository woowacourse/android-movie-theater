package woowacourse.app.presentation.model

import woowacourse.app.presentation.ui.main.home.adapter.HomeViewType

abstract class HomeData {
    abstract val homeViewType: HomeViewType
    abstract val id: Long
}
