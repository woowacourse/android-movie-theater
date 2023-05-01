package woowacourse.movie.data.settings

import junit.framework.TestCase.assertEquals
import org.junit.Test

class SettingsDataAdapterTest {

    object TestSettingsData : SettingsData {
        private val data = mutableMapOf<String, Boolean>()

        override fun setBooleanData(key: String, value: Boolean) {
            data[key] = value
        }

        override fun getBooleanData(key: String, defaultValue: Boolean) = data[key] ?: defaultValue
    }

    @Test
    fun `데이터에 값을 넣지 않으면 DefaultValue 를 반환한다`() {
        // given
        val key = "notification1"
        val defaultValue = false
        val settingsDataAdapter = SettingsDataAdapter(TestSettingsData)

        // when
        val actual = settingsDataAdapter.getBooleanData(key, defaultValue)

        // then
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `key 를 notification 으로 하고 데이터에 true 를 set 하면 get 결과는 true 이다`() {
        // given
        val key = "notification2"
        val value = true
        val defaultValue = false
        val settingsDataAdapter = SettingsDataAdapter(TestSettingsData)

        // when
        settingsDataAdapter.setBooleanData(key, value)
        val actual = settingsDataAdapter.getBooleanData(key, defaultValue)

        // then
        val expected = true
        assertEquals(expected, actual)
    }
}
