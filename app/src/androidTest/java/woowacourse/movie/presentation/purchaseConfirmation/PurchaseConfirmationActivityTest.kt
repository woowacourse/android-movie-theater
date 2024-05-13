package woowacourse.movie.presentation.purchaseConfirmation

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.data.MovieRepositoryStore
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat
import woowacourse.movie.util.testApplicationContext
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class PurchaseConfirmationActivityTest {
    private lateinit var movieRepositoryStore: MovieRepositoryStore

    @Before
    fun setUp() {
        movieRepositoryStore = MovieRepositoryStore.instance(testApplicationContext)
        movieRepositoryStore.setRepository(
            object : MovieRepository {
                override fun loadReservedMovies(): Result<List<Reservation>> {
                    throw UnsupportedOperationException()
                }

                override fun loadReservedMovie(id: Long): Result<Reservation> {
                    return Result.success(
                        Reservation(
                            1,
                            "CGV",
                            Title("차람과 하디의 진지한 여행기"),
                            LocalDateTime.of(2024, 2, 25, 10, 0),
                            RunningTime(230),
                            Synopsis("wow!"),
                            setOf(
                                Seat('A', 1, "B"),
                                Seat('B', 1, "S"),
                            ),
                        ),
                    )
                }

                override fun saveReservation(reservation: Reservation): Result<Long> {
                    throw UnsupportedOperationException()
                }

                override fun deleteAllReservedMovie(): Result<Unit> {
                    throw UnsupportedOperationException()
                }
            },
        )
    }

    @After
    fun tearDown() {
        movieRepositoryStore.clear()
    }

    @Test
    @DisplayName("예약 화면이 보이는지 테스트")
    fun reservation_test() {
        ActivityScenario.launch<PurchaseConfirmationActivity>(
            PurchaseConfirmationActivity.newIntent(
                testApplicationContext,
                1,
            ),
        )
        onView(withId(R.id.movie_title_confirmation))
            .check(matches(ViewMatchers.withText(containsString("차람과 하디의 진지한 여행기"))))
        onView(withId(R.id.reserved_information))
            .check(matches(ViewMatchers.withText(containsString("CGV"))))
    }
}
