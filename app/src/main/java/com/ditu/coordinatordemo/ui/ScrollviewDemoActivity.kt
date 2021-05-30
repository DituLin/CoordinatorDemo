package com.ditu.coordinatordemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ditu.coordinatordemo.databinding.ActivityScrollviewDemoBinding
import com.ditu.coordinatordemo.fragment.EmptyFragment
import com.ditu.coordinatordemo.fragment.WeatherFragment
import com.flyco.tablayout.listener.OnTabSelectListener
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import org.simple.eventbus.EventBus

class ScrollviewDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollviewDemoBinding

    private val titles = arrayOf("推荐","热门")

    private val listFragment = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollviewDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        listFragment.add(WeatherFragment.newInstance(titles[0]))
        listFragment.add(EmptyFragment.newInstance(titles[1]))
        val adapter = ScrollFragmentAdapter(supportFragmentManager,titles.asList())
        binding.vpScroll.adapter = adapter
        binding.vpScroll.offscreenPageLimit = titles.size
        binding.stlScroll.setViewPager(binding.vpScroll,titles)
        binding.stlScroll.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                if (position == 0) {
                    binding.refresh.setEnableLoadMore(true)
                }else{
                    binding.refresh.setEnableLoadMore(false)
                }

            }

            override fun onTabReselect(position: Int) {

            }

        })

        binding.refresh.setOnMultiListener(object :SimpleMultiListener(){
            override fun onRefresh(refreshLayout: RefreshLayout) {
                super.onRefresh(refreshLayout)
                EventBus.getDefault().post(true,"Refresh")
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                super.onLoadMore(refreshLayout)
               EventBus.getDefault().post(false,"Refresh")
            }
        })

        binding.vpScroll.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding.refresh.setEnableLoadMore(true)
                }else{
                    binding.refresh.setEnableLoadMore(false)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }



    fun onRefreshFinish(hasMore: Boolean) {
        if (hasMore) {
            binding.refresh.finishRefresh()
        }else{
            binding.refresh.finishRefreshWithNoMoreData()
        }
    }

    fun onLoadMoreFinish(hasMore: Boolean) {
        if (hasMore) {
            binding.refresh.finishLoadMore()
        }else{
            binding.refresh.finishLoadMoreWithNoMoreData()
        }
    }



    class ScrollFragmentAdapter(fm: FragmentManager,private val list: List<String>) : FragmentPagerAdapter(fm){
        override fun getCount() = list.size

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> WeatherFragment.newInstance(list[position])
                else -> EmptyFragment.newInstance(list[position])
            }
        }

    }


}