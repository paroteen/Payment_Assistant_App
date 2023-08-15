package com.example.gymapp.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymapp.adapter.PaymentAdapter
import com.example.gymapp.database.SubscribersDatabase
import com.example.gymapp.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {
    private var _binding : FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val paymentDao = SubscribersDatabase.getInstance(application).paymentDao

        val viewModelFactory = PaymentViewModelFactory(paymentDao)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(PaymentViewModel::class.java)

        binding.paymentRecyclerView.layoutManager = LinearLayoutManager(application)

        viewModel.payments.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter = PaymentAdapter(application,it)
                binding.paymentRecyclerView.adapter = adapter
            }
        })


        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
}