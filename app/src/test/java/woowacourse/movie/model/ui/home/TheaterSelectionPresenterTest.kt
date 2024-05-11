package woowacourse.movie.model.ui.home

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.database.movie.MovieContentDao
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.theater.TheaterDao
import woowacourse.movie.ui.home.TheaterSelectionContract
import woowacourse.movie.ui.home.TheaterSelectionPresenter

class TheaterSelectionPresenterTest {
    private lateinit var movieContentDao: MovieContentDao
    private lateinit var theaterDao: TheaterDao
    private lateinit var db: MovieDatabase
    private lateinit var view: TheaterSelectionContract.View
    private lateinit var presenter: TheaterSelectionPresenter

    @BeforeEach
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).build()
        movieContentDao = db.movieContentDao()
        theaterDao = db.theaterDao()
        view = mockk<TheaterSelectionContract.View>(relaxed = true)
        presenter = TheaterSelectionPresenter(view, movieContentDao, theaterDao)
    }

    @Test
    fun `영화의 id를 넘겨주면 영화가 상영되는 극장 정보를 표출한다`() {
        // given

        // when
        presenter.loadTheaters(0L)

        // then
        verify { view.showTheaters(any(), any()) }
    }

    @Test
    fun `유효하지_않은_영화_아이디가_주어졌을_때_오류메시지를_표출한다`() {
        // given

        // when
        presenter.loadTheaters(-1L)

        // then
        verify {
            view.showError(any())
        }
    }
}
