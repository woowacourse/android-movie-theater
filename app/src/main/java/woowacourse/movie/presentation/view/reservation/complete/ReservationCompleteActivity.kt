package woowacourse.movie.presentation.view.reservation.complete

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.data.ReservationDatabase
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.presentation.Extras
import woowacourse.movie.presentation.ReservationUiFormatter
import woowacourse.movie.presentation.getParcelableExtraCompat
import woowacourse.movie.presentation.model.ReservationInfoUiModel

class ReservationCompleteActivity :
    AppCompatActivity(),
    ReservationCompleteContract.View {
    private val reservationDao by lazy {
        ReservationDatabase.getInstance(this).reservationDao()
    }
    private lateinit var binding: ActivityReservationCompleteBinding
    private val presenter: ReservationCompletePresenter by lazy {
        ReservationCompletePresenter(
            this,
            reservationDao,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_complete)
        ViewCompat.setOnApplyWindowInsetsListener(binding.reservationComplete) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val reservationInfoUiModel =
            intent?.getParcelableExtraCompat<ReservationInfoUiModel>(Extras.ReservationInfoData.RESERVATION_KEY)
        presenter.fetchData(reservationInfoUiModel)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showErrorDialog() {
    }

    override fun showReservationInfo(reservationInfoUiModel: ReservationInfoUiModel) {
        binding.reservationInfo = reservationInfoUiModel
        binding.totalPrice =
            ReservationUiFormatter.priceToUI(reservationInfoUiModel.seats.totalPrice)
    }
}
