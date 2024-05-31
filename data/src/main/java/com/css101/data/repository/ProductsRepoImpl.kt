package com.css101.data.repository

import android.util.Log
import com.css101.data.storage.models.CategoriesData
import com.css101.data.storage.models.ProductsData
import com.css101.domain.models.Categories
import com.css101.domain.models.Products
import com.css101.domain.repository.ProductsRepo
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request

class ProductsRepoImpl : ProductsRepo {
    override fun getProductList(): List<Products>? {
        return try { //todo replace with retrofit
            val mapper = jacksonObjectMapper()
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            val client = OkHttpClient()
            val request =
                Request.Builder().url("https://anika1d.github.io/WorkTestServer/Products.json")
                    .build()
            val result = client.newCall(request).execute().body()?.string()
            dataListToProductsList(
                mapper.readValue(result, object : TypeReference<List<ProductsData>>() {})
            )

        } catch (e: Exception) {
            null
        }
    }

    override fun getCategoriesList(): List<Categories>? {
        return try { //todo replace with retrofit
            val mapper = jacksonObjectMapper()
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            val client = OkHttpClient()
            val request =
                Request.Builder().url("https://anika1d.github.io/WorkTestServer/Categories.json")
                    .build()
            val result = client.newCall(request).execute().body()?.string()
            Log.e("aaaa", dataListToCategoriesList(
                mapper.readValue(result, object : TypeReference<List<CategoriesData>>() {})
            ).toString()
            )

            dataListToCategoriesList(
                mapper.readValue(result, object : TypeReference<List<CategoriesData>>() {})
            )

        } catch (e: Exception) {
            Log.e("aaaa", e.toString())
            null
        }
    }

    private fun dataListToCategoriesList(data: List<CategoriesData>): List<Categories> {
        return data.map { categoriesData ->
            Categories(
                id = categoriesData.id, name = categoriesData.name
            )
        }
    }

    private fun dataListToProductsList(data: List<ProductsData>): List<Products> {
        return data.map { productsData ->
            Products(
                id = productsData.id,
                categoryId = productsData.categoryId,
                name = productsData.name,
                description = productsData.description,
                image = productsData.image,
                priceCurrent = productsData.priceCurrent,
                priceOld = productsData.priceOld,
                measure = productsData.measure,
                measureUnit = productsData.measureUnit,
                energyPer100Grams = productsData.energyPer100Grams,
                proteinsPer100Grams = productsData.proteinsPer100Grams,
                fatsPer100Grams = productsData.fatsPer100Grams,
                carbohydratesPer100Grams = productsData.carbohydratesPer100Grams,
                tagIds = productsData.tagIds
            )
        }
    }
}