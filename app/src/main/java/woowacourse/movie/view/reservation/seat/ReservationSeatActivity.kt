package woowacourse.movie.view.reservation.seat

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View.OnClickListener
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.data.mapper.toDomain
import woowacourse.movie.databinding.ActivityReservationSeatBinding
import woowacourse.movie.domain.movieseat.Position
import woowacourse.movie.view.dialog.DialogFactory
import woowacourse.movie.view.dialog.DialogInfo
import woowacourse.movie.view.reservation.TicketUi
import woowacourse.movie.view.reservation.result.ReservationCompleteActivity
import woowacourse.movie.view.setting.alarm.AlarmHelper

class ReservationSeatActivity : AppCompatActivity(), ReservationSeatContract.View {
    private val presenter: ReservationSeatContract.Present by lazy {
        ReservationSeatPresenter.provideFactory(this)
    }
    private var _binding: ActivityReservationSeatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReservationSeatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ticketUi =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(KEY_TICKET, TicketUi::class.java)
            } else {
                intent.getSerializableExtra(KEY_TICKET) as? TicketUi
            }
        checkTicket(ticketUi)
        initialize()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun checkTicket(ticketUi: TicketUi?) {
        if (ticketUi == null) {
            handleInvalidTicket()
        } else {
            presenter.fetchData(ticketUi.toDomain())
        }
    }

    private fun initialize() {
        setSeatTag()
        setSeatInit()
        setSeatClickListener()
        setReservationButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.onRestoreState(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAllSeatTextViews(): Sequence<TextView> {
        return binding.tvSeat
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>()
    }

    private fun setSeatTag() {
        getAllSeatTextViews().forEachIndexed { index, textView ->
            val row = index / 4
            val column = index % 4
            textView.tag = Position(row, column)
        }
    }

    private fun setSeatInit() {
        getAllSeatTextViews().forEach { textView ->
            val position = textView.tag as Position
            textView.text = getSeatName(position)
            setSeatColor(textView, position)
        }
    }

    private fun setSeatClickListener() {
        getAllSeatTextViews().forEachIndexed { _, textView ->
            textView.setOnClickListener {
                toggleSeatSelection(textView)
            }
        }
    }

    override fun showMovieName(movieName: String) {
        binding.movieName = movieName
    }

    override fun showTicketMoney(seatsPrice: Int) {
        binding.seatsPrice = seatsPrice
    }

    private fun setReservationButton() {
        binding.onConfirm = OnClickListener { showReservationDialog() }
    }

    private fun toggleSeatSelection(textView: TextView) {
        val position = textView.tag as Position
        if (textView.isSelected) {
            presenter.deselectSeat(position)
        } else {
            presenter.selectSeat(position)
        }
    }

    override fun selectSeatView(position: Position) {
        val textView = findTextViewByPosition(position)
        textView.setBackgroundColor(Color.YELLOW)
        textView.isSelected = true
    }

    override fun deselectSeatView(position: Position) {
        val textView = findTextViewByPosition(position)
        textView.setBackgroundColor(Color.WHITE)
        textView.isSelected = false
    }

    override fun setButton(isSelectable: Boolean) {
        binding.isSelectable = isSelectable
    }

    private fun showReservationDialog() {
        DialogFactory().show(
            DialogInfo(
                this,
                R.string.reserve_confirm,
                R.string.askFor_reserve,
                R.string.complete,
                R.string.cancel,
            ),
        ) {
            presenter.createTicket()
            finish()
        }
    }

    override fun handleReservationComplete(ticketUi: TicketUi) {
        val intent =
            ReservationCompleteActivity.newIntent(this@ReservationSeatActivity, ticketUi)
        AlarmHelper.setAlarm(this, ticketUi)
        startActivity(intent)
    }

    private fun findTextViewByPosition(position: Position): TextView {
        return getAllSeatTextViews().first { it.tag == position }
    }

    private fun getSeatName(position: Position): String {
        val columnChar = 'A' + position.row
        return "$columnChar${position.column + 1}"
    }

    private fun setSeatColor(
        textView: TextView,
        position: Position,
    ) {
        val color =
            when (position.row) {
                0, 1 -> Color.MAGENTA
                2, 3 -> Color.BLUE
                else -> Color.GREEN
            }
        textView.setTextColor(color)
    }

    override fun handleInvalidTicket() {
        DialogFactory().showError(this) {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val KEY_TICKET = "ticket"

        fun newIntent(
            context: Context,
            ticketUi: TicketUi?,
        ): Intent =
            Intent(context, ReservationSeatActivity::class.java).putExtra(
                KEY_TICKET,
                ticketUi,
            )
    }
}
