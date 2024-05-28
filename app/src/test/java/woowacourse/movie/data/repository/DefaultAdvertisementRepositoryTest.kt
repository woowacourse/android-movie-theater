package woowacourse.movie.data.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.source.AdvertisementDataSource
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.ScreenAndAd
import woowacourse.movie.domain.repository.DefaultAdvertisementRepository

class DefaultAdvertisementRepositoryTest {
    private lateinit var repository: DefaultAdvertisementRepository
    private lateinit var advertisementDataSource: AdvertisementDataSource

    @BeforeEach
    fun setUp() {
        advertisementDataSource = FakeAdvertisementDataSource()
        repository = DefaultAdvertisementRepository(advertisementDataSource)
    }

    @Test
    fun loadAdvertisement() {
        val advertisement = repository.loadAdvertisement()
        val expected =
            ScreenAndAd.Advertisement(
                id = 0,
                content = "우아한테크코스",
                image = Image.DrawableImage(1),
            )

        assertThat(advertisement).isEqualTo(expected)
    }
}
