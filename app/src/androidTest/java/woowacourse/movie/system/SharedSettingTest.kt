package woowacourse.movie.system

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedSettingTest {

    @Test
    fun 설정에_reservation이라는_키를_가지고_true를_저장하면_reservation이라는_키를_가지고_조회했을때_true를_가져올_수_있다() {
        // given
        val context = ApplicationProvider.getApplicationContext<Context>()
        val setting = SharedSetting(context)
        val key = "reservation"
        val expect = true
        setting.setValue(key, true)

        // when
        val actual = setting.getValue(key)

        // then
        Assert.assertEquals(expect, actual)
    }
}
