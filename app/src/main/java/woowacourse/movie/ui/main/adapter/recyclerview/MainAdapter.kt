package woowacourse.movie.ui.main.adapter.recyclerview

import android.content.Intent
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.model.main.MainData
import woowacourse.movie.ui.main.adapter.MainViewType

class MainAdapter(
    private val onBooked: (Long) -> Unit,
    private val onAdClicked: (Intent) -> Unit,
) : RecyclerView.Adapter<MainViewHolder<ViewDataBinding>>() {

    private val movies = mutableListOf<MainData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder<ViewDataBinding> {
        val mainViewType = MainViewType.getMainViewType(viewType)
        val viewHolder = when (mainViewType) {
            MainViewType.CONTENT -> MovieViewHolder.from(parent)
            MainViewType.ADVERTISEMENT -> AdvertisementViewHolder.from(parent)
        }
        setViewHolderClick(viewHolder)

        return viewHolder
    }

    override fun getItemViewType(position: Int): Int =
        movies[position].mainViewType.ordinal

    private fun setViewHolderClick(viewHolder: MainViewHolder<ViewDataBinding>) {
        when (viewHolder) {
            is MovieViewHolder -> viewHolder.setBookingClick(onBooked)
            is AdvertisementViewHolder -> viewHolder.setAdvertisementClick(onAdClicked)
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder<ViewDataBinding>, position: Int) {
        holder.onBind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun initMovies(items: List<MainData>) {
        movies.clear()
        movies.addAll(items)
        notifyDataSetChanged()
    }
}
