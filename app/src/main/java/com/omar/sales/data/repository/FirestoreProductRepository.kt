package com.omar.sales.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.omar.sales.domain.model.Product
import com.omar.sales.domain.repository.ProductRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreProductRepository : ProductRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("products")

    override suspend fun saveProduct(product: Product) {
        collection.document(product.code).set(product).await()
    }

    override suspend fun deleteProduct(productCode: String) {
        collection.document(productCode).delete().await()
    }

    override suspend fun findProductByCode(productCode: String): Product? {
        val snapshot = collection.document(productCode).get().await()
        return if (snapshot.exists()) {
            snapshot.toObject(Product::class.java)
        } else {
            null
        }
    }

    override fun getProducts(): Flow<List<Product>> = callbackFlow {
        val listenerRegistration = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val products = snapshot?.documents?.mapNotNull { document ->
                document.toObject(Product::class.java)
            } ?: emptyList()

            trySend(products).isSuccess
        }

        awaitClose {
            listenerRegistration.remove()
        }
    }
}