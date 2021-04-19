package com.ditu.coordinatordemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ditu.coordinatordemo.adapter.NewsAdapter
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
class NewsFragment : BaseLazyFragment() {

    private  var binding : FragmentLayoutNewsBinding? = null

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
        binding = FragmentLayoutNewsBinding.inflate(inflater,container,false)
        EventBus.getDefault().register(this)
        initView()
        return binding?.root
    }


    private fun initView() {
        binding?.apply {
            refresh.setOnRefreshListener {
                list.clear()
                for (i in 0..20) {
                    list.add("$i-item")
                }
                refresh.finishRefresh()
                mAdapter?.notifyDataSetChanged()
            }

            refresh.setOnLoadMoreListener {
                for (i in 0..20) {
                    list.add("$i-item")
                }
                refresh.finishLoadMore()
                mAdapter?.notifyDataSetChanged()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
        binding = null
    }

    private lateinit var mAdapter: NewsAdapter


    override fun lazyLoadData() {
        for (i in 0..20) {
            list.add("$i-item")
        }
        mAdapter = NewsAdapter(list)
        binding?.rvNews?.adapter = mAdapter
    }

    companion object{
        fun newInstance(name: String): NewsFragment {
            var weatherFragment = NewsFragment()
            var bundle = Bundle()
            bundle.putString("NAME", name)
            weatherFragment.arguments = bundle
            return weatherFragment
        }
    }


    @Subscriber(tag = EventHub.TAB_STATE)
    private fun onTabStateEvent(state: Boolean) {
        binding?.apply {
            rvNews.isNestedScrollingEnabled = state
        }
    }

}