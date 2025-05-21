package woowacourse.movie.fixture.repository

import woowacourse.movie.data.repository.SettingRepository

class FakeSettingRepository(
    private val isSaved: Boolean,
    private val isGranted: Boolean,
) : SettingRepository {
    override fun isSaved(): Boolean = isSaved

    override fun isGranted(): Boolean = isGranted

    override fun saveSettingState(isGranted: Boolean) = Unit
}
