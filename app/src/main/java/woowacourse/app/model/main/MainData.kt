package woowacourse.app.model.main

import woowacourse.app.ui.main.adapter.MainViewType

sealed class MainData {
    abstract val mainViewType: MainViewType
    abstract val id: Long
}
