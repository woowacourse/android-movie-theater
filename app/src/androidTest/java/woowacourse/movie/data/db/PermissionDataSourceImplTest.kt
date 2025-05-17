package woowacourse.movie.data.db

import android.content.SharedPreferences
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import woowacourse.movie.data.PermissionSharedPreferences
import woowacourse.movie.data.datasource.PermissionDataSourceImpl
import woowacourse.movie.fixture.fakeContext

class PermissionDataSourceImplTest {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var permissionSharedPreferences: PermissionSharedPreferences
    private lateinit var dataSource: PermissionDataSourceImpl

    @Before
    fun setUp() {
        sharedPreferences = fakeContext.getSharedPreferences("TestPrefs", android.content.Context.MODE_PRIVATE)

        permissionSharedPreferences = PermissionSharedPreferences(fakeContext)
        dataSource = PermissionDataSourceImpl(permissionSharedPreferences)
    }

    @Test
    fun `권한이_허용_되면_true를_저장한다`() {
        // given
        dataSource.savePermission(true)

        // when
        val result = dataSource.isGranted()

        // then
        assertEquals(true, result)
    }

    @Test
    fun `권한이_거부_되면_false를_저장한다`() {
        // given
        dataSource.savePermission(false)

        // when
        val result = dataSource.isGranted()

        // then
        assertEquals(false, result)
    }

    @After
    fun tearDown() {
        sharedPreferences.edit().clear().apply()
    }
}
