package com.example.mobileclient.model

class Sample {
    class Store {
        var store_name: String? = null
        var store_price: String? = null
        var product_url: String? = null
        var currency_code: String? = null
        var currency_symbol: String? = null
    }

    class Review {
        var name: String? = null
        var rating: String? = null
        var title: String? = null
        var review: String? = null
        var datetime: String? = null
    }

    class Product {
        var barcode_number: String? = null
        var barcode_type: String? = null
        var barcode_formats: String? = null
        var mpn: String? = null
        var model: String? = null
        var asin: String? = null
        var product_name: String? = null
        var title: String? = null
        var category: String? = null
        var manufacturer: String? = null
        var brand: String? = null
        var label: String? = null
        var author: String? = null
        var publisher: String? = null
        var artist: String? = null
        var actor: String? = null
        var director: String? = null
        var studio: String? = null
        var genre: String? = null
        var audience_rating: String? = null
        var ingredients: String? = null
        var nutrition_facts: String? = null
        var color: String? = null
        var format: String? = null
        var package_quantity: String? = null
        var size: String? = null
        var length: String? = null
        var width: String? = null
        var height: String? = null
        var weight: String? = null
        var release_date: String? = null
        var description: String? = null
        lateinit var features: Array<Any>
        lateinit var images: Array<String>
        lateinit var stores: Array<Store>
        lateinit var reviews: Array<Review>
    }
}
