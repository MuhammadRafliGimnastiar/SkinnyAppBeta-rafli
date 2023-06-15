package com.gimnastiar.skinnyappbeta.ui.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.databinding.FragmentHistoryBinding
import com.gimnastiar.skinnyappbeta.ui.dapter.HistoryTabAdapter
import com.gimnastiar.skinnyappbeta.ui.setting.SettingActivity
import com.gimnastiar.skinnyappbeta.utils.LoginPreference
import com.gimnastiar.skinnyappbeta.utils.UserData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: LoginPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = LoginPreference(requireContext())
        val data = pref.getUserData()

        setName(data)
        setTabAdapter()
        buttonclick()
    }

    private fun setName(data: UserData) {
        if (data.name != null) {
            binding.apply {
                tvName.text = data.username
            }
        }
    }

    private fun buttonclick() {
        with(binding) {
            btnSetting.setOnClickListener {
                val intent = Intent(requireContext(), SettingActivity::class.java)
                startActivity(intent)
            }

            tvProfileMore.setOnClickListener {
                Toast.makeText(requireContext(), "Masih dalam pengembangan!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setTabAdapter() {

        val sectionsPagerAdapter = HistoryTabAdapter(requireActivity() as AppCompatActivity)
        with(binding) {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
//            supportActionBar?.elevation = 0f
        }
    }

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}