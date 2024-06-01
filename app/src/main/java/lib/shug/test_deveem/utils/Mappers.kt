package lib.shug.test_deveem.utils

import lib.shug.test_deveem.model.local.entities.ProductEntity
import lib.shug.test_deveem.model.remote.api_models.ProductsItemModel

fun ProductsItemModel.toProductEntity(): ProductEntity {
    return ProductEntity(
        category,
        description,
        id,
        image,
        price,
        rating.rate,
        rating.count,
        title
    )
}