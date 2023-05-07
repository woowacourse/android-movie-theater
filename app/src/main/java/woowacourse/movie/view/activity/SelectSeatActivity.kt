package woowacourse.movie.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.ReservationAlarmManager
import woowacourse.movie.contract.SelectSeatContract
import woowacourse.movie.databinding.ActivitySelectSeatBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.*
import woowacourse.movie.setBackgroundColorId
import woowacourse.movie.view.MovieView
import woowacourse.movie.view.SeatTable
import woowacourse.movie.presenter.SelectSeatPresenter
import woowacourse.movie.sql.ReservationDbHelper
import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.*

class SelectSeatActivity : AppCompatActivity(), SelectSeatContract.View {

    override val presenter: SelectSeatContract.Presenter by lazy {
        SelectSeatPresenter(
            view = this,
            ticketOfficeUiModel = receiveTicketOfficeUiModel(),
            movieUiModel = receiveMovieUiModel(),
            reservationDbHelper = ReservationDbHelper(this)
        )
    }
    private lateinit var binding: ActivitySelectSeatBinding
    private val movieUiModel: MovieUiModel by lazy {
        receiveMovieUiModel()
    }

    private val seatTable: SeatTable by lazy {
        SeatTable(
            tableLayout = binding.selectSeatTableLayout,
            rowSize = 5,
            colSize = 4,
            presenter::onClickSeat
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_seat)
        seatTable.makeSeatTable()
        MovieView(title = binding.selectSeatMovieTitleTextView).render(
            movieUiModel
        )
        presenter.onPriceTextChange()
        presenter.onCheckButtonStateChange()
        binding.selectSeatCheckButton.setOnClickListener { presenter.onClickCheckButton() }
    }

    private fun receiveMovieUiModel(): MovieUiModel {
        val movieUiModel = intent.extras?.getSerializableCompat<MovieUiModel>(MOVIE_KEY_VALUE)
            ?: finishActivityWithToast(
                getString(R.string.movie_data_null_error)
            )
        return movieUiModel as MovieUiModel
    }

    private fun receiveTicketOfficeUiModel(): TicketOfficeUiModel {
        val ticketOfficeUiModel = intent.extras?.getSerializableCompat<TicketOfficeUiModel>(
            TICKET_OFFICE_KEY
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


    override fun showDialog() {
        val dialog = createReservationAlertDialog()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun createReservationAlertDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.select_seat_dialog_title)
        builder.setMessage(R.string.select_seat_dialog_message)
        builder.setPositiveButton(R.string.select_seat_dialog_positive_button_text) { dialog, _ ->
            presenter.onClickDialogPositiveButton()
        }
        builder.setNegativeButton(R.string.select_seat_dialog_negative_button_text) { dialog, _ ->
            presenter.onClickDialogCancelButton()
        }
        return builder.create()
    }

    override fun startReservationResultActivity(
        movieUiModel: MovieUiModel,
        ticketsUiModel: TicketsUiModel
    ) {
        ReservationResultActivity.start(
            context = this,
            movieUiModel = movieUiModel,
            ticketsUiModel = ticketsUiModel
        )
    }

    override fun registerAlarm(movieUiModel: MovieUiModel, ticketsUiModel: TicketsUiModel) {
        ReservationAlarmManager(this).registerAlarm(
            movieUiModel = movieUiModel,
            ticketsUiModel = ticketsUiModel
        )
    }

    override fun cancelDialog() = Unit
    override fun updateSeats(ticketsUiModel: TicketsUiModel) {
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

    override fun setCheckButtonColor(isSelected: Boolean) {
        if (isSelected) binding.selectSeatCheckButton.setBackgroundColorId(R.color.select_seat_clickable_check_button_background)
        else binding.selectSeatCheckButton.setBackgroundColorId(R.color.select_seat_non_clickable_check_button_background)
    }

    companion object {
        private const val TICKET_OFFICE_KEY = "ticket_office"
        private const val MOVIE_KEY_VALUE = "movie"
        fun start(
            context: Context,
            ticketOfficeUiModel: TicketOfficeUiModel,
            movieUiModel: MovieUiModel
        ) {
            val intent = Intent(context, SelectSeatActivity::class.java)
            intent.putExtra(TICKET_OFFICE_KEY, ticketOfficeUiModel)
            intent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
            context.startActivity(intent)
        }
    }
}
