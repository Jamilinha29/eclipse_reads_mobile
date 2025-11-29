@Entity(tableName = "items")
data class MyEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val updatedAt: String
)

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MyEntity>)

    @Query("SELECT * FROM items ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, MyEntity>

    @Query("DELETE FROM items")
    suspend fun clearAll()
}
