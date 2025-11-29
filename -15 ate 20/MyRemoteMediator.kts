import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.MediatorResult
import androidx.room.withTransaction

@OptIn(ExperimentalPagingApi::class)
class MyRemoteMediator(
    private val api: MyApi,
    private val db: AppDatabase
) : RemoteMediator<Int, MyEntity>() {

    private val myDao = db.myDao()
    private val remoteKeysDao = db.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: androidx.paging.PagingState<Int, MyEntity>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // pega última key
                    val lastKey = remoteKeysDao.remoteKeysId("last")
                    lastKey?.nextPage ?: 1
                }
            }

            val response = api.getItems(page = page, pageSize = state.config.pageSize)
            val items = response.items.map { dto -> MyEntity(dto.id, dto.title, dto.updatedAt) }
            val endOfList = response.items.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    myDao.clearAll()
                    remoteKeysDao.clearRemoteKeys()
                }
                myDao.insertAll(items)
                remoteKeysDao.insertAll(listOf(RemoteKeys("last", if (endOfList) null else page + 1)))
            }
            MediatorResult.Success(endOfPaginationReached = endOfList)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
