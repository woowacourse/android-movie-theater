package woowacourse.movie.model.ui.home

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.movie.MovieContentDao
import woowacourse.movie.ui.home.MovieHomeContract
import woowacourse.movie.ui.home.MovieHomePresenter

class MovieHomePresenterTest {
    private lateinit var movieContentDao: MovieContentDao
    private lateinit var db: MovieDatabase
    private lateinit var presenter: MovieHomePresenter
    private lateinit var view: MovieHomeContract.View

    @BeforeEach
    fun setUp() {
        db =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                MovieDatabase::class.java,
            ).build()
        movieContentDao = db.movieContentDao()
        view = mockk<MovieHomeContract.View>(relaxed = true)
        presenter = MovieHomePresenter(view, movieContentDao)
    }

    @Test
    fun `영화 목록을 가져온다`() {
        // given

        // when
        presenter.loadMovieContents()

        // then
        verify { view.showMovieContents(any()) }
    }
}
