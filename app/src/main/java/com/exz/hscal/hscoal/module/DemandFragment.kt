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


/**
 * Created by pc on 2017/12/4.
 * 需求专区
 */

class DemandFragment : MyBaseFragment() {
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
        tl_1.setTabData(mTitles, activity, R.id.mFrameLayout, mFragments);
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
}