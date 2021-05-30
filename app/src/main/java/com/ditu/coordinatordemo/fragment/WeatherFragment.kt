package com.ditu.coordinatordemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ditu.coordinatordemo.ui.ScrollviewDemoActivity
import com.ditu.coordinatordemo.adapter.NewsAdapter
import com.ditu.coordinatordemo.databinding.FragmentLayoutWeatherBinding
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber

/**
 *=======================================
 *
 * Created by Ditu on 4/7/21
 *=======================================
 */
class WeatherFragment : BaseLazyFragment() {

    private  var binding : FragmentLayoutWeatherBinding? = null

    private val name: String? by lazy {
        arguments?.run {
            getString("NAME")
        }
    }

    private val list = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLayoutWeatherBinding.inflate(inflater,container,false)
        EventBus.getDefault().register(this)
        initView()
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun initView() {
        for (i in 0..20) {
            list.add("$i-item")
        }
        mAdapter = NewsAdapter(list)
        binding?.rvWeather?.adapter = mAdapter
    }


    fun onRefresh() {
        list.clear()
        for (i in 0..20) {
            list.add("$i-item")
        }
        mAdapter?.notifyDataSetChanged()
        val ac = activity as ScrollviewDemoActivity
        ac.onRefreshFinish(true)
    }

    fun onLoadMore() {
        for (i in 0..20) {
            list.add("$i-item")
        }
        mAdapter?.notifyDataSetChanged()
        val ac = requireActivity() as ScrollviewDemoActivity
        ac.onLoadMoreFinish(true)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private lateinit var mAdapter: NewsAdapter


    override fun lazyLoadData() {

    }

    @Subscriber(tag = "Refresh")
    fun onRefreshEvent(isRefresh: Boolean) {
        if (isRefresh) {
            onRefresh()
        }else{
            onLoadMore()
        }
    }

    companion object{
        fun newInstance(name: String): WeatherFragment {
            var weatherFragment = WeatherFragment()
            var bundle = Bundle()
            bundle.putString("NAME", name)
            weatherFragment.arguments = bundle
            return weatherFragment
        }
    }


}