package com.omar.sales.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omar.sales.data.repository.FirestoreCustomerRepository
import com.omar.sales.data.repository.FirestoreProductRepository
import com.omar.sales.domain.usecase.customer.CreateCustomerUseCase
import com.omar.sales.domain.usecase.customer.DeleteCustomerUseCase
import com.omar.sales.domain.usecase.customer.ListCustomersUseCase
import com.omar.sales.domain.usecase.product.CreateProductUseCase
import com.omar.sales.domain.usecase.product.DeleteProductUseCase
import com.omar.sales.domain.usecase.product.ListProductsUseCase
import com.omar.sales.presentation.customer.create.CreateCustomerScreen
import com.omar.sales.presentation.customer.create.CreateCustomerViewModel
import com.omar.sales.presentation.customer.list.ListCustomerScreen
import com.omar.sales.presentation.customer.list.ListCustomerViewModel
import com.omar.sales.presentation.product.create.CreateProductScreen
import com.omar.sales.presentation.product.create.CreateProductViewModel
import com.omar.sales.presentation.product.list.ListProductScreen
import com.omar.sales.presentation.product.list.ListProductViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val productRepository = remember { FirestoreProductRepository() }
    val customerRepository = remember { FirestoreCustomerRepository() }

    val createProductUseCase = remember { CreateProductUseCase(productRepository) }
    val listProductsUseCase = remember { ListProductsUseCase(productRepository) }
    val deleteProductUseCase = remember { DeleteProductUseCase(productRepository) }

    val createCustomerUseCase = remember { CreateCustomerUseCase(customerRepository) }
    val listCustomersUseCase = remember { ListCustomersUseCase(customerRepository) }
    val deleteCustomerUseCase = remember { DeleteCustomerUseCase(customerRepository) }

    val createProductViewModel = remember { CreateProductViewModel(createProductUseCase) }
    val listProductViewModel = remember {
        ListProductViewModel(
            getProductsUseCase = listProductsUseCase,
            deleteProductUseCase = deleteProductUseCase
        )
    }

    val createCustomerViewModel = remember { CreateCustomerViewModel(createCustomerUseCase) }
    val listCustomerViewModel = remember {
        ListCustomerViewModel(
            getCustomersUseCase = listCustomersUseCase,
            deleteCustomerUseCase = deleteCustomerUseCase
        )
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onGoToProducts = { navController.navigate("product_list") },
                onGoToCustomers = { navController.navigate("customer_list") }
            )
        }

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

        composable("customer_list") {
            ListCustomerScreen(
                viewModel = listCustomerViewModel,
                onNavigateToCreate = { navController.navigate("create_customer") }
            )
        }

        composable("create_customer") {
            CreateCustomerScreen(
                viewModel = createCustomerViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}