package woowacourse.movie.presentation.common.util

class AdInjector<T>(
    private val adFrequency: Int,
    private val adFactory: () -> T,
) {
    fun inject(items: List<T>): List<T> =
        items
            .chunked(adFrequency)
            .flatMap { chunk ->
                if (isFullChunk(chunk)) chunk + adFactory() else chunk
            }

    private fun isFullChunk(chunk: List<T>) = chunk.size == adFrequency
}
