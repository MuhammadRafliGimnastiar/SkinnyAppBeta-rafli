package com.gimnastiar.skinnyappbeta.ui.history.historyData

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.data.remote.model.DaftarHistoryItem
import com.gimnastiar.skinnyappbeta.data.repository.Resource
import com.gimnastiar.skinnyappbeta.databinding.FragmentHistoryBinding
import com.gimnastiar.skinnyappbeta.databinding.FragmentHistoryDataBinding
import com.gimnastiar.skinnyappbeta.ui.ViewModelFactory
import com.gimnastiar.skinnyappbeta.ui.dapter.HistoryAdapter
import com.gimnastiar.skinnyappbeta.ui.detect.DetectViewModel
import com.gimnastiar.skinnyappbeta.ui.history.detailHistory.DetailHistoryActivity
import com.gimnastiar.skinnyappbeta.utils.LoginPreference


class HistoryDataFragment : Fragment() {

    private var _binding: FragmentHistoryDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HistoryDataViewModel

    private lateinit var pref: LoginPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryDataBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vmFactory = ViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, vmFactory).get(HistoryDataViewModel::class.java)

        pref = LoginPreference(requireContext())

        val username = pref.getUserData().username!!
        getData(username)
    }

    private fun getData(username: String) {
        viewModel.getHistory(username).observe(requireActivity())  {
            if (it != null) {
                when(it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Error -> {
                        showLoading(true)
//                        errorHanler(it.data.toString())
                        Log.i("History Test", "EERROORRR ${it.data}")
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        successHandler(it.data.daftarHistory)
                    }
                }
            }
        }
    }

    private fun successHandler(data: List<DaftarHistoryItem>) {
//        Toast.makeText(requireContext(), "Sucess history data ${data[0].penanganan.classTerprediksi}", Toast.LENGTH_SHORT).show()
        with(binding) {
            rvHistory.layoutManager = LinearLayoutManager(requireContext())
            val adapter = HistoryAdapter(data)
            rvHistory.adapter= adapter

            adapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DaftarHistoryItem) {
                    val intent = Intent(requireContext(), DetailHistoryActivity::class.java)
                    intent.putExtra(DetailHistoryActivity.TAG, data)
                    startActivity(intent)
                }
            })
        }


    }

    private fun errorHanler(error: String) {
        binding.apply {
            shimmerViewContainer.visibility = View.GONE
            rvHistory.visibility = View.GONE
            animateError.visibility = View.VISIBLE
            tvError.visibility = View.VISIBLE
            tvErrorDescription.visibility = View.VISIBLE
            tvErrorDescription.text = error
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.apply {
                shimmerViewContainer.visibility = View.VISIBLE
                rvHistory.visibility = View.GONE
            }
        } else {
            binding.apply {
                shimmerViewContainer.visibility = View.GONE
                rvHistory.visibility = View.VISIBLE
            }
        }
    }

}