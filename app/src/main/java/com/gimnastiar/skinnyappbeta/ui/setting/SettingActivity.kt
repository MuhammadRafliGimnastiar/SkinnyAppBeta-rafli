package com.gimnastiar.skinnyappbeta.ui.setting

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.ui.MainActivity
import com.gimnastiar.skinnyappbeta.databinding.ActivitySettingBinding
import com.gimnastiar.skinnyappbeta.ui.FrontActivity
import com.gimnastiar.skinnyappbeta.ui.setting.setUp.SettingPreferences
import com.gimnastiar.skinnyappbeta.ui.setting.setUp.SettingViewModelFactory
import com.gimnastiar.skinnyappbeta.utils.LoginPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewModel

    private lateinit var loginPref: LoginPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(this.dataStore)
        viewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[SettingViewModel::class.java]

        loginPref = LoginPreference(this)

        topBarAction()
        setTheme()
        buttonClick()
    }

    private fun buttonClick() {
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        binding.btnLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun showLogoutDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_log_out)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val btnYes: Button = dialog.findViewById(R.id.btn_yes)
        val btnNo: Button = dialog.findViewById(R.id.btn_no)

        val resultIntent = Intent()
        val value = "finishApp"
        resultIntent.putExtra("key", value)
        setResult(Activity.RESULT_OK, resultIntent)

        btnYes.setOnClickListener{
            loginPref.removeLogin()
            dialog.dismiss()
            finish()
            val intent = Intent(this, FrontActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Anda telah Keluar dari Story App!", Toast.LENGTH_SHORT).show()
        }

        btnNo.setOnClickListener{
            dialog.dismiss()
        }

    }

    private fun topBarAction() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setTheme() {
        binding.apply {
            viewModel.getThemeSettings().observe(this@SettingActivity) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    btnTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    btnTheme.isChecked = false
                }
            }
            btnTheme.setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
                viewModel.saveThemeSetting(isChecked)
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

//        startActivity(Intent(this, MainActivity::class.java))
    }
}