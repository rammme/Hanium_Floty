package com.hanium.floty.fragment

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hanium.floty.R
import com.hanium.floty.adapter.DiaryListAdapter
import com.hanium.floty.adapter.DictionaryAdapter
import com.hanium.floty.model.Diary
import com.hanium.floty.model.Plant
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.lang.Exception

class DictionaryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var pullParserFactory: XmlPullParserFactory
    var plantList = arrayListOf<Plant>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.fragment_dictionary, container, false)

//        var searchView: SearchView = view.findViewById(R.id.search_view)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                // 검색 버튼 누를 때 호출
//
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                // 검색 창에서 글자 변경이 일어날 때마다 호출
//
//                return true
//            }
//        })

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)

        try {
            var xpp: XmlPullParser = resources.getXml(R.xml.plant)

            while (xpp.eventType != XmlPullParser.END_DOCUMENT) {
                if (xpp.eventType == XmlPullParser.START_TAG) {
                    if (xpp.name.equals("plant")) {
                        val plant = Plant()
                        plant.plantkor = xpp.getAttributeValue(3)
                        plant.planteng = xpp.getAttributeValue(2)
                        plant.temp = xpp.getAttributeValue(4)
                        plant.hum = xpp.getAttributeValue(0)
                        plant.imgurl = xpp.getAttributeValue(1)

                        plantList.add(plant)
                    }
                }
                xpp.next()
            }
            Log.e("MY_VALUE", plantList.toString())
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        val adapter = DictionaryAdapter(context!!, plantList)
        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()

        return view
    }

}