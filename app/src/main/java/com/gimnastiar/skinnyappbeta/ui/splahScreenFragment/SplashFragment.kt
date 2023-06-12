package com.gimnastiar.skinnyappbeta.ui.splahScreenFragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.databinding.FragmentSplashBinding
import com.gimnastiar.skinnyappbeta.ui.MainActivity
import com.gimnastiar.skinnyappbeta.utils.LoginPreference
import com.gimnastiar.skinnyappbeta.utils.UserData

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: LoginPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = LoginPreference(requireContext())
        val data = pref.getUserData()

        Handler().postDelayed({
            lifecycleScope.launchWhenCreated {
                movePage(data)
            }
        }, MILISECON.toLong())

    }

    private fun movePage(data: UserData) {
        if (data.name != null) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }

    }

    companion object{
        const val TAG = "SplashScreenFragment"
        const val MILISECON = 3000.0
    }

}