package woowacourse.movie.presentation.activities.main.fragments.history

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.model.item.Reservation

internal class HistoryFragmentTest {
    private lateinit var view: HistoryContract.View
    private lateinit var db: HistoryDbHelper
    private lateinit var presenter: HistoryPresenter
    private lateinit var reservation: Reservation

    @Before
    fun setUp() {
        view = mockk()
        db = mockk()
        reservation = mockk()
        presenter = HistoryPresenter(view, db)
    }

    @Test
    fun `예매목록 데이터베이스에서 데이터를 가져고, 영화의 제목은 가디언즈 오브 갤럭시다`() {
        // given
        every { reservation.movieTitle } returns "가디언즈 오브 갤럭시"
        every { db.getData() } returns(listOf(reservation))

        val slot = slot<List<Reservation>>()
        every { view.setAdapterData(capture(slot)) } just Runs

        val expected = "가디언즈 오브 갤럭시"

        // when
        presenter.getData()
        val actual = slot.captured[0].movieTitle

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `예매목록을 클릭하면 해당 아이템을 가지고 다른 액티비티로 넘어간다`() {
        // given
        val slot = slot<Reservation>()
        every { reservation.movieTitle } returns "가디언즈 오브 갤럭시"
        every { view.setAdapterListener(capture(slot)) } just Runs
        val expected = "가디언즈 오브 갤럭시"

        // when
        presenter.onClicked(reservation)
        val actual = slot.captured.movieTitle

        // then
        assertEquals(expected, actual)
    }
}
