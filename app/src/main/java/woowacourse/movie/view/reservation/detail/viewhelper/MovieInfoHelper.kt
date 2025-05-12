package woowacourse.movie.view.reservation.detail.viewhelper

import androidx.appcompat.content.res.AppCompatResources
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.view.reservation.detail.ReservationDetailActivity

class MovieInfoHelper(
    private val activity: ReservationDetailActivity,
    private val binding: ActivityReservationBinding,
) {
    fun showMovieInfo(
        posterResId: Int,
        title: String,
        startDate: String,
        endDate: String,
        runningTime: Int,
    ) {
        setupMovieReservationInfo(posterResId, title, startDate, endDate, runningTime)
        activity.presenter.initDateAdapter()
    }

    private fun setupMovieReservationInfo(
        posterResId: Int,
        title: String,
        startDate: String,
        endDate: String,
        runningTime: Int,
    ) {
        val poster = AppCompatResources.getDrawable(activity, posterResId)
        binding.ivReservationPoster.setImageDrawable(poster)
        binding.tvReservationTitle.text = title
        binding.tvReservationScreeningDate.text =
            activity.resources.getString(R.string.movie_screening_date, startDate, endDate)
        binding.tvReservationRunningTime.text =
            activity.getString(R.string.movie_running_time).format(runningTime)
    }
}
