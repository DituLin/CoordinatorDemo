package com.ditu.coordinatordemo

import android.view.ViewTreeObserver
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

/**
 *
 * <p>
 * Author: xuxinji
 * Date: 2021/3/19
 * Company: 厦门玖拾捌度信息科技有限公司
 * Updater:
 * Update Time:
 * Update Comments:
 */
class AppBarDragCallback {

    companion object {
        val instance: AppBarDragCallback by lazy { AppBarDragCallback() }
    }

    fun setDragCallback(appBar: AppBarLayout, canDrag: Boolean) {
        val params: CoordinatorLayout.LayoutParams =
            appBar.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as AppBarLayout.Behavior
        behavior.setDragCallback(object :
            AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(@NonNull appBarLayout: AppBarLayout): Boolean {
                return canDrag
            }
        })
    }

}