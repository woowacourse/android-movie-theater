package woowacourse.movie.presentation.view.reservation.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationDetailBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.extension.toDateTimeFormatter
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.ScreenUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.util.DialogInfo
import woowacourse.movie.presentation.view.MovieTheaterActivity
import woowacourse.movie.presentation.view.reservation.seat.ReservationSeatFragment
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationDetailFragment :
    BaseFragment<FragmentReservationDetailBinding>(R.layout.fragment_reservation_detail),
    ReservationDetailContract.View {
    private val presenter: ReservationDetailPresenter by lazy { ReservationDetailPresenter(this) }
    private val views: ReservationDetailViews by lazy {
        ReservationDetailViews(
            requireContext(),
            binding,
        )
    }

    private var shouldIgnoreNextSelection = false

    private val noAvailableTimesDialogInfo: DialogInfo by lazy {
        DialogInfo(
            title = getString(R.string.no_available_times_dialog_title),
            message = getString(R.string.no_available_times_dialog_message),
            positiveButtonText = getString(R.string.no_available_times_dialog_positive),
            onClickPositiveButton = {
                parentFragmentManager.popBackStack()
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as? MovieTheaterActivity)?.setVisibleBottomNavigation(false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()

        shouldIgnoreNextSelection = savedInstanceState != null

        val movie = arguments.getParcelableCompat<MovieUiModel>(BUNDLE_KEY_MOVIE)
        val (count, dateTime) = restoreReservationData(savedInstanceState)
        val theater = arguments.getParcelableCompat<TheaterUiModel>(BUNDLE_KEY_THEATER)

        presenter.fetchData(movie, theater, count, dateTime)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveSpinnersData(outState)
        saveReservationCount(outState)
    }

    override fun updateReservationCount(
        count: Int,
        isEnabled: Boolean,
    ) {
        views.updateReservationCount(count, isEnabled)
    }

    override fun showScreen(movie: MovieUiModel) {
        views.bindMovieInfo(movie)
        setupDateSpinner()
        setupReservationCountControls()
        setupFinishButton()
    }

    override fun notifyNoAvailableDates() {
        views.dialog.show(noAvailableTimesDialogInfo)
    }

    override fun notifyReservationConfirm(
        reservationInfo: ReservationInfoUiModel,
        screen: ScreenUiModel,
        theaterName: String,
    ) {
        val fragment = ReservationSeatFragment.newInstance(reservationInfo, screen)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
    }

    override fun updateDates(
        dates: List<LocalDate>,
        times: List<LocalTime>,
        selectedDateTime: LocalDateTime?,
    ) {
        val selectedDate = selectedDateTime?.toLocalDate()
        val selectedTime = selectedDateTime?.toLocalTime()
        views.updateDateSpinnerItems(dates, selectedDate)
        updateTimes(times, selectedTime)
    }

    override fun updateTimes(
        times: List<LocalTime>,
        selectedTime: LocalTime?,
    ) {
        views.updateTimeSpinnerItems(times, selectedTime)
    }

    override fun notifyReservationLimitReached() {
        showToast(getString(R.string.reservation_count_limit_reached_message))
    }

    private fun setupActionBar() {
        showActionBarBackButton(true)
    }

    private fun setupReservationCountControls() {
        views.setOnReservationCountChanged(
            onDecrease = { presenter.updateReservationCount(-1) },
            onIncrease = { presenter.updateReservationCount(1) },
        )
    }

    private fun setupFinishButton() {
        views.setOnFinishClickListener {
            val (date, time) = views.selectedSpinnerDateAndTime()
            if (date == null || time == null) {
                showToast(getString(R.string.invalid_reservation_datetime_message))
                return@setOnFinishClickListener
            }

            presenter.onReserve(LocalDateTime.of(date, time))
        }
    }

    private fun setupDateSpinner() {
        views.setSpinners(
            onDateSelected = { selectedDate -> presenter.onSelectDate(selectedDate) },
            shouldIgnoreNext = { shouldIgnoreNextSelection },
            clearIgnoreNext = {
                shouldIgnoreNextSelection = false
            },
        )
    }

    private fun saveSpinnersData(outState: Bundle) {
        val (date, time) = views.selectedSpinnerDateAndTime()
        val reservationDateTime =
            if (date != null && time != null) LocalDateTime.of(date, time) else null

        reservationDateTime?.let { dateTime ->
            outState.putString(RESTORE_BUNDLE_KEY_RESERVATION_DATETIME, dateTime.toString())
        }
    }

    private fun saveReservationCount(outState: Bundle) {
        views.reservationCount()?.let { count ->
            outState.putInt(RESTORE_BUNDLE_KEY_RESERVATION_NUMBER, count)
        }
    }

    private fun restoreReservationData(savedInstanceState: Bundle?): Pair<Int?, LocalDateTime?> {
        if (savedInstanceState == null) return null to null
        val count = savedInstanceState.getInt(RESTORE_BUNDLE_KEY_RESERVATION_NUMBER)

        val restoredDateTime = savedInstanceState.getString(RESTORE_BUNDLE_KEY_RESERVATION_DATETIME)
        val formatter = SPINNER_DATETIME_FORMAT.toDateTimeFormatter()
        val dateTime = restoredDateTime?.let { LocalDateTime.parse(it, formatter) }

        return count to dateTime
    }

    companion object {
        private const val BUNDLE_KEY_MOVIE = "movie"
        private const val BUNDLE_KEY_THEATER = "theater"
        private const val RESTORE_BUNDLE_KEY_RESERVATION_DATETIME = "reservation_datetime"
        private const val RESTORE_BUNDLE_KEY_RESERVATION_NUMBER = "reservation_number"
        private const val SPINNER_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm"

        fun newInstance(
            movie: MovieUiModel,
            theater: TheaterUiModel,
        ): ReservationDetailFragment =
            ReservationDetailFragment().apply {
                arguments = bundleOf(BUNDLE_KEY_MOVIE to movie, BUNDLE_KEY_THEATER to theater)
            }
    }
}
