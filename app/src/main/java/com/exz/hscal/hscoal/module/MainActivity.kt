package com.exz.hscal.hscoal.module

import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SizeUtils
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.TabEntity
import com.exz.hscal.hscoal.module.demand.CoalFragment
import com.exz.hscal.hscoal.utils.DialogUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news.*
import java.util.*

class MainActivity : BaseActivity() {

    private val mTitles = arrayOf("首页", "信息资讯", "需求专区", "我的")
    private val mIconSelectIds = intArrayOf(R.mipmap.icon_tab1_on, R.mipmap.icon_tab2_on, R.mipmap.icon_tab3_on, R.mipmap.icon_tab4_on)
    private val mIconUnSelectIds = intArrayOf(R.mipmap.icon_tab1_off, R.mipmap.icon_tab2_off, R.mipmap.icon_tab3_off, R.mipmap.icon_tab4_off)
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val mFragments = ArrayList<Fragment>()


    override fun setInflateId(): Int {
        return R.layout.activity_main
    }

    override fun initToolbar(): Boolean {
        StatusBarUtil.immersive(this)
        return false
    }

    override fun init() {
//        SZWUtils.resetProgress(progressBar=progressBar,tagView = bar_tag,start = 0,end = 50,secondStart = 0,secondEnd = 100,animatorSpeed = 30,secondAnimatorSpeed = 15,delayMillis = 1000)
        mTitles.indices.mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        mFragments.add(MainFragment.newInstance())
        mFragments.add(NewsFragment.newInstance())
        mFragments.add(DemandFragment.newInstance())
        mFragments.add(MineFragment.newInstance())
        mainTabBar.setTabData(mTabEntities, this, R.id.frameLayout, mFragments)
        mainTabBar.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                startAnimation(position)
            }

            override fun onTabReselect(position: Int) {
                startAnimation(position)
            }
        })

        DataCtrlClass.updateApk(mContext, AppUtils.getAppVersionName(),{
            if (it != null) {
                if (it.data?.isUpgrade.equals("1")) {
                    DialogUtils.updateApk(mContext, it.data?.tip ?: "", it.data?.isMust ?: "", {
                        val uri = Uri.parse(it.data?.loadUrl)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        if(it.data?.isMust.equals("0")){
                            startActivity(Intent.createChooser(intent, "请选择浏览器"));
                        }else{
                            startActivity(intent)
                            finishAffinity()
                        }
                    })
                }

            }
        })
    }

    fun startAnimation(position: Int) {
        val iconAnimate = ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f)
        val iconAnimateMain = ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        val titleAnimate = ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
        iconAnimate.duration = 50
        iconAnimateMain.duration = 50
        titleAnimate.duration = 50

        if (position == 0) {
            mainTabBar.getTitleView(0).visibility = View.GONE
            val lp = LinearLayout.LayoutParams(SizeUtils.dp2px(50f), SizeUtils.dp2px(50f))
            lp.topMargin = SizeUtils.dp2px(5f)
            mainTabBar.getIconView(0).layoutParams = lp
            mainTabBar.getIconView(0).startAnimation(iconAnimateMain)
            mainTabBar.getIconView(0).scaleType = ImageView.ScaleType.CENTER_INSIDE
        } else {
            mainTabBar.getTitleView(position).visibility = View.VISIBLE
            mainTabBar.getTitleView(0).visibility = View.VISIBLE
            val lp = LinearLayout.LayoutParams(SizeUtils.dp2px(37f), SizeUtils.dp2px(37f))
            mainTabBar.getIconView(0).layoutParams = lp
            mainTabBar.getIconView(0).scaleType = ImageView.ScaleType.FIT_CENTER
            mainTabBar.getTitleView(position).startAnimation(titleAnimate)
            mainTabBar.getIconView(position).startAnimation(iconAnimate)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if ((keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN)
                && mFragments.get(mainTabBar.currentTab) is NewsFragment
                && (mFragments.get(mainTabBar.currentTab) as NewsFragment).mWebView.canGoBack()) {
            (mFragments.get(mainTabBar.currentTab) as NewsFragment).mWebView.goBack()
            return true
        } else
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.addCategory(Intent.CATEGORY_HOME)
                startActivity(intent)
                return true
            }
        return super.onKeyDown(keyCode, event)
    }


}
