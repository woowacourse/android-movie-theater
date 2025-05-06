package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.Screening

@Parcelize
data class ScreeningUiModel(
    val movie: MovieUiModel,
    val times: List<Int>,
) : Parcelable

fun Screening.toPresentation(): ScreeningUiModel = ScreeningUiModel(movie.toPresentation(), times)

fun ScreeningUiModel.toDomain(): Screening = Screening(movie.toDomain(), times)
