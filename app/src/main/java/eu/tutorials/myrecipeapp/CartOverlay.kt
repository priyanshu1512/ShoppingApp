package eu.tutorials.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CartOverlay(
    products: List<Product>,
    onRemoveFromCart: (Product) -> Unit,
    onIncrementQuantity: (Product) -> Unit,
    onDecrementQuantity: (Product) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Shopping Cart",
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close Cart")
            }
        }

        LazyColumn {
            items(products.filter { it.isInCart }) { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(product.image),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )

                    Text(
                        text = product.title,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )

                    // Quantity Controls
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(
                            onClick = { onDecrementQuantity(product) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Text("-", style = MaterialTheme.typography.titleMedium)
                        }

                        Text(
                            text = product.quantity.toString(),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(
                            onClick = { onIncrementQuantity(product) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Text("+", style = MaterialTheme.typography.titleMedium)
                        }
                    }

                    Text(
                        text = "$${String.format("%.2f", product.price * product.quantity)}",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(onClick = { onRemoveFromCart(product) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove from cart")
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }

        // Total calculation including quantities
        val total = products
            .filter { it.isInCart }
            .sumOf { it.price * it.quantity }

        Text(
            text = "Total: $${String.format("%.2f", total)}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        )
    }
}