package com.omar.sales.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omar.sales.data.repository.InMemoryProductRepository
import com.omar.sales.domain.usecase.product.CreateProductUseCase
import com.omar.sales.domain.usecase.product.DeleteProductUseCase
import com.omar.sales.domain.usecase.product.ListProductsUseCase
import com.omar.sales.presentation.product.create.CreateProductScreen
import com.omar.sales.presentation.product.create.CreateProductViewModel
import com.omar.sales.presentation.product.list.ListProductScreen
import com.omar.sales.presentation.product.list.ListProductViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val repository = remember { InMemoryProductRepository() }
    val createProductUseCase = remember { CreateProductUseCase(repository) }
    val listProductsUseCase = remember { ListProductsUseCase(repository) }
    val deleteProductUseCase = remember { DeleteProductUseCase(repository) }

    val createProductViewModel = remember { CreateProductViewModel(createProductUseCase) }
    val listProductViewModel = remember {
        ListProductViewModel(
            getProductsUseCase = listProductsUseCase,
            deleteProductUseCase = deleteProductUseCase
        )
    }

    NavHost(
        navController = navController,
        startDestination = "product_list"
    ) {
        composable("product_list") {
            ListProductScreen(
                viewModel = listProductViewModel,
                onNavigateToCreate = { navController.navigate("create_product") }
            )
        }

        composable("create_product") {
            CreateProductScreen(
                viewModel = createProductViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}