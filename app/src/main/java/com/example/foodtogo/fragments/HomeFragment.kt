package com.example.foodtogo.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodtogo.FoodDatabase
import com.example.foodtogo.FoodEntity
import com.example.foodtogo.R
import com.example.foodtogo.adapter.HomeRecyclerAdapter
import com.example.foodtogo.model.Food
import com.example.foodtogo.util.ConnectionManager
import org.json.JSONException
import java.util.*
import kotlin.collections.HashMap

class HomeFragment : Fragment() {



    lateinit var recylerview: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar


    val bookInfoList = arrayListOf<Food>()
    lateinit var recyleradapter : HomeRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recylerview = view.findViewById(R.id.recyclerDashboard)
        layoutManager= LinearLayoutManager(activity)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)

        progressLayout.visibility=View.VISIBLE



        setHasOptionsMenu(true)

        val queue = Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v2/restaurants/fetch_result/"

        if(ConnectionManager().checkconnectivity(activity as Context)) {
            val jsonObjectRequest =
                object : JsonObjectRequest(
                    Request.Method.GET, url, null, Response.Listener {


                        try {

                            progressLayout.visibility=View.GONE
                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            if (success) {
                                val data = data.getJSONArray("data")
                                for (i in 0 until data.length()) {
                                    val foodJsonObject = data.getJSONObject(i)
                                  val foodObject = Food(

                                        foodJsonObject.getString("id").toInt(),


                                        foodJsonObject.getString("name"),
                                        foodJsonObject.getString("rating"),
                                        foodJsonObject.getString("cost_for_one").toInt(),
                                      foodJsonObject.getString("image_url")

                                    )
                                    bookInfoList.add(foodObject)
                                    recyleradapter =
                                        HomeRecyclerAdapter(activity as Context, bookInfoList)
                                    recylerview.adapter = recyleradapter
                                    recylerview.layoutManager = layoutManager


                                }
                            } else {
                                Toast.makeText(
                                    activity as Context,
                                    "Some error Occurerd!!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } catch(e: JSONException)
                        {
                            Toast.makeText(activity as Context,"Some unexpected error occured!!!",
                                Toast.LENGTH_LONG).show()
                        }

                    },
                    Response.ErrorListener {
                        if(activity!=null) {
                            Toast.makeText(
                                activity as Context,
                                "Volley error occured!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "14a430e270b218"
                        return headers
                    }
                }
            queue.add(jsonObjectRequest)
        }
        else
        {
            val dialog= AlertDialog.Builder(activity as Context)
            dialog.setTitle("Success")
            dialog.setMessage("Internet Connection Not  Found")
            dialog.setPositiveButton("Open Settings"){
                    text,listener->

                val settingintent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingintent)
                activity?.finish()




            }
            dialog.setNegativeButton("Exit"){
                    text,listener->
                ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()
        }
        return view

    }



}
