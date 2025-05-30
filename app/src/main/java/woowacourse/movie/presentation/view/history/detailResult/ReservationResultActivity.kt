package woowacourse.movie.presentation.view.history.detailResult

import android.os.Bundle
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.model.TicketBundleUiModel
import woowacourse.movie.presentation.view.home.reservation.result.ReservationResultFragment

class ReservationResultActivity :
    BaseActivity<ActivityReservationResultBinding>(R.layout.activity_reservation_result) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val ticket = intent.getParcelableExtra<TicketBundleUiModel>("ticket")
            if (ticket != null) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(
                        R.id.fragment_container_view,
                        ReservationResultFragment.newInstance(ticket),
                    )
                }
            }
        }
    }
}