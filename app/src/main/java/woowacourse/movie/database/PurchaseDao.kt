package woowacourse.movie.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PurchaseDao {
    @Insert
    suspend fun insertPurchase(purchase: Purchase)

    @Query("SELECT * FROM Purchase")
    suspend fun getAllPurchases(): List<Purchase>
}
