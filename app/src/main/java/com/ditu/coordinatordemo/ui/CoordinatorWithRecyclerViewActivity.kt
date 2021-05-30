package com.ditu.coordinatordemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ditu.coordinatordemo.R
import com.ditu.coordinatordemo.adapter.NewsAdapter
import com.ditu.coordinatordemo.databinding.ActivityCoordinatorWithRecyclerViewBinding
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import org.simple.eventbus.EventBus

class CoordinatorWithRecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoordinatorWithRecyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoordinatorWithRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private val list = mutableListOf<String>()
    private lateinit var mAdapter: NewsAdapter
    private fun initUI() {

        for (i in 0..20) {
            list.add("$i-item")
        }
        mAdapter = NewsAdapter(list)
        binding?.rvRecycler?.adapter = mAdapter

        binding.refresh.setOnMultiListener(object : SimpleMultiListener(){
            override fun onRefresh(refreshLayout: RefreshLayout) {
                super.onRefresh(refreshLayout)
                list.clear()
                for (i in 0..20) {
                    list.add("$i-item")
                }
                binding.refresh.finishRefresh()
                mAdapter?.notifyDataSetChanged()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                super.onLoadMore(refreshLayout)
                for (i in 0..20) {
                    list.add("$i-item")
                }
                binding.refresh.finishLoadMore()
                mAdapter?.notifyDataSetChanged()
            }
        })
    }
}