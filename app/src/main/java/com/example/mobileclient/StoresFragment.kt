package com.example.mobileclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.adapters.DetailsAdapter
import com.example.mobileclient.model.ItemObject
import com.example.mobileclient.model.Sample
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject


class StoresFragment : Fragment() {

    val args: StoresFragmentArgs by navArgs()

    private lateinit var storesList: RecyclerView
    private lateinit var storesCountTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storesList = view.findViewById(R.id.stores_list)
        storesCountTextView = view.findViewById(R.id.stores_count)

        val list: MutableList<ItemObject> = mutableListOf()
        var item: ItemObject

        val data = JSONObject(args.stores)
        val values = data.get("values") as JSONArray

        for (i in 0 until values.length()) {
            val value = values.get(i) as JSONObject
            val store =
                Gson().fromJson(value.get("nameValuePairs").toString(), Sample.Store::class.java)
            var itemKey: String
            var itemValue: String

            if (store.product_url != "" && store.store_name != "") {
                itemKey = store.product_url!!
                itemValue =
                    store.store_name!!
                if (store.store_price != "0.00") {
                    itemValue += " - " + store.store_price + store.currency_symbol + " (" + store.currency_code + ")"
                }
                item = ItemObject(itemKey, itemValue)
                list.add(item)
            }
        }

        storesCountTextView.text = getString(R.string.stores_count_textview, values.length())

        val adapter = DetailsAdapter(list)

        storesList.adapter = adapter
        storesList.layoutManager = LinearLayoutManager(context)
    }

}