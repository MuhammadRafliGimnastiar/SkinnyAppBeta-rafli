package com.gimnastiar.skinnyappbeta.ui.dapter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gimnastiar.skinnyappbeta.ui.history.MedecineFragment.MedecineFragment
import com.gimnastiar.skinnyappbeta.ui.history.historyData.HistoryDataFragment

class HistoryTabAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HistoryDataFragment()
            1 -> fragment = MedecineFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}