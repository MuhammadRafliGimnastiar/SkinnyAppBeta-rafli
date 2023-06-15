package com.gimnastiar.skinnyappbeta.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.databinding.ActivityMainBinding
import com.gimnastiar.skinnyappbeta.ui.favorite.FavoriteActivity
import com.gimnastiar.skinnyappbeta.ui.history.HistoryFragment
import com.gimnastiar.skinnyappbeta.ui.homeFragment.HomeFragment
import com.gimnastiar.skinnyappbeta.ui.setting.SettingActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dialog = ChoseImageButtomSheet()


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

        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivityForResult(intent, 123)
                }
                R.id.favorite -> {
                    startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            val value = data?.getStringExtra("key")
            if (value == "finsihApp") {
                finish()
            }
            // Lakukan sesuatu dengan nilai yang dikirim kembali
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        //handle finish()
//        finish()
    }
}