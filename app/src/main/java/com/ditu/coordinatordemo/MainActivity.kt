package com.ditu.coordinatordemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import com.ditu.coordinatordemo.adapter.FragmentAdapter
import com.ditu.coordinatordemo.databinding.ActivityMainBinding
import com.ditu.coordinatordemo.event.EventHub
import com.ditu.coordinatordemo.ui.CoordinatorWitViewPagerActivity
import com.ditu.coordinatordemo.ui.CoordinatorWithRecyclerViewActivity
import com.ditu.coordinatordemo.ui.ScrollviewDemoActivity
import com.google.android.material.appbar.AppBarLayout
import org.simple.eventbus.EventBus

class MainActivity : AppCompatActivity() {

    private val titles = arrayOf("推荐","热门","美食","文化","科技","生活","艺术","数码")

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        EventBus.getDefault().register(this)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() {
        binding.appBar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                if (state != State.EXPANDED) {
                    EventBus.getDefault().post(state != State.COLLAPSED, EventHub.TAB_STATE)
                }
                if (state == State.COLLAPSED) {
                    binding.clToolbarNews.isGone = false
                    binding.clToolbarHome.isGone = true
                } else {
                    binding.clToolbarNews.isGone = true
                    binding.clToolbarHome.isGone = false
                }
            }
        })

        binding.appBar.viewTreeObserver.addOnGlobalLayoutListener {
            AppBarDragCallback.instance.setDragCallback(binding.appBar, true)
        }
        binding.tvHomeTt.setOnClickListener {
            EventBus.getDefault().post(false,EventHub.TAB_STATE)
            binding.appBar.setExpanded(false)
        }

        binding.tvBackHome.setOnClickListener {
            EventBus.getDefault().post(true,EventHub.TAB_STATE)
            binding.appBar.setExpanded(true)
        }


        binding.tvHomeDate.setOnClickListener {
            startActivity(Intent(this, CoordinatorWitViewPagerActivity::class.java))
        }
        binding.btnFirst.setOnClickListener {
            startActivity(Intent(this, CoordinatorWithRecyclerViewActivity::class.java))
        }
    }


    private fun initData() {
        val adapter = FragmentAdapter(supportFragmentManager,titles.asList())
        binding.vpHomeNews.adapter = adapter
        binding.vpHomeNews.offscreenPageLimit = titles.size
        binding.stlHomeNews.setViewPager(binding.vpHomeNews,titles)

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}