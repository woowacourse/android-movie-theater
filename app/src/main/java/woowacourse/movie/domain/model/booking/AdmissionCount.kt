package woowacourse.movie.domain.model.booking

@JvmInline
value class AdmissionCount(val value: Int = MIN_COUNT) {
    fun increase(limit: Int): AdmissionCount {
        val increasedCount = this.value + STEP_SIZE
        return AdmissionCount(increasedCount.coerceAtMost(limit))
    }

    fun decrease(): AdmissionCount {
        val decreasedCount = this.value - STEP_SIZE
        return AdmissionCount((decreasedCount).coerceAtLeast(MIN_COUNT))
    }

    companion object {
        private const val MIN_COUNT = 1
        private const val STEP_SIZE = 1
    }
}
