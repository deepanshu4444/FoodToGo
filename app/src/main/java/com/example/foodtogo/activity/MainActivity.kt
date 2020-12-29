package com.example.foodtogo.activity


import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.foodtogo.R
import com.example.foodtogo.fragments.FaqFragment
import com.example.foodtogo.fragments.FavouriteFragment
import com.example.foodtogo.fragments.HomeFragment
import com.example.foodtogo.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var drawerlayout: DrawerLayout
    lateinit var framelayout: FrameLayout
    lateinit var coordinatelayout: CoordinatorLayout
    lateinit var navigationview: NavigationView
    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationview=findViewById(R.id.navigationview)
        toolbar=findViewById(R.id.toolbar)
        framelayout=findViewById(R.id.framelayout)
        coordinatelayout=findViewById(R.id.coordinatelayout)
        drawerlayout=findViewById(R.id.drawerlayout)

        setUpToolbar()
        var actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,drawerlayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerlayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        openHome()
        navigationview.setNavigationItemSelectedListener {


            if(previousMenuItem!=null)
            {
                previousMenuItem?.isChecked=false
            }

            it.isChecked=true
            it.isCheckable=true
            previousMenuItem=it

            when(it.itemId)
            {
                R.id.Home ->
                {

                    openHome()
                }

                R.id.FavouriteRestaurants ->
                {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framelayout,
                        FavouriteFragment()
                    ).commit()
                    drawerlayout.closeDrawers()
                    supportActionBar?.title="Favourites Restaurants"
                }

                R.id.MyProfile ->
                {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framelayout,
                        ProfileFragment()
                    ).commit()
                    drawerlayout.closeDrawers()
                    supportActionBar?.title="My Profile"
                }

                R.id.FAQs ->
                {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framelayout,
                        FaqFragment()
                    ).commit()
                    drawerlayout.closeDrawers()
                    supportActionBar?.title="FAQs"
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="ALL Restaurants"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id==android.R.id.home)
        {
            drawerlayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun openHome()
    {
        val fragment = HomeFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout,fragment)
        transaction.commit()
        navigationview.setCheckedItem(R.id.Home)
        supportActionBar?.title="ALL Restaurants"

    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.framelayout)
        when(frag)
        {
            !is HomeFragment -> openHome()

            else->super.onBackPressed()
        }
    }
}
