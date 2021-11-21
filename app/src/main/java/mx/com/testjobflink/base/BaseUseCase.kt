package mx.com.testjobflink.base

interface BaseUseCase {
    suspend fun updateFavoriteState(id: Long, isFavorite: Boolean)
}