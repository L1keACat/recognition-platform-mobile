package com.example.mobileclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.adapters.HistoryAdapter
import com.example.mobileclient.model.HistoryItem
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import org.json.JSONArray

class HistoryFragment : Fragment() {

    val args: HistoryFragmentArgs by navArgs()

    private lateinit var historyTextView: TextView
    private lateinit var historyList: RecyclerView

    private val serverUrl: String = "http://10.0.2.2:8080"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyTextView = view.findViewById(R.id.history_textview)
        historyList = view.findViewById(R.id.history_list)

        val username = args.username

        historyTextView.text = getString(R.string.history_textview, username)

        val (_, _, result) = "$serverUrl/history/${args.username}".httpGet()
            .responseString()

        val list: MutableList<HistoryItem> = mutableListOf()
        var item: HistoryItem

        val resultJson = JSONArray(result.get())
        for (i in 0 until resultJson.length()) {
            val historyItem = Gson().fromJson(resultJson.get(i).toString(), HistoryItem::class.java)
            list.add(historyItem)
        }

        val adapter = HistoryAdapter(list)
        historyList.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        historyList.layoutManager = linearLayoutManager
    }
}