package woowacourse.domain.util

sealed interface CgvResult<out T> {
    class Success<T>(val data: T) : CgvResult<T>
    class Failure(val error: CgvError) : CgvResult<Nothing>
}

sealed class CgvError(val message: String) {
    sealed class DataSourceError(errorMessage: String) :
        CgvError(dataSourceMessage + errorMessage) {

        class DataSourceNoSuchId : DataSourceError("해당 ID가 없습니다.")

        companion object {
            private val dataSourceMessage: String = "DataSource에서 발생했습니다."
        }
    }
}
