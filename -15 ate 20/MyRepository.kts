import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class MyRepository(private val api: MyApi, private val db: AppDatabase) {
    fun getItemsPaged(): Flow<PagingData<MyEntity>> {
        val pagingSourceFactory = { db.myDao().pagingSource() }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = MyRemoteMediator(api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
