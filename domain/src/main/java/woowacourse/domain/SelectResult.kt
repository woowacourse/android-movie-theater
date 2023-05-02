package woowacourse.domain

sealed interface SelectResult {
    object Unselect : SelectResult

    sealed interface Select : SelectResult {
        object Full : Select
        object Success : Select
    }
}
