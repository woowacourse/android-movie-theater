package woowacourse.movie.presentation.home.reservation.detail

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationDetailBinding
import woowacourse.movie.presentation.common.base.BaseFragment
import woowacourse.movie.presentation.common.custom.CustomAlertDialog
import woowacourse.movie.presentation.common.custom.DialogInfo
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.extension.toDateTimeFormatter
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.ReservationCountUiModel
import woowacourse.movie.presentation.common.model.ReservationInfoUiModel
import woowacourse.movie.presentation.common.model.ScreenUiModel
import woowacourse.movie.presentation.common.model.TheaterUiModel
import woowacourse.movie.presentation.home.reservation.seat.ReservationSeatFragment
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationDetailFragment :
    BaseFragment<FragmentReservationDetailBinding>(R.layout.fragment_reservation_detail),
    ReservationDetailContract.View {
    private val presenter: ReservationDetailPresenter by lazy { ReservationDetailPresenter(this) }
    private val dialog: CustomAlertDialog by lazy { CustomAlertDialog(requireContext()) }

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

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val movie = arguments.getParcelableCompat<MovieUiModel>(BUNDLE_KEY_MOVIE)
        val (count, dateTime) = restoreReservationData(savedInstanceState)
        val theater = arguments.getParcelableCompat<TheaterUiModel>(BUNDLE_KEY_THEATER)

        presenter.fetchData(movie, theater, count, dateTime)
        setupDateSpinner()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveSpinnersData(outState)
        saveReservationCount(outState)
    }

    override fun updateReservationCount(reservationCount: ReservationCountUiModel) {
        binding.reservationCount = reservationCount
    }

    override fun showScreen(movie: MovieUiModel) {
        binding.movie = movie

        setupReservationCountControls()
        setupFinishButton()
    }

    override fun notifyNoAvailableDates() {
        dialog.show(noAvailableTimesDialogInfo)
    }

    override fun notifyReservationConfirm(
        reservationInfo: ReservationInfoUiModel,
        screen: ScreenUiModel,
        theaterName: String,
    ) {
        val fragment = ReservationSeatFragment.newInstance(reservationInfo, screen)
        parentFragmentManager.commit {
            add(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
    }

    override fun updateDates(
        dates: List<LocalDate>,
        times: List<LocalTime>,
        selectedDateTime: LocalDateTime?,
    ) {
        binding.dates = dates
        binding.selectedDate = selectedDateTime?.toLocalDate()
        updateTimes(times, selectedDateTime?.toLocalTime())
    }

    override fun updateTimes(
        times: List<LocalTime>,
        selectedTime: LocalTime?,
    ) {
        binding.times = times
        if (binding.selectedTime == null) binding.selectedTime = selectedTime
    }

    private fun setupReservationCountControls() {
        binding.btnReservationCountPlus.setOnClickListener { presenter.updateReservationCount(1) }
        binding.btnReservationCountMinus.setOnClickListener { presenter.updateReservationCount(-1) }
    }

    private fun setupFinishButton() {
        binding.btnReservationFinish.setOnClickListener {
            val (date, time) = selectedSpinnerDateAndTime()
            if (date == null || time == null) {
                showToast(getString(R.string.invalid_reservation_datetime_message))
                return@setOnClickListener
            }

            presenter.onReserve(LocalDateTime.of(date, time))
        }
    }

    private fun setupDateSpinner() {
        binding.spinnerReservationDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selected = parent.getItemAtPosition(position) as LocalDate
                    presenter.onSelectDate(selected)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun saveSpinnersData(outState: Bundle) {
        val (date, time) = selectedSpinnerDateAndTime()
        val reservationDateTime =
            if (date != null && time != null) LocalDateTime.of(date, time) else null

        reservationDateTime?.let { dateTime ->
            outState.putString(RESTORE_BUNDLE_KEY_RESERVATION_DATETIME, dateTime.toString())
        }
    }

    private fun saveReservationCount(outState: Bundle) {
        binding.tvReservationCount.text.toString().toIntOrNull()?.let { count ->
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

    private fun selectedSpinnerDateAndTime(): Pair<LocalDate?, LocalTime?> =
        binding.spinnerReservationDate.selectedItem as? LocalDate to binding.spinnerReservationTime.selectedItem as? LocalTime

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
