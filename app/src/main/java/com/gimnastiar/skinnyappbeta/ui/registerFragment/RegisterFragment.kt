package com.gimnastiar.skinnyappbeta.ui.registerFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.data.repository.Resource
import com.gimnastiar.skinnyappbeta.databinding.FragmentRegisterBinding
import com.gimnastiar.skinnyappbeta.ui.ViewModelFactory

class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vmFactory = ViewModelFactory(requireContext())
        viewModel = ViewModelProvider(
            requireActivity(),
            vmFactory
        ).get(RegisterViewModel::class.java)


        buttonClick()
        passwordListener()
    }

    fun passwordListener() {
        binding.passwordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.passwordLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun buttonClick() {
        binding.apply {
            btnRegist.setOnClickListener {
                val name = nameInput.text
                val username = usernameInput.text
                val password = passwordInput.text

                val cUser = name != null && name.toString().isNotEmpty()
                val cUsername = username != null && username.toString().isNotEmpty()
                val cPassword = password != null && password.toString().isNotEmpty()

                if(cUser && cUsername && cPassword) {
                    registHandler(
                        name.toString().trim(),
                        username.toString().trim(),
                        password.toString().trim()
                    )
                } else {
                    Toast.makeText(requireContext(), "Harap masukan input terlebih dahulu!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun registHandler(name: String, username: String, password: String) {
        viewModel.register(
            name,
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
                        succesHandler(it.data.message)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        errorHandler(it.data)
                    }
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

    private fun succesHandler(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.loginFragment)
    }

    private fun errorHandler(message: String) {
        Log.e("Error Register", message)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {

    }
}