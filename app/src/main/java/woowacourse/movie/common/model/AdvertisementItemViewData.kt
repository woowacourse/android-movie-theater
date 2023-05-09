package woowacourse.movie.common.model

import java.io.Serializable

class AdvertisementItemViewData(
    val banner: ImageViewData,
    override val viewType: MovieListViewType = MovieListViewType.ADVERTISEMENT
) : Serializable, MovieListItemViewData
