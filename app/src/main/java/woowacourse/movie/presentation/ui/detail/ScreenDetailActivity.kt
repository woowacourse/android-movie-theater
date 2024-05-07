package woowacourse.movie.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityScreenDetailBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.model.Quantity
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.ui.seatselection.SeatSelectionActivity
import java.time.LocalDate
import java.time.LocalTime

class ScreenDetailActivity :
    BaseActivity<ActivityScreenDetailBinding>(),
    ScreenDetailContract.View {
    override val layoutResourceId: Int
        get() = R.layout.activity_screen_detail
    private lateinit var presenter: ScreenDetailContract.Presenter
    private val movieId by lazy { intent.getIntExtra(PUT_EXTRA_KEY_MOVIE_ID, DEFAULT_ID) }
    private val theaterId by lazy { intent.getIntExtra(PUT_EXTRA_KEY_THEATER_ID, DEFAULT_ID) }
    private var datePosition = 0
    private var timePosition = 0
    private lateinit var _detailModel: ScreenDetailUiModel
    val detailModel: ScreenDetailUiModel
        get() = _detailModel

    override fun initStartView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.handler = this
        presenter = ScreenDetailPresenter(this, DummyScreens())
        presenter.loadScreenDetail(movieId, theaterId)
        initItemSelectedListener()
    }

    override fun showScreenDetail(screenDetail: ScreenDetailUiModel) {
        _detailModel = screenDetail
        binding.detailModel = screenDetail
        showScreenAdapter(screenDetail.screen)
    }

    private fun showScreenAdapter(screen: Screen) {
        with(screen) {
            showDateSpinnerAdapter(selectableDates)
            showTimeSpinnerAdapter(selectableDates.first())
        }
    }

    private fun showDateSpinnerAdapter(screenDates: List<ScreenDate>) {
        binding.spnDate.apply {
            adapter =
                ArrayAdapter(
                    this@ScreenDetailActivity,
                    android.R.layout.simple_spinner_item,
                    screenDates.map { it.date },
                )
            setSelection(datePosition)
        }
    }

    private fun showTimeSpinnerAdapter(screenDate: ScreenDate) {
        binding.spnTime.apply {
            adapter =
                ArrayAdapter(
                    this@ScreenDetailActivity,
                    android.R.layout.simple_spinner_item,
                    screenDate.getSelectableTimes().map { it },
                )
            setSelection(timePosition)
        }
    }

    private fun initItemSelectedListener() {
        binding.spnDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: android.view.View?,
                    position: Int,
                    id: Long,
                ) {
                    datePosition = position
                    val localDate = parent.getItemAtPosition(position) as LocalDate
                    if (detailModel.selectedDate.date != localDate) {
                        _detailModel =
                            detailModel.copy(selectedDate = ScreenDate(localDate))
                        showTimeSpinnerAdapter(ScreenDate(localDate))
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        binding.spnTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: android.view.View?,
                    position: Int,
                    id: Long,
                ) {
                    _detailModel =
                        detailModel.copy(selectedTime = parent.getItemAtPosition(position) as LocalTime)
                    timePosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    override fun onMinusButtonClicked() {
        presenter.decreaseQuantity(detailModel.quantity)
    }

    override fun onPlusButtonClicked() {
        presenter.increaseQuantity(detailModel.quantity)
    }

    override fun updateTicketQuantity(quantity: Quantity) {
        _detailModel = detailModel.copy(quantity = quantity)
        binding.detailModel = detailModel
    }

    override fun onSeatSelectionButtonClicked() {
        val selectedDate = detailModel.selectedDate
        val reservationInfo =
            ReservationInfo(
                theaterId = this.detailModel.theaterId,
                dateTime = selectedDate.getLocalDateTime(this.detailModel.selectedTime),
                ticketQuantity = this.detailModel.quantity.count,
            )
        SeatSelectionActivity.startActivity(this, reservationInfo, movieId)
    }

    override fun terminateOnError(e: Throwable) {
        showToastMessage(e)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PUT_TICKET_STATE_KEY, detailModel.quantity.count)
        outState.putInt(PUT_STATE_KEY_SELECTED_DATE, datePosition)
        outState.putInt(PUT_STATE_KEY_SELECTED_TIME, timePosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        restoreTicketQuantity(savedInstanceState)
        restoreSelectedDate(savedInstanceState)
        restoreSelectedTime(savedInstanceState)
    }

    private fun restoreTicketQuantity(savedInstanceState: Bundle) {
        val count = savedInstanceState.getInt(PUT_TICKET_STATE_KEY, DEFAULT_TICKET_QUANTITY)
        if (count != DEFAULT_TICKET_QUANTITY) {
            updateTicketQuantity(Quantity(count))
        }
    }

    private fun restoreSelectedDate(savedInstanceState: Bundle) {
        val savedDatePosition =
            savedInstanceState.getInt(PUT_STATE_KEY_SELECTED_DATE)
        datePosition = savedDatePosition
    }

    private fun restoreSelectedTime(savedInstanceState: Bundle) {
        val savedTimePosition =
            savedInstanceState.getInt(PUT_STATE_KEY_SELECTED_TIME)
        timePosition = savedTimePosition
    }

    companion object {
        private const val DEFAULT_ID = -1
        private const val DEFAULT_TICKET_QUANTITY = -1
        private const val PUT_EXTRA_KEY_MOVIE_ID = "movieId"
        private const val PUT_EXTRA_KEY_THEATER_ID = "theaterId"

        private const val PUT_TICKET_STATE_KEY = "ticketQuantity"
        private const val PUT_STATE_KEY_SELECTED_DATE = "selectedDate"
        private const val PUT_STATE_KEY_SELECTED_TIME = "selectedTime"

        fun startActivity(
            context: Context,
            movieId: Int,
            theaterId: Int,
        ) {
            val intent = Intent(context, ScreenDetailActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_MOVIE_ID, movieId)
            intent.putExtra(PUT_EXTRA_KEY_THEATER_ID, theaterId)
            context.startActivity(intent)
        }
    }
}
