package com.gimnastiar.skinnyappbeta.ui.onBoarding

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.ui.onBoarding.scene.OnBoardingContentFragment

class OnBoardingFragmentAdapter(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingContentFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_1),
                context.resources.getString(R.string.description_onboarding_1),
                R.drawable.mobile_vector
            )
            1 -> OnBoardingContentFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_2),
                context.resources.getString(R.string.description_onboarding_2),
                R.drawable.model_vector
            )
            else -> OnBoardingContentFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_3),
                context.resources.getString(R.string.description_onboarding_3),
                R.drawable.health_vector

            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}