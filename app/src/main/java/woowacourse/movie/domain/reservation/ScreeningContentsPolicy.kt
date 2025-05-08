package woowacourse.movie.domain.reservation

interface ScreeningContentsPolicy {
    fun screeningContents(): List<ScreeningContent>
}

class DefaultScreeningContentsPolicy(
    private val screenings: List<Screening>,
    private val advertisements: Advertisements,
) : ScreeningContentsPolicy {
    override fun screeningContents(): List<ScreeningContent> {
        val screeningContents: MutableList<ScreeningContent> = mutableListOf()
        var screeningCount = 0

        for (screening in screenings) {
            if (screeningContents.size == CONTENTS_SIZE_MAX) break
            screeningContents.add(screening)
            screeningCount++

            if (screeningCount.shouldAddAdvertisement) {
                screeningContents.add(advertisements.fetchAdvertisement())
            }
        }

        return screeningContents
    }

    private val Int.shouldAddAdvertisement: Boolean get() = this % SCREENINGS_PER_ADVERTISEMENT == 0

    companion object {
        private const val SCREENINGS_PER_ADVERTISEMENT = 3
        private const val CONTENTS_SIZE_MAX = 10_000
    }
}
