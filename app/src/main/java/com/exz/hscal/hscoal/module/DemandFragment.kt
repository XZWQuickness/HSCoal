package com.exz.hscal.hscoal.module

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.demand.CoalFragment
import com.exz.hscal.hscoal.module.demand.SteelFragment
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_demand.*
import com.exz.hscal.hscoal.R.string.commit
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*
import com.exz.hscal.hscoal.R.string.commit




/**
 * Created by pc on 2017/12/4.
 * 需求专区
 */

class DemandFragment : MyBaseFragment() {
    private var fragment: Fragment? = null
    private val mTitles = arrayOf("煤炭", "金属")
    private val mFragments = ArrayList<Fragment>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_demand, container, false)
        return rootView
    }

    override fun initView() {
        initBar()
        initTab()
    }


    private fun initTab() {
        mFragments.add(CoalFragment.newInstance())
        mFragments.add(SteelFragment.newInstance())
        tl_1.setTabData(mTitles)
        tl_1.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchContent(fragment, mFragments[position]);
                fragment = mFragments[position];
            }

            override fun onTabReselect(position: Int) {
            }
        })

        switchContent(fragment, mFragments[1]);
        switchContent(fragment, mFragments[0]);
        fragment = mFragments[0];

    }

    private fun initBar() {
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, llLay)

    }


    companion object {
        fun newInstance(): DemandFragment {
            val bundle = Bundle()
            val fragment = DemandFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    fun switchContent(from: Fragment?, to: Fragment) {
        val transaction = childFragmentManager.beginTransaction()

        if (from != null) {
            transaction.hide(from)
        }
        if (!to.isAdded) { // 先判断是否被add过
            transaction.add(R.id.mFrameLayout, to).commit() // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.show(to).commit() // 隐藏当前的fragment，显示下一个
        }
    }
}