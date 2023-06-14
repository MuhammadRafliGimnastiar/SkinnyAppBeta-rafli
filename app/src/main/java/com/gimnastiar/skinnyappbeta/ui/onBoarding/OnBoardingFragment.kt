package com.gimnastiar.skinnyappbeta.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.databinding.FragmentOnBoardingBinding
import com.gimnastiar.skinnyappbeta.ui.MainActivity
import com.gimnastiar.skinnyappbeta.utils.LoginPreference
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: LoginPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = LoginPreference(requireContext())
        val data = pref.getUserData()

        binding.btnCreateAccount.setOnClickListener {
            if (data.username != null) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
            }
            pref.hiddenOnBoarding()
        }

        binding.viewPager.adapter = OnBoardingFragmentAdapter(requireActivity(), requireContext())
        TabLayoutMediator(binding.pageIndicator, binding.viewPager) { _, _ -> }.attach()
        binding.viewPager.offscreenPageLimit = 1
    }

}