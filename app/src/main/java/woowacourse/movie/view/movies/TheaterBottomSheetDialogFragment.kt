package woowacourse.movie.view.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Schedule
import woowacourse.movie.domain.ScheduleTime
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.movietime.Date
import woowacourse.movie.view.movies.adapter.TheaterAdapter
import java.time.LocalDate
import java.time.LocalDateTime

class TheaterBottomSheetDialogFragment(
    val eventListener: OnBottomSheetDialogEventListener,
) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_theater_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.lv_theater_category)

        showMoviesScreen(recyclerView)
    }

    private fun showMoviesScreen(recyclerView: RecyclerView) {
        val theaterAdapter: TheaterAdapter =
            TheaterAdapter(
                object : OnTheaterEventListener {
                    override fun onClickReservation(theater: Theater) {
                        eventListener.onClick(theater)
                        dismiss()
                    }
                },
            )

        val theaters =
            listOf(
                Theater(
                    "선릉점",
                    Schedule(
                        Movie(
                            R.drawable.harry,
                            "해리 포터",
                            Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 30)),
                            152,
                        ),
                        ScheduleTime(
                            listOf(
                                LocalDateTime.of(2025, 1, 1, 1, 0),
                                LocalDateTime.of(2025, 1, 1, 5, 0),
                            ),
                        ),
                    ),
                ),
                Theater(
                    "잠실점",
                    Schedule(
                        Movie(
                            R.drawable.harry,
                            "해리 포터",
                            Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 30)),
                            152,
                        ),
                        ScheduleTime(
                            listOf(
                                LocalDateTime.of(2025, 1, 1, 1, 0),
                                LocalDateTime.of(2025, 1, 1, 5, 0),
                            ),
                        ),
                    ),
                ),
                Theater(
                    "강남점",
                    Schedule(
                        Movie(
                            R.drawable.harry,
                            "해리 포터",
                            Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 30)),
                            152,
                        ),
                        ScheduleTime(
                            listOf(
                                LocalDateTime.of(2025, 1, 1, 1, 0),
                                LocalDateTime.of(2025, 1, 1, 5, 0),
                            ),
                        ),
                    ),
                ),
            )

        recyclerView.adapter = theaterAdapter
        theaterAdapter.submitList(theaters)
    }
}
