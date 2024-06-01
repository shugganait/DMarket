package lib.shug.test_deveem.repository.use_cases.local.cart

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lib.shug.test_deveem.repository.local.cart.LocalCartRepository
import javax.inject.Inject

class DeleteAllCartDBUseCase @Inject constructor(private val repository: LocalCartRepository) {

    suspend fun invoke() = withContext(Dispatchers.IO) {repository.deleteAll()}

}