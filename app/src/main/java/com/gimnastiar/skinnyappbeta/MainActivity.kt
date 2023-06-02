package com.gimnastiar.skinnyappbeta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.gimnastiar.skinnyappbeta.databinding.ActivityMainBinding
import com.gimnastiar.skinnyappbeta.ui.history.HistoryFragment
import com.gimnastiar.skinnyappbeta.ui.homeFragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.background = null

        replaceFragment(HomeFragment())
        iconBarClick()
        scanButton()

    }

    private fun scanButton(){
        binding.btnScan.setOnClickListener{
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = ChoseImageButtomSheet()
        dialog.show(supportFragmentManager, ChoseImageButtomSheet.TAG)
    }

    private fun iconBarClick() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.history_profile -> replaceFragment(HistoryFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}