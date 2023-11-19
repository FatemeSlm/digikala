package com.example.digikala.ui.screens.cart

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digikala.data.model.cart.CartStatus
import com.example.digikala.ui.component.Loading
import com.example.digikala.viewmodel.CartViewModel

@Composable
fun ShoppingCart(
    viewModel: CartViewModel = hiltViewModel()
) {

    val cartDetail = viewModel.cartDetail.collectAsState()

    val currentCartResult by viewModel.currentCartItems.collectAsState()
    when (currentCartResult) {
        is CartState.Success -> {
            val currentCardList = currentCartResult.data ?: emptyList()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 60.dp)
            ) {
                if (currentCardList.isEmpty()) {
                    item { EmptyShoppingCart() }
                    item { SuggestedListSection() }
                } else {
                    items(currentCardList) {
                        CartItemCard(item = it, cartStatus = CartStatus.CURRENT_CART)
                    }
                    item {
                        CartPriceDetail(cartDetail.value)
                    }
                }
            }
        }

        is CartState.Error -> {
            Log.e("3636", "ShoppingCart ${currentCartResult.error}")
        }

        is CartState.Loading -> {
            Loading(height = LocalConfiguration.current.screenHeightDp.dp - 60.dp, isDark = true)
        }
    }
}