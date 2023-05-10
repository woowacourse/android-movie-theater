package woowacourse.movie.view.seatselect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.ReservationAlarmManager
import woowacourse.movie.database.ReservationDbHelper
import woowacourse.movie.databinding.ActivitySelectSeatBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.* // ktlint-disable no-wildcard-imports
import woowacourse.movie.setBackgroundColorId
import woowacourse.movie.view.common.MovieView
import woowacourse.movie.view.reservationresult.ReservationResultActivity
import java.text.NumberFormat
import java.util.*

class SelectSeatActivity : AppCompatActivity(), SelectSeatContract.View {

    private val presenter: SelectSeatContract.Presenter by lazy {
        SelectSeatPresenter(
            view = this,
            ticketOfficeUiModel = receiveTicketOfficeUiModel(),
            movieUiModel = receiveMovieUiModel(),
            reservationDbHelper = ReservationDbHelper(this),
        )
    }
    private lateinit var binding: ActivitySelectSeatBinding
    private val movieUiModel: MovieUiModel by lazy { receiveMovieUiModel() }
    private val seatTable: SeatTable by lazy {
        SeatTable(
            tableLayout = binding.selectSeatTableLayout,
            rowSize = 5,
            colSize = 4,
            ::updateViewBySeatClick,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_seat)
        initView()
    }

    private fun initView() {
        seatTable.makeSeatTable()
        presenter.updateSeatsRank(seatTable.getAllSeats())
        MovieView(title = binding.selectSeatMovieTitleTextView).render(
            movieUiModel,
        )
        presenter.calculatePrice()
        presenter.changeButtonState()
        binding.selectSeatCheckButton.setOnClickListener { presenter.completeReservation() }
    }

    private fun updateViewBySeatClick(seatUiModel: SeatUiModel) {
        presenter.updateTickets(seatUiModel)
        presenter.calculatePrice()
        presenter.changeButtonState()
    }

    private fun receiveMovieUiModel(): MovieUiModel {
        val movieUiModel = intent.extras?.getSerializableCompat<MovieUiModel>(MOVIE_KEY_VALUE)
            ?: finishActivityWithToast(
                getString(R.string.movie_data_null_error),
            )
        return movieUiModel as MovieUiModel
    }

    private fun receiveTicketOfficeUiModel(): TicketOfficeUiModel {
        val ticketOfficeUiModel = intent.extras?.getSerializableCompat<TicketOfficeUiModel>(
            TICKET_OFFICE_KEY,
        ) ?: finishActivityWithToast(getString(R.string.reservation_data_null_error))
        return ticketOfficeUiModel as TicketOfficeUiModel
    }

    private fun finishActivityWithToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setSeatsTextColor(seatRanks: List<SeatRankUiModel>) {
        seatTable.initSeatsTextColor(seatRanks)
    }

    override fun askConfirmReservation() {
        val dialog = createReservationAlertDialog()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun createReservationAlertDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.select_seat_dialog_title)
        builder.setMessage(R.string.select_seat_dialog_message)
        builder.setPositiveButton(R.string.select_seat_dialog_positive_button_text) { _, _ ->
            presenter.showResult()
        }
        builder.setNegativeButton(R.string.select_seat_dialog_negative_button_text) { _, _ -> }
        return builder.create()
    }

    override fun showResultScreen(
        movieUiModel: MovieUiModel,
        ticketsUiModel: TicketsUiModel,
    ) {
        ReservationResultActivity.start(
            context = this,
            movieUiModel = movieUiModel,
            ticketsUiModel = ticketsUiModel,
        )
    }

    override fun setAlarm(movieUiModel: MovieUiModel, ticketsUiModel: TicketsUiModel) {
        ReservationAlarmManager(this).registerAlarm(
            movieUiModel = movieUiModel,
            ticketsUiModel = ticketsUiModel,
        )
    }

    override fun setSeatsBackgroundColor(ticketsUiModel: TicketsUiModel) {
        seatTable.updateTable(ticketsUiModel)
    }

    override fun setPriceText(price: Int) {
        val formattedPrice =
            NumberFormat.getNumberInstance(Locale.US).format(price)
        binding.selectSeatPriceTextView.text = formattedPrice
    }

    override fun setCheckButtonClickable(isClickable: Boolean) {
        binding.selectSeatCheckButton.isClickable = isClickable
    }

    override fun setCheckButtonColorBy(isSelected: Boolean) {
        if (isSelected) {
            binding.selectSeatCheckButton.setBackgroundColorId(R.color.select_seat_clickable_check_button_background)
        } else {
            binding.selectSeatCheckButton.setBackgroundColorId(R.color.select_seat_non_clickable_check_button_background)
        }
    }

    companion object {
        private const val TICKET_OFFICE_KEY = "ticket_office"
        private const val MOVIE_KEY_VALUE = "movie"
        fun start(
            context: Context,
            ticketOfficeUiModel: TicketOfficeUiModel,
            movieUiModel: MovieUiModel,
        ) {
            val intent = Intent(context, SelectSeatActivity::class.java)
            intent.putExtra(TICKET_OFFICE_KEY, ticketOfficeUiModel)
            intent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
            context.startActivity(intent)
        }
    }
}
