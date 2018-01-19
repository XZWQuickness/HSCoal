package com.exz.hscal.hscoal.module.demand

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSON
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.DemandAdapter
import com.exz.hscal.hscoal.bean.AreaBean
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.PopStairListBean
import com.exz.hscal.hscoal.pop.AreaPop
import com.exz.hscal.hscoal.pop.StairPop
import com.exz.hscal.hscoal.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.view.DrawableCenterButton
import org.jetbrains.anko.textColor
import razerdp.basepopup.BasePopupWindow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlinx.android.synthetic.main.fragment_coal.*


/**
 * Created by pc on 2017/12/5.
 */

class CoalFragment : MyBaseFragment(), OnRefreshListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    private var refreshState = com.szw.framelibrary.config.Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1

    private var coalVarietyId = ""//煤种id
    private var provinceId = ""//省份
    private var cityId = ""//城市
    private var sortType = "0"//排序方式（0综合排序 1求购数递增 2求购数递减 3收货时间最近 4收货时间最远）

    private lateinit var sortPop: StairPop
    private lateinit var coalPop: StairPop
    private lateinit var arePop: AreaPop
    private lateinit var mAdapter: DemandAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_coal, container, false)
        return rootView
    }


    override fun initView() {
        initRecycler()
        initPop()
        onRefresh(refreshLayout)
    }



    private fun initRecycler() {
        mAdapter = DemandAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(context, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        cocalRb1.setOnClickListener(this)
        cocalRb2.setOnClickListener(this)
        cocalRb3.setOnClickListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var entity = mAdapter.data.get(position)
                startActivity(Intent(activity, DemandCocalDetailAtivity::class.java).putExtra(DemandCocalDetailAtivity.Intent_Detaile_Id, entity.id))
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)

    }


    var sortData = ArrayList<PopStairListBean>()
    var coalData = ArrayList<PopStairListBean>()
    private fun initPop() {
        sortData.add(PopStairListBean("综合排序", true, "0"))
        sortData.add(PopStairListBean("求购数由低到高", false, "1"))
        sortData.add(PopStairListBean("求购数由高到低", false, "2"))
        sortData.add(PopStairListBean("收货时间最近", false, "3"))
        sortData.add(PopStairListBean("收货时间最远", false, "4"))
        coalData.add(PopStairListBean("全部煤种", true, ""))
        coalData.add(PopStairListBean("焦炭/焦粉/焦粒", false, "1"))
        coalData.add(PopStairListBean("炼焦煤", false, "2"))
        coalData.add(PopStairListBean("动力煤", false, "3"))
        sortPop = StairPop(activity, {
            if (it != null) {
                if (it.name.equals("综合排序")) {
                    setGaryOrblue(cocalRb1, false, "综合排序")
                } else {
                    setGaryOrblue(cocalRb1, true, it.name)
                }
                if(!sortType.equals( it.id)){
                    sortType = it.id
                    onRefresh(refreshLayout)

                }

                sortPop. dismiss()
                Log.i("coalPop","{sortPop}")
            }
        })
        sortPop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup1.clearCheck()

                Log.i("coalPop","sortPop onDismiss")
            }
        }
        sortPop.data = sortData
        coalPop = StairPop(activity, {

            Log.i("coalPop","{coalPop}")
            if (it != null) {
                if (it.name.equals("全部煤种")) {
                    setGaryOrblue(cocalRb2, false, "全部煤种")
                } else {
                    setGaryOrblue(cocalRb2, true, it.name)
                }
                if(!coalVarietyId.equals( it.id)){
                    coalVarietyId = it.id
                    onRefresh(refreshLayout)

                }
                coalPop. dismiss()
            }
        })
        coalPop.data = coalData
        coalPop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup1.clearCheck()
                Log.i("coalPop","coalPop onDismiss")
            }
        }
        arePop = AreaPop(context, { name, provinceId, cityId, check ->

            setGaryOrblue(cocalRb3, check, name)
            if(this.provinceId != provinceId){
                this.provinceId = provinceId
                onRefresh(refreshLayout)
            }
            if(this.cityId != cityId){
                this.cityId = cityId
                onRefresh(refreshLayout)

            }
            arePop.dismiss()

        })
        arePop.data = (JSON.parseArray(getJson(), AreaBean::class.java) as ArrayList<AreaBean>)
        arePop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {

                radioGroup1.clearCheck()
            }
        }

    }

    private fun getJson(): String {

        val stringBuilder = StringBuilder()
        try {
            val assetManager = activity.assets
            val bf = BufferedReader(InputStreamReader(
                    assetManager.open("area.json")))
            var b = true
            while (b) {
                val line = bf.readLine()
                if (line != null) {
                    stringBuilder.append(line)
                } else {
                    b = false
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

    private fun setGaryOrblue(rb: DrawableCenterButton, check: Boolean, name: String) {
        if (!TextUtils.isEmpty(name)) rb.text = name
        if (check) {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(activity, R.drawable.blue_arrow), null)
            rb.textColor = ContextCompat.getColor(activity, R.color.colorPrimary)

        } else {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(activity, R.drawable.gray_arrow), null)
            rb.textColor = ContextCompat.getColor(activity, R.color.MaterialGrey600)
        }
    }

    override fun onClick(v: View) {
        when (v) {
            cocalRb1 -> {
                Log.i("coalPop","rb1 前")
                if (!sortPop.isShowing) {

                    sortPop.showPopupWindow(radioGroup1)
                } else {
                    radioGroup1.clearCheck()
                }
                Log.i("coalPop","rb1 后")
            }
            cocalRb2 -> {
                if (!coalPop.isShowing) {
                    coalPop.showPopupWindow(radioGroup1)
                } else {
                    radioGroup1.clearCheck()
                }
            }
            cocalRb3 -> {
                if (!arePop.isShowing) {
                    arePop.showPopupWindow(radioGroup1)
                } else {
                    radioGroup1.clearCheck()
                }


            }
        }
    }


    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 1
        refreshState = com.szw.framelibrary.config.Constants.RefreshState.STATE_REFRESH
        iniData()

    }

    override fun onLoadMoreRequested() {
        refreshState = com.szw.framelibrary.config.Constants.RefreshState.STATE_LOAD_MORE
        iniData()
    }

    private fun iniData() {
        DataCtrlClass.coalEnquiryData(context, currentPage, coalVarietyId, provinceId, cityId, sortType) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == com.szw.framelibrary.config.Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it)
                } else {
                    mAdapter.addData(it)

                }
                if (it.isNotEmpty()) {
                    mAdapter.loadMoreComplete()
                    currentPage++
                } else {
                    mAdapter.loadMoreEnd()
                }
            } else {
                mAdapter.loadMoreFail()
            }
        }
    }


    companion object {
        fun newInstance(): CoalFragment {
            val bundle = Bundle()
            val fragment = CoalFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
