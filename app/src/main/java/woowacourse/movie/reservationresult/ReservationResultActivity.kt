package woowacourse.movie.reservationresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.data.DummyMovieRepository
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.reservationresult.uimodel.ReservationResultUiModel

class ReservationResultActivity : AppCompatActivity(), ReservationResultContract.View {
    private lateinit var binding: ActivityReservationResultBinding
    private lateinit var presenter: ReservationResultPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservationId = intent.getLongExtra(EXTRA_RESERVATION_ID, INVALID_RESERVATION_ID)

        presenter =
            ReservationResultPresenter(
                repository = DummyMovieRepository,
                view = this,
            )
        presenter.loadReservationResult(reservationId)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showResult(reservationResult: ReservationResultUiModel) {
        binding.reservation = reservationResult
    }

    companion object {
        const val EXTRA_RESERVATION_ID: String = "reservationId"
        const val INVALID_RESERVATION_ID: Long = -1

        fun getIntent(
            context: Context,
            reservationId: Long,
        ): Intent {
            return Intent(context, ReservationResultActivity::class.java).apply {
                putExtra(EXTRA_RESERVATION_ID, reservationId)
            }
        }
    }
}
