package eu.tutorials.myrecipeapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val idCategory:String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
    ):Parcelable

data class CategoriesResponse(val categories: List<Category>)
//Since we are passing objects from one screen to another we need to serialize and deserialize it using Parcelable plugin method.
//parcelize means to store a object in a string format to make it easy to navigate.
// deparcelize means to again retrive back the object from the string.