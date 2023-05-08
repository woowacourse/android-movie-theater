package woowacourse.movie.presentation.views.main.fragments.home.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener

class OnEndScrollListener(private val onScrollEnd: () -> Unit) : OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(DOWN_DIRECTION) && dy > 0) {
            onScrollEnd()
        }
    }

    companion object {
        private const val DOWN_DIRECTION = 1
    }
}
