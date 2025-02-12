package eu.tutorials.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductDetailScreen(product: Product,
                        onAddToCart: (Product) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = product.title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = rememberAsyncImagePainter(product.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp)
        )

        Button(
            onClick = { onAddToCart(product) },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(if (product.isInCart) "Remove from Cart" else "Add to Cart")
        }

        Text(
            text = "Price: $${product.price}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Rating: ${product.rating.rate} (${product.rating.count} reviews)",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Category: ${product.category}",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = product.description,
            textAlign = TextAlign.Justify
        )
    }
}