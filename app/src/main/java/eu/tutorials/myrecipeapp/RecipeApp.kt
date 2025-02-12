package eu.tutorials.myrecipeapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(navController: NavHostController) {
    val productViewModel: MainViewModel = viewModel()
    val viewstate by productViewModel.productsState
    val showCart by productViewModel.cartVisible

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Products")
                BadgedBox(
                    badge = {
                        val cartCount = viewstate.list.count { it.isInCart }
                        if (cartCount > 0) {
                            Badge { Text(cartCount.toString()) }
                        }
                    }
                ) {
                    IconButton(onClick = { productViewModel.toggleCart() }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            }

            NavHost(navController = navController, startDestination = Screen.RecipeScreen.route) {
                composable(route = Screen.RecipeScreen.route) {
                    ProductScreen(viewstate = viewstate, navigateToDetail = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("product", it)
                        navController.navigate(Screen.DetailScreen.route)
                    })
                }
                composable(route = Screen.DetailScreen.route) {
                    val product = navController.previousBackStackEntry?.savedStateHandle?.
                    get<Product>("product") ?: Product(
                        id = 0,
                        title = "",
                        price = 0.0,
                        description = "",
                        category = "",
                        image = "",
                        rating = Rating(0.0, 0)
                    )
                    ProductDetailScreen(
                        product = product,
                        onAddToCart = { productViewModel.addToCart(it) }
                    )
                }
            }
        }

        // Cart Overlay
        // In your RecipeApp composable
        if (showCart) {
            CartOverlay(
                products = viewstate.list,
                onRemoveFromCart = { productViewModel.removeFromCart(it) },
                onIncrementQuantity = { productViewModel.incrementQuantity(it) },
                onDecrementQuantity = { productViewModel.decrementQuantity(it) },
                onDismiss = { productViewModel.toggleCart() }
            )
        }
    }
}