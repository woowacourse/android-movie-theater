package woowacourse.movie.model.mapper

import android.net.Uri
import com.example.domain.model.Adv
import woowacourse.movie.model.AdvState

fun Adv.asPresentation(): AdvState {
    return AdvState(
        Uri.parse(imageUrl),
        advDescription
    )
}
