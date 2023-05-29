package woowacourse.movie.ui.moviedetail

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.uimodel.PeopleCountModel
import woowacourse.movie.uimodel.TheaterModel
import java.time.LocalDate
import java.time.LocalTime

internal class MovieDetailPresenterTest {
    private lateinit var presenter: MovieDetailPresenter
    private lateinit var view: MovieDetailContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MovieDetailPresenter(
            view,
            TheaterModel(
                "잠실",
                3,
                mapOf<LocalDate, List<LocalTime>>(
                    LocalDate.of(2023, 5, 8) to
                        listOf(LocalTime.of(11, 0), LocalTime.of(17, 0)),
                    LocalDate.of(2023, 5, 9) to
                        listOf(LocalTime.of(14, 0)),
                ),
            ),
        )
    }

    @Test
    fun PeopleCount가_1명일_때_add하면_2명이_된다() {
        val slot = slot<Int>()
        every { view.setPeopleCountView(capture(slot)) } answers { }
        every { view.updatePeopleCount(any()) } just Runs

        presenter.addCount(PeopleCountModel(1))

        val actual = slot.captured
        assertEquals(2, actual)
        verify { view.setPeopleCountView(actual) }
    }

    @Test
    fun PeopleCount가_2명일_때_minus하면_1명이_된다() {
        val slot = slot<Int>()
        every { view.setPeopleCountView(capture(slot)) } answers { }
        every { view.updatePeopleCount(any()) } just runs

        presenter.minusCount(PeopleCountModel(2))

        val actual = slot.captured
        assertEquals(1, actual)
        verify { view.setPeopleCountView(actual) }
    }
}
