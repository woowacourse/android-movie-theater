package woowacourse.movie.ui.home

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TheaterSelectionBottomSheetFragmentTest {
    @Test
    fun `극장을_선택할_수_있는_다이얼로그가_표시된다`() {
        val fragmentArgs = bundleOf("movie_content_id" to 0L)
        val scenario = launchFragment<TheaterSelectionBottomSheetFragment>(fragmentArgs)
        scenario.onFragment {
            assertEquals(it.dialog?.isShowing, true)
        }
    }
}
