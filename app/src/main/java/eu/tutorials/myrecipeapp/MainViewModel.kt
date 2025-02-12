package eu.tutorials.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _productsState = mutableStateOf(ProductState())
    val productsState: State<ProductState> = _productsState

    private val _cartVisible = mutableStateOf(false)
    val cartVisible: State<Boolean> = _cartVisible

    fun toggleCart() {
        _cartVisible.value = !_cartVisible.value
    }

    fun addToCart(product: Product) {
        val currentList = _productsState.value.list.toMutableList()
        val index = currentList.indexOfFirst { it.id == product.id }
        if (index != -1) {
            currentList[index] = currentList[index].copy(isInCart = true)
            _productsState.value = _productsState.value.copy(list = currentList)
        }
    }

    fun removeFromCart(product: Product) {
        val currentList = _productsState.value.list.toMutableList()
        val index = currentList.indexOfFirst { it.id == product.id }
        if (index != -1) {
            currentList[index] = currentList[index].copy(isInCart = false)
            _productsState.value = _productsState.value.copy(list = currentList)
        }
    }
    fun incrementQuantity(product: Product) {
        val currentList = _productsState.value.list.toMutableList()
        val index = currentList.indexOfFirst { it.id == product.id }
        if (index != -1) {
            currentList[index] = currentList[index].copy(quantity = currentList[index].quantity + 1)
            _productsState.value = _productsState.value.copy(list = currentList)
        }
    }

    fun decrementQuantity(product: Product) {
        val currentList = _productsState.value.list.toMutableList()
        val index = currentList.indexOfFirst { it.id == product.id }
        if (index != -1 && currentList[index].quantity > 1) {
            currentList[index] = currentList[index].copy(quantity = currentList[index].quantity - 1)
            _productsState.value = _productsState.value.copy(list = currentList)
        } else if (index != -1 && currentList[index].quantity == 1) {
            // Remove from cart if quantity becomes 0
            removeFromCart(product)
        }
    }
    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = productService.getProducts()
                _productsState.value = _productsState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _productsState.value = _productsState.value.copy(
                    loading = false,
                    error = "Error fetching products ${e.message}"
                )
            }
        }
    }

    data class ProductState(
        val loading: Boolean = true,
        val list: List<Product> = emptyList(),
        val error: String? = null
    )
}