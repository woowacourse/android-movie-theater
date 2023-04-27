package woowacourse.movie

import org.junit.Assert.assertEquals
import org.junit.Test

class SettingTest {
    @Test
    fun `설정에 reservation이라는 키를 가지고 true를 저장하면 reservation이라는 키를 가지고 조회했을때 true를 가져올 수 있다`() {
        // given
        val setting = FakeSetting()
        val key = "reservation"

        // when
        val expect = true
        setting.setValue(key, expect)

        // then
        val actual = setting.getValue(key)

        assertEquals(expect, actual)
    }
}
