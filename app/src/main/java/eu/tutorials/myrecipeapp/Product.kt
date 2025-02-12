package eu.tutorials.myrecipeapp


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,
    var isInCart: Boolean = false,
    var quantity: Int = 1
): Parcelable

@Parcelize
data class Rating(
    val rate: Double,
    val count: Int
): Parcelable

data class ProductsResponse(val products: List<Product>)