package com.example.foodtogo.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.foodtogo.R

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    lateinit var name : TextView
    lateinit var address:TextView
    lateinit var mobile : TextView
    lateinit var email : TextView
    private lateinit var sharedPrefs: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val  view =inflater.inflate(R.layout.fragment_profile, container, false)

        sharedPrefs = (activity as FragmentActivity).getSharedPreferences("FoodToGo", Context.MODE_PRIVATE)
        name = view.findViewById(R.id.name)
        mobile =  view.findViewById(R.id.contact)
        email=view.findViewById(R.id.email)
        address = view.findViewById(R.id.address)

        name.text = sharedPrefs.getString("user_name", null)
        val phoneText = "+91-${sharedPrefs.getString("user_mobile_number", null)}"
        mobile.text = phoneText
        email.text = sharedPrefs.getString("user_email", null)
        val add = sharedPrefs.getString("user_address", null)
        address.text = add


        return view
    }

}
