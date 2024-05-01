package woowacourse.movie.screeningmovie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemScreeningAdvertiseBinding
import woowacourse.movie.databinding.ItemScreeningMovieBinding

sealed class ScreeningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MovieViewHolder(
    private val binding: ItemScreeningMovieBinding,
    private val onClickReservationButton: (id: Long) -> Unit,
) : ScreeningViewHolder(binding.root) {

    fun onBind(item: ScreenMovieUiModel) {
        binding.movie = item
        binding.btnMovieReservation.setOnClickListener {
            onClickReservationButton(item.id)
        }
    }
}

class AdvertiseViewHolder(binding: ItemScreeningAdvertiseBinding) : ScreeningViewHolder(binding.root)
