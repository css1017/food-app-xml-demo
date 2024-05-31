package com.css101.data.storage.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductsData(
    val id: Int,
    @JsonProperty("category_id")
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    @JsonProperty("price_current")
    val priceCurrent: Double,
    @JsonProperty("price_old")
    val priceOld: Double?,
    val measure: Double,
    @JsonProperty("measure_unit")
    val measureUnit: String,
    @JsonProperty("energy_per_100_grams")
    val energyPer100Grams: Double,
    @JsonProperty("proteins_per_100_grams")
    val proteinsPer100Grams: Double,
    @JsonProperty("fats_per_100_grams")
    val fatsPer100Grams: Double,
    @JsonProperty("carbohydrates_per_100_grams")
    val carbohydratesPer100Grams: Double,
    @JsonProperty("tag_ids")
    val tagIds: List<Int?>
)