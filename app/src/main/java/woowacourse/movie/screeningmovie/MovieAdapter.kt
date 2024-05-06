package woowacourse.movie.screeningmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemScreeningAdvertiseBinding
import woowacourse.movie.databinding.ItemScreeningMovieBinding

class MovieAdapter(
    private val adapterClickListener: AdapterClickListener,
) : RecyclerView.Adapter<ScreeningViewHolder>() {
    private var itemList = listOf<ScreeningItem>()

    enum class ScreenItemViewType(val value: Int) {
        SCREENING_MOVIE(0),
        ADVERTISE(1), ;

        companion object {
            fun valueOfType(value: Int): ScreenItemViewType = if (value == 0) SCREENING_MOVIE else ADVERTISE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is ScreenMovieUiModel -> ScreenItemViewType.SCREENING_MOVIE.value
            is AdvertiseUiModel -> ScreenItemViewType.ADVERTISE.value
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ScreeningViewHolder {
        return when (ScreenItemViewType.valueOfType(viewType)) {
            ScreenItemViewType.SCREENING_MOVIE -> {
                val binding =
                    ItemScreeningMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                MovieViewHolder(binding, adapterClickListener)
            }

            ScreenItemViewType.ADVERTISE -> {
                val binding =
                    ItemScreeningAdvertiseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                AdvertiseViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ScreeningViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MovieViewHolder -> holder.onBind(itemList[position] as ScreenMovieUiModel)
            is AdvertiseViewHolder -> {}
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun submitList(newItemList: List<ScreeningItem>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    companion object {
        private const val MOVIE = 0
        private const val ADVERTISE = 1
    }
}
