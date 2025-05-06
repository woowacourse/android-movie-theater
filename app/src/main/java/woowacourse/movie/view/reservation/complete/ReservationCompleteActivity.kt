package woowacourse.movie.view.reservation.complete

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.view.Extras
import woowacourse.movie.view.getParcelableExtraCompat
import woowacourse.movie.view.model.ReservationInfoUiModel

class ReservationCompleteActivity :
    AppCompatActivity(),
    ReservationCompleteContract.View {
    private lateinit var binding: ActivityReservationCompleteBinding
    private val presenter: ReservationCompletePresenter by lazy { ReservationCompletePresenter(this) }

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
    }
}
