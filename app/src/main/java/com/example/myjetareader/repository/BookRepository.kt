package com.example.myjetareader.repository

import com.example.myjetareader.data.DataOrException
import com.example.myjetareader.model.Item
import com.example.myjetareader.network.BooksApi
import java.lang.Exception
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val api: BooksApi
) {
    private val dataOfException = DataOrException<List<Item>, Boolean, Exception>()
    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()

    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        try {
            dataOfException.loading = true
            dataOfException.data = api.getAllBooks(searchQuery).items
            if (dataOfException.data!!.isNotEmpty()) dataOfException.loading = false
        } catch (e: Exception) {
            dataOfException.e = e
        }
        return dataOfException
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        val response = try {
            bookInfoDataOrException.loading = true
            bookInfoDataOrException.data = api.getBookInfo(bookId = bookId)
            if (bookInfoDataOrException.data.toString().isNotEmpty()) bookInfoDataOrException.loading = false
            else{}
            
        } catch (e: Exception) {
            bookInfoDataOrException.e = e
        }
    }
}