package woowacourse.movie.ui.complete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieReservationCompleteBinding
import woowacourse.movie.model.data.UserTicketsImpl
import woowacourse.movie.model.movie.Seat
import woowacourse.movie.model.movie.UserTicket
import woowacourse.movie.ui.base.BaseActivity
import woowacourse.movie.ui.main.MovieMainActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MovieReservationCompleteActivity :
    BaseActivity<MovieReservationCompletePresenter>(),
    MovieReservationCompleteContract.View {
    private lateinit var binding: ActivityMovieReservationCompleteBinding
    private val userTicketId by lazy { userTicketId() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_reservation_complete)

        if (userTicketId == USER_TICKET_ID_DEFAULT_VALUE) {
            presenter.handleError(NoSuchElementException())
            return
        }

        initializeOnBackPressedCallback()

        presenter.loadTicket(userTicketId)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initializePresenter() = MovieReservationCompletePresenter(this, UserTicketsImpl)

    private fun userTicketId() = intent.getLongExtra(MovieReservationCompleteKey.TICKET_ID, USER_TICKET_ID_DEFAULT_VALUE)

    override fun showTicket(userTicket: UserTicket) {
        binding.userTicket = userTicket
    }

    override fun showError(throwable: Throwable) {
        Log.e(TAG, throwable.message.toString())
        Toast.makeText(this, resources.getString(R.string.invalid_key), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> navigateBackToMainScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeOnBackPressedCallback() {
        val onBackPressedCallBack =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() = navigateBackToMainScreen()
            }
        onBackPressedDispatcher.addCallback(onBackPressedCallBack)
    }

    private fun navigateBackToMainScreen() {
        Intent(this, MovieMainActivity::class.java).also {
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(it)
        }
    }

    companion object {
        private val TAG = MovieReservationCompleteActivity::class.simpleName
        private const val USER_TICKET_ID_DEFAULT_VALUE = -1L
    }
}

@BindingAdapter("context", "count", "seats", "theaterName")
fun setReservationResult(
    textView: TextView,
    context: Context,
    count: Int,
    seats: List<Seat>,
    theaterName: String,
) {
    textView.text =
        context.getString(R.string.reservation_result, count, seats.joinToString(), theaterName)
}

@BindingAdapter("context", "dateTime")
fun setReservedDateTime(
    textView: TextView,
    context: Context,
    dateTime: LocalDateTime,
) {
    textView.text =
        dateTime.format(DateTimeFormatter.ofPattern(context.getString(R.string.reservation_screening_date_time_format)))
}
