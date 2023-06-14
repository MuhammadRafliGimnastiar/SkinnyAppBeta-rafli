package com.gimnastiar.skinnyappbeta.ui.loginFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseLogin
import com.gimnastiar.skinnyappbeta.data.repository.Resource
import com.gimnastiar.skinnyappbeta.databinding.FragmentLoginBinding
import com.gimnastiar.skinnyappbeta.ui.ViewModelFactory
import com.gimnastiar.skinnyappbeta.utils.LoginPreference

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vmFactory = ViewModelFactory(requireContext())
        viewModel = ViewModelProvider(
            requireActivity(),
            vmFactory
        ).get(LoginViewModel::class.java)

        buttonClick()
    }

    private fun buttonClick() {
        binding.apply {
            btnLogin.setOnClickListener {
                val username = usernameInput.text
                val password = passwordInput.text

                val cUsername = username != null && username.toString().isNotEmpty()
                val cPassword = password != null && password.toString().isNotEmpty()

                if(cUsername && cPassword) {
                    loginHandler(
                        username.toString().trim(),
                        password.toString().trim()
                    )
                } else {
                    Toast.makeText(requireContext(), "Harap masukan input terlebih dahulu!", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.btnToRegist.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    fun loginHandler(username: String, password: String) {
        viewModel.login(
            username,
            password
        ).observe(requireActivity()) {
            if (it != null) {
                when(it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        succesHandler(it.data)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        errorHandler(it.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                animLoading.visibility = View.VISIBLE
                bgLoading.visibility = View.VISIBLE
            } else {
                animLoading.visibility = View.GONE
                bgLoading.visibility = View.GONE
            }
        }

    }

    private fun succesHandler(data: ResponseLogin) {
        //save data login to preference
        LoginPreference(requireContext()).saveLogin(data)

        Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        requireActivity().finish()
    }

    private fun errorHandler(message: String) {
        Log.e("ERROR LOGIN", message)
        Toast.makeText(requireContext(), "Harap Coba Lagi", Toast.LENGTH_SHORT).show()
    }

}