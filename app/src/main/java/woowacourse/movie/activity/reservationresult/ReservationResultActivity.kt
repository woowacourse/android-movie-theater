package woowacourse.movie.activity.reservationresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.error.ActivityError.finishWithError
import woowacourse.movie.view.error.ViewError
import woowacourse.movie.view.getSerializable

class ReservationResultActivity : AppCompatActivity(), ReservationResultContract.View {

    lateinit var presenter: ReservationResultContract.Presenter
    private val binding: ActivityReservationResultBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ReservationResultPresenter(this)
        initReservationResultView()
    }

    private fun initReservationResultView() {
        makeBackButton()

        val reservation: ReservationViewData = getIntentReservationData() ?: return finishWithError(
            ViewError.MissingExtras(ReservationViewData.RESERVATION_EXTRA_NAME),
        )
        presenter.setUpReservation(reservation)
    }

    private fun getIntentReservationData(): ReservationViewData? =
        intent.extras?.getSerializable<ReservationViewData>(ReservationViewData.RESERVATION_EXTRA_NAME)

    private fun makeBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setUpReservation(reservationViewData: ReservationViewData) {
        binding.reservation = reservationViewData
    }

    companion object {
        fun from(
            context: Context,
            reservation: ReservationViewData,
        ): Intent {
            return Intent(context, ReservationResultActivity::class.java).apply {
                putExtra(ReservationViewData.RESERVATION_EXTRA_NAME, reservation)
            }
        }
    }
}
