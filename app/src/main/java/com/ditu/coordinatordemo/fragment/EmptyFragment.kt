package com.ditu.coordinatordemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ditu.coordinatordemo.adapter.NewsAdapter
import com.ditu.coordinatordemo.databinding.FragmentLayoutEmptyBinding
import com.ditu.coordinatordemo.databinding.FragmentLayoutNewsBinding
import com.ditu.coordinatordemo.event.EventHub
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber

/**
 *=======================================
 *
 * Created by Ditu on 4/7/21
 *=======================================
 */
class EmptyFragment : BaseLazyFragment() {

    private  var binding : FragmentLayoutEmptyBinding? = null

    private val name: String? by lazy {
        arguments?.run {
            getString("NAME")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLayoutEmptyBinding.inflate(inflater,container,false)
        initView()
        return binding?.root
    }


    private fun initView() {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun lazyLoadData() {

    }

    companion object{
        fun newInstance(name: String): EmptyFragment {
            var weatherFragment = EmptyFragment()
            var bundle = Bundle()
            bundle.putString("NAME", name)
            weatherFragment.arguments = bundle
            return weatherFragment
        }
    }




}