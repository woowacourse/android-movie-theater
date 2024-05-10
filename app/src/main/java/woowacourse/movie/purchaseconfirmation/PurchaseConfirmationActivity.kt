package woowacourse.movie.purchaseconfirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.MovieApplication
import woowacourse.movie.data.DummyEverythingRepository
import woowacourse.movie.data.reservationref.ReservationRefRepositoryImpl
import woowacourse.movie.data.screeningref.ScreeningRefRepositoryImpl
import woowacourse.movie.data.theater.TheaterRepositoryImpl
import woowacourse.movie.databinding.ActivityPuchaseConfirmationBinding
import woowacourse.movie.purchaseconfirmation.uimodel.PurchaseConfirmationUiModel
import woowacourse.movie.usecase.FetchReservationWithIdUseCase
import woowacourse.movie.usecase.FetchScreeningWithIdUseCase

class PurchaseConfirmationActivity : AppCompatActivity(), PurchaseConfirmationContract.View {
    private lateinit var binding: ActivityPuchaseConfirmationBinding
    private lateinit var presenter: PurchaseConfirmationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPuchaseConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservationId = intent.getLongExtra(EXTRA_RESERVATION_ID, INVALID_RESERVATION_ID)
        val db = (application as MovieApplication).db
        val fetchScreeningWithIdUseCase =
            FetchScreeningWithIdUseCase(
                DummyEverythingRepository,
                TheaterRepositoryImpl(db.theaterDao()),
                ScreeningRefRepositoryImpl(db.screeningDao()),
            )

        val fetchReservationWithIdUseCase =
            FetchReservationWithIdUseCase(
                fetchScreeningWithIdUseCase,
                ReservationRefRepositoryImpl(db.reservationDao()),
            )
        presenter =
            PurchaseConfirmationPresenter(
                view = this,
                fetchReservationWithIdUseCase,
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

    override fun showResult(reservationResult: PurchaseConfirmationUiModel) {
        binding.reservation = reservationResult
    }

    companion object {
        const val EXTRA_RESERVATION_ID: String = "reservationId"
        const val INVALID_RESERVATION_ID: Long = -1

        fun getIntent(
            context: Context,
            reservationId: Long,
        ): Intent {
            return Intent(context, PurchaseConfirmationActivity::class.java).apply {
                putExtra(EXTRA_RESERVATION_ID, reservationId)
            }
        }
    }
}
