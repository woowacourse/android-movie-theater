package woowacourse.movie.seats.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatsBinding
import woowacourse.movie.detail.view.MovieInformationDetailActivity
import woowacourse.movie.detail.view.MovieInformationDetailActivity.Companion.EXTRA_COUNT_KEY
import woowacourse.movie.detail.view.MovieInformationDetailActivity.Companion.EXTRA_DATE_KEY
import woowacourse.movie.detail.view.MovieInformationDetailActivity.Companion.EXTRA_TIME_KEY
import woowacourse.movie.list.view.HomeFragment.Companion.EXTRA_MOVIE_ID_KEY
import woowacourse.movie.seats.contract.SeatsContract
import woowacourse.movie.seats.model.Seat
import woowacourse.movie.seats.presenter.SeatsPresenter
import woowacourse.movie.ticket.view.MovieTicketActivity
import java.io.Serializable

class SeatsActivity : AppCompatActivity(), SeatsContract.View {
    override val presenter: SeatsContract.Presenter = SeatsPresenter(this)
    private lateinit var binding: ActivitySeatsBinding
    private var theaterId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seats)
        binding.seats = this
        setContentView(binding.root)
        theaterId = intent.getLongExtra(MovieInformationDetailActivity.EXTRA_THEATER_ID_KEY, -1)
        val ticketCount = intent.getIntExtra(EXTRA_COUNT_KEY, -1)
        presenter.storeTicketCount(ticketCount)
        initSeats()
        setOnSelectSeat()
        processPresenterTask()
    }

    private fun processPresenterTask() {
        presenter.storeMovieId(intent.getLongExtra(EXTRA_MOVIE_ID_KEY, -1))
        presenter.storeDate(intent.getStringExtra(EXTRA_DATE_KEY) ?: "")
        presenter.storeTime(intent.getStringExtra(EXTRA_TIME_KEY) ?: "")
        presenter.setMovieTitleInfo()
        presenter.setPriceInfo()
        presenter.setSeatsTextInfo()
    }

    private fun initSeats() {
        binding.seatsTable.children.filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                initRowOfSeats(tableRow, rowIndex)
            }
    }

    private fun initRowOfSeats(
        tableRow: TableRow,
        rowIndex: Int,
    ) {
        tableRow.children.filterIsInstance<TextView>().forEachIndexed { colIndex, cell ->
            presenter.createSeat(rowIndex, colIndex)
            presenter.initCell(cell)
        }
    }

    override fun initCell(
        cell: TextView,
        seat: Seat,
    ) {
        cell.text = seat.coordinate
        cell.setBackgroundColor(seat.cellBackgroundColor)
    }

    override fun setOnSelectSeat() {
        binding.seatsTable.children.filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                setOnSelectRow(tableRow, rowIndex)
            }
    }

    private fun setOnSelectRow(
        tableRow: TableRow,
        rowIndex: Int,
    ) {
        tableRow.children.filterIsInstance<TextView>().forEachIndexed { colIndex, cell ->
            setOnCellClickListener(cell, rowIndex, colIndex)
        }
    }

    private fun setOnCellClickListener(
        cell: TextView,
        rowIndex: Int,
        colIndex: Int,
    ) {
        cell.setOnClickListener {
            presenter.selectSeat(rowIndex, colIndex)
            presenter.setSeatsCellsBackgroundColorInfo()
            presenter.setConfirmButtonClickListener()
        }
    }

    override fun setSeatsText(info: Seat) {
        val row = binding.seatsTable.getChildAt(info.rowIndex) as TableRow
        val cell: TextView = row.getChildAt(info.colIndex) as TextView
        cell.text = info.coordinate
    }

    override fun setMovieTitle(info: String) {
        binding.seatsMovieTitle.text = info
    }

    override fun setTotalPrice(info: Int) {
        binding.seatsTotalPrice.text = TOTAL_PRICE.format(info)
    }

    override fun setOnConfirmButtonClickListener() {
        val confirmButton = binding.confirmButton
        confirmButton.setBackgroundColor(getColor(R.color.purple_500))
        confirmButton.setOnClickListener {
            showDialog()
        }
    }

    override fun setOffConfirmButtonClickListener() {
        val confirmButton = binding.confirmButton
        confirmButton.setBackgroundColor(getColor(R.color.gray))
        confirmButton.isClickable = false
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setMessage(MESSAGE_DIALOG)
            .setNegativeButton(TEXT_CANCEL) { _, _ ->
                // nothing
            }.setPositiveButton(TEXT_CONFIRM) { _, _ ->
                presenter.startNextActivity()
            }.setCancelable(false).show()
    }

    override fun startNextActivity(
        id: Long,
        title: String,
        date: String,
        time: String,
        seats: List<Seat>,
        price: Int,
    ) {
        val intent = Intent(this, MovieTicketActivity::class.java)
        intent.putExtra(ID_KEY, id)
        intent.putExtra(TITLE_KEY, title)
        intent.putExtra(DATE_KEY, date)
        intent.putExtra(TIME_KEY, time)
        intent.putExtra(SEATS_KEY, seats as Serializable)
        intent.putExtra(PRICE_KEY, price)
        intent.putExtra(MovieInformationDetailActivity.EXTRA_THEATER_ID_KEY, theaterId)
        startActivity(intent)
    }

    override fun setSeatCellBackgroundColor(info: Seat) {
        val row = binding.seatsTable.getChildAt(info.rowIndex) as TableRow
        val cell = row.getChildAt(info.colIndex)
        cell.setBackgroundColor(info.cellBackgroundColor)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            presenter.clearSelectedSeats()
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        const val TOTAL_PRICE = "%d원"
        const val ID_KEY = "id_key"
        const val TITLE_KEY = "title_key"
        const val DATE_KEY = "date_key"
        const val TIME_KEY = "time_key"
        const val SEATS_KEY = "seats_key"
        const val PRICE_KEY = "price_key"
        private const val MESSAGE_DIALOG = "정말 예매하시겠습니까?"
        private const val TEXT_CANCEL = "취소"
        private const val TEXT_CONFIRM = "예매 완료"
    }
}
