package com.exz.hscal.hscoal.module.demand

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSON
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.DemandAdapter
import com.exz.hscal.hscoal.bean.AreaBean
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.PopStairListBean
import com.exz.hscal.hscoal.pop.AreaPop
import com.exz.hscal.hscoal.pop.StairPop
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.view.DrawableCenterButton
import kotlinx.android.synthetic.main.fragment_steel.*
import org.jetbrains.anko.textColor
import razerdp.basepopup.BasePopupWindow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by pc on 2017/12/5.
 */

class CoalFragment : MyBaseFragment(), OnRefreshListener, View.OnClickListener {
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
        refreshLayout.autoRefresh()

    }

    private var data = ArrayList<CargoListBean>()
    private fun initRecycler() {
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        mAdapter = DemandAdapter()
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(context, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        rb1.setOnClickListener(this)
        rb2.setOnClickListener(this)
        rb3.setOnClickListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var entity = mAdapter.data.get(position)
                startActivity(Intent(activity, DemandCocalDetailAtivity::class.java).putExtra(DemandCocalDetailAtivity.Intent_Id, entity.id))
            }
        })
    }


    var sortData = ArrayList<PopStairListBean>()
    var coalData = ArrayList<PopStairListBean>()
    private fun initPop() {
        sortData.add(PopStairListBean("综合排序", true, "1"))
        sortData.add(PopStairListBean("价格由低到高", false, "1"))
        sortData.add(PopStairListBean("价格由高到低", false, "1"))
        sortData.add(PopStairListBean("最新上架", false, "1"))
        coalData.add(PopStairListBean("全部煤种", true, "1"))
        coalData.add(PopStairListBean("焦炭/焦粉/焦粒", false, "1"))
        coalData.add(PopStairListBean("炼焦煤", false, "1"))
        coalData.add(PopStairListBean("动力煤", false, "1"))
        sortPop = StairPop(activity, {
            if (it != null) {
                if (it.name.equals("综合排序")) {
                    setGaryOrblue(rb1, false, "综合排序")
                } else {
                    setGaryOrblue(rb1, true, it.name)
                }

            }
        })
        sortPop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }
        sortPop.data = sortData
        coalPop = StairPop(activity, {
            if (it != null) {
                if (it.name.equals("全部煤种")) {
                    setGaryOrblue(rb2, false, "全部煤种")
                } else {
                    setGaryOrblue(rb2, true, it.name)
                }

            }
        })
        coalPop.data = coalData
        coalPop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }
        arePop = AreaPop(context, { name, povinceId, cityId, check ->

            setGaryOrblue(rb3, check, name)
        })
        arePop.data = (JSON.parseArray(getJson(), AreaBean::class.java) as ArrayList<AreaBean>)
        arePop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
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
            rb1 -> {
                if (!sortPop.isShowing) {

                    sortPop.showPopupWindow(radioGroup)
                } else {
                    radioGroup.clearCheck()
                }
            }
            rb2 -> {
                if (!coalPop.isShowing) {
                    coalPop.showPopupWindow(radioGroup)
                } else {
                    radioGroup.clearCheck()
                }
            }
            rb3 -> {
                if (!arePop.isShowing) {
                    arePop.showPopupWindow(radioGroup)
                } else {
                    radioGroup.clearCheck()
                }
            }
        }
    }


    override fun onRefresh(refreshlayout: RefreshLayout?) {
        refreshLayout?.finishRefresh()
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
