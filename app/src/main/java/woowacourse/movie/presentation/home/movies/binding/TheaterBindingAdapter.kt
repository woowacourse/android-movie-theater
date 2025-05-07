package woowacourse.movie.presentation.home.movies.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.common.model.TheatersUiModel
import woowacourse.movie.presentation.common.model.toTheaterUiModels
import woowacourse.movie.presentation.home.movies.dialog.TheatersAdapter

@BindingAdapter("setItems")
fun setItems(
    rv: RecyclerView,
    theaters: TheatersUiModel?,
) {
    if (theaters == null) return

    val adapter = rv.adapter
    if (adapter is TheatersAdapter) {
        adapter.submitList(theaters.toTheaterUiModels())
    }
}
