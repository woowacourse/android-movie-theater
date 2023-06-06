package woowacourse.movie.presentation.activities.main.fragments.theaterPicker

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Theater

internal class TheaterPickerDialogPresenterTest {
    private lateinit var view: TheaterPickerDialogContract.View
    private lateinit var movie: Movie
    private lateinit var presenter: TheaterPickerDialogContract.Presenter

    @Before
    fun setup() {
        view = mockk()
        movie = mockk()
        presenter = TheaterPickerDialogPresenter(view, movie)
    }

    @Test
    fun `극장을 선택하면 극장 정보를 가지고 다음 화면으로 이동한다`() {
        // given
        val theater: Theater = mockk()
        every { view.startTicketingActivity(theater) } just Runs

        // when
        presenter.moveTicketingActivity(theater)

        // then
        verify { view.startTicketingActivity(theater) }
    }
}
