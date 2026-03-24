package com.omar.sales.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.omar.sales.domain.model.Customer
import com.omar.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreCustomerRepository : CustomerRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("customers")

    override suspend fun saveCustomer(customer: Customer) {
        collection.document(customer.code).set(customer).await()
    }

    override suspend fun deleteCustomer(customerCode: String) {
        collection.document(customerCode).delete().await()
    }

    override suspend fun findCustomerByCode(customerCode: String): Customer? {
        val snapshot = collection.document(customerCode).get().await()
        return if (snapshot.exists()) {
            snapshot.toObject(Customer::class.java)
        } else {
            null
        }
    }

    override fun getCustomers(): Flow<List<Customer>> = callbackFlow {
        val listenerRegistration = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val customers = snapshot?.documents?.mapNotNull { document ->
                document.toObject(Customer::class.java)
            } ?: emptyList()

            trySend(customers).isSuccess
        }

        awaitClose {
            listenerRegistration.remove()
        }
    }
}