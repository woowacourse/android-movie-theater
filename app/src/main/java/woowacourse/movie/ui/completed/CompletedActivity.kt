package woowacourse.movie.ui.completed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityCompletedBinding
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.util.formatScreenDateTime
import woowacourse.movie.util.getParcelable

class CompletedActivity : AppCompatActivity(), CompletedContract.View {

    private lateinit var binding: ActivityCompletedBinding
    private val reservation: ReservationUiModel by lazy {
        intent.getParcelable(
            RESERVATION,
            ReservationUiModel::class.java
        ) ?: throw IllegalArgumentException(RECEIVING_RESERVATION_ERROR)
    }
    private val presenter: CompletedPresenter by lazy {
        CompletedPresenter(
            view = this,
            reservation = reservation
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.initReservation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView(
        movie: MovieUiModel,
        theaterName: String
    ) {
        with(binding) {
            textCompletedTitle.text = movie.title
            textCompletedScreeningDate.text =
                reservation.bookedDateTime.formatScreenDateTime()
            textCompletedTicketCount.text =
                getString(
                    R.string.ticket_count_seat_info,
                    reservation.count,
                    reservation.seatPosition,
                    theaterName
                )
            textCompletedPaymentAmount.text =
                getString(R.string.payment_amount, reservation.payment)
        }

        showBackButton()
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        private const val RESERVATION = "RESERVATION"
        private const val RECEIVING_RESERVATION_ERROR = "예약한 영화의 정보를 받아올 수 없습니다."

        fun getIntent(context: Context, reservation: ReservationUiModel): Intent {
            return Intent(context, CompletedActivity::class.java).apply {
                putExtra(RESERVATION, reservation)
            }
        }
    }
}
