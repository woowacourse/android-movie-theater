package woowacourse.app.model

import woowacourse.app.ui.main.adapter.MainViewType

abstract class MainData {
    abstract val mainViewType: MainViewType
    abstract val id: Long
}
