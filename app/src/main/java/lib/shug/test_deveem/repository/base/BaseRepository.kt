package lib.shug.test_deveem.repository.base

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import lib.shug.test_deveem.model.utils.Resource

abstract class BaseRepository {

    protected fun <T> doRequest(request: suspend () -> T) = flow {
        emit(Resource.Loading())
        try {
            val result = request()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "unknown error"))
        }
    }.catch { e ->
        emit(Resource.Error(e.localizedMessage ?: "unknown error"))
    }
}