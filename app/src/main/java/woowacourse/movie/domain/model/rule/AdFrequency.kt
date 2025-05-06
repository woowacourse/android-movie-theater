package woowacourse.movie.domain.model.rule

@JvmInline
value class AdFrequency(
    val value: Int,
) {
    init {
        require(value >= MINIMUM_FREQUENCY) { INVALID_AD_FREQUENCY_MESSAGE }
    }

    companion object {
        private const val MINIMUM_FREQUENCY = 0
        private const val INVALID_AD_FREQUENCY_MESSAGE =
            "광고 빈도는 $MINIMUM_FREQUENCY 이상이어야 합니다. (현재: %d)"
    }
}
