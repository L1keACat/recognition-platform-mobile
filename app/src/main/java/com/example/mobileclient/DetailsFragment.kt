package com.example.mobileclient

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.model.ItemObject
import com.example.mobileclient.model.RootObject
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.net.URL


class DetailsFragment : Fragment() {

    val args: DetailsFragmentArgs by navArgs()

    private val serverUrl: String = "http://10.0.2.2:8080"

    private lateinit var detailsList: RecyclerView
    private lateinit var productImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsList = view.findViewById(R.id.details_list)
        productImage = view.findViewById(R.id.product_imageview)

        val data = args.codeData
        val list: MutableList<ItemObject> = mutableListOf()
        var item: ItemObject

        val decodedJson = JSONObject(data)
        val code = decodedJson.get("content") as String

        if (decodedJson.get("type_dimension") == "2D") {
            item = ItemObject("Decoded data", code)
            list.add(item)
            item = ItemObject("Type format", decodedJson.get("type_format") as String)
            list.add(item)
            item = ItemObject("Type dimension", decodedJson.get("type_dimension") as String)
            list.add(item)
        } else {
            val (_, _, result) = "$serverUrl/decode/${args.username}/${code}".httpGet()
                .responseString()

            val resultJson = JSONObject(result.get())
            val products = resultJson.get("products") as JSONArray
            val product = products.get(0) as JSONObject

            if (product.has("error")) {
                item = ItemObject("Barcode", product.get("barcode_number") as String)
                list.add(item)
                item = ItemObject("Not found", product.get("error") as String)
                list.add(item)
            } else {
                val response = Gson().fromJson(result.get(), RootObject::class.java)
                val productObject = response.products[0]

                if (productObject.barcode_number != "") {
                    item = ItemObject("Barcode", productObject.barcode_number!!)
                    list.add(item)
                }
                if (productObject.barcode_type != "") {
                    item = ItemObject("Barcode type", productObject.barcode_type!!)
                    list.add(item)
                }
                if (productObject.barcode_formats != "") {
                    item = ItemObject("Barcode formats", productObject.barcode_formats!!)
                    list.add(item)
                }
                if (productObject.mpn != "") {
                    item = ItemObject("MPN", productObject.mpn!!)
                    list.add(item)
                }
                if (productObject.model != "") {
                    item = ItemObject("Model", productObject.model!!)
                    list.add(item)
                }
                if (productObject.asin != "") {
                    item = ItemObject("ASIN", productObject.asin!!)
                    list.add(item)
                }
                if (productObject.product_name != "") {
                    item = ItemObject("Product name", productObject.product_name!!)
                    list.add(item)
                }
                if (productObject.title != "") {
                    item = ItemObject("Title", productObject.title!!)
                    list.add(item)
                }
                if (productObject.category != "") {
                    item = ItemObject("Category", productObject.category!!)
                    list.add(item)
                }
                if (productObject.manufacturer != "") {
                    item = ItemObject("Manufacturer", productObject.manufacturer!!)
                    list.add(item)
                }
                if (productObject.brand != "") {
                    item = ItemObject("Brand", productObject.brand!!)
                    list.add(item)
                }
                if (productObject.label != "") {
                    item = ItemObject("Label", productObject.label!!)
                    list.add(item)
                }
                if (productObject.author != "") {
                    item = ItemObject("Author", productObject.author!!)
                    list.add(item)
                }
                if (productObject.publisher != "") {
                    item = ItemObject("Publisher", productObject.publisher!!)
                    list.add(item)
                }
                if (productObject.artist != "") {
                    item = ItemObject("Artist", productObject.artist!!)
                    list.add(item)
                }
                if (productObject.actor != "") {
                    item = ItemObject("Actor", productObject.actor!!)
                    list.add(item)
                }
                if (productObject.director != "") {
                    item = ItemObject("Director", productObject.director!!)
                    list.add(item)
                }
                if (productObject.studio != "") {
                    item = ItemObject("Studio", productObject.studio!!)
                    list.add(item)
                }
                if (productObject.genre != "") {
                    item = ItemObject("Genre", productObject.genre!!)
                    list.add(item)
                }
                if (productObject.audience_rating != "") {
                    item = ItemObject("Audience rating", productObject.audience_rating!!)
                    list.add(item)
                }
                if (productObject.ingredients != "") {
                    item = ItemObject("Ingredients", productObject.ingredients!!)
                    list.add(item)
                }
                if (productObject.nutrition_facts != "") {
                    item = ItemObject("Nutrition facts", productObject.nutrition_facts!!)
                    list.add(item)
                }
                if (productObject.color != "") {
                    item = ItemObject("Color", productObject.color!!)
                    list.add(item)
                }
                if (productObject.format != "") {
                    item = ItemObject("Format", productObject.format!!)
                    list.add(item)
                }
                if (productObject.package_quantity != "") {
                    item = ItemObject("Package quantity", productObject.package_quantity!!)
                    list.add(item)
                }
                if (productObject.size != "") {
                    item = ItemObject("Size", productObject.size!!)
                    list.add(item)
                }
                if (productObject.length != "") {
                    item = ItemObject("Length", productObject.length!!)
                    list.add(item)
                }
                if (productObject.width != "") {
                    item = ItemObject("Width", productObject.width!!)
                    list.add(item)
                }
                if (productObject.height != "") {
                    item = ItemObject("Height", productObject.height!!)
                    list.add(item)
                }
                if (productObject.weight != "") {
                    item = ItemObject("Weight", productObject.weight!!)
                    list.add(item)
                }
                if (productObject.release_date != "") {
                    item = ItemObject("Release date", productObject.release_date!!)
                    list.add(item)
                }
                if (productObject.description != "") {
                    item = ItemObject("Description", productObject.description!!)
                    list.add(item)
                }
                if (productObject.images.isNotEmpty()) {
                    val bmp: Bitmap
                    val imageUrl = productObject.images[0]
                    val `in`: InputStream = URL(imageUrl).openStream()
                    bmp = BitmapFactory.decodeStream(`in`)

                    productImage.setImageBitmap(bmp)
                }
                /*if (productObject.stores.isNotEmpty()) {
                    for (store in productObject.stores) {
                    }
                }
                if (productObject.features.isNotEmpty()) {

                }
                if (productObject.reviews.isNotEmpty()) {

                }*/
            }

        }

        val adapter = CustomAdapter(list)

        detailsList.adapter = adapter
        detailsList.layoutManager = LinearLayoutManager(context)
    }
}