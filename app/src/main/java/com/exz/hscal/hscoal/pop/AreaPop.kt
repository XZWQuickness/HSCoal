package com.exz.hscal.hscoal.pop

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.AreaOneAdapter
import com.exz.hscal.hscoal.adapter.AreaTwoAdapter
import com.exz.hscal.hscoal.bean.AreaBean
import com.szw.framelibrary.utils.RecycleViewDivider
import kotlinx.android.synthetic.main.pop_area.view.*
import razerdp.basepopup.BasePopupWindow

/**
 * Created by pc on 2017/12/6.
 */

class AreaPop(context: Context, listener: (name: String, povinceId: String,cityId:String, check: Boolean) -> Unit) : BasePopupWindow(context) {
    private lateinit var inflate: View
    private var mOneAdapter: AreaOneAdapter
    private lateinit var mTwoAdapter: AreaTwoAdapter
    private var linearLayoutManager: LinearLayoutManager
    private  var entity=AreaBean()

    var data = ArrayList<AreaBean>()
        set(value) {
            field = value
            var dataCities=  ArrayList<AreaBean.CitiesBean>()
            var cities=AreaBean.CitiesBean()
            cities.isCheck=true
            cities.areaName="不限"
            dataCities.add(cities)
            var mAreaBean = AreaBean()
            mAreaBean.areaId = ""
            mAreaBean.areaName = "不限"
            mAreaBean.isCheck = true
            mAreaBean.cities = ArrayList<AreaBean.CitiesBean>()
            value.add(0, mAreaBean)
            mOneAdapter.setNewData(value)
        }

    init {
        popupWindow.isClippingEnabled = false
        // 1级分类
        mOneAdapter = AreaOneAdapter()
        linearLayoutManager = LinearLayoutManager(context)
        inflate.mRecyclerViewOne.layoutManager = linearLayoutManager
        mOneAdapter.bindToRecyclerView(inflate.mRecyclerViewOne)
        inflate.mRecyclerViewOne.addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.White)))
        inflate.mRecyclerViewOne.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                entity=mOneAdapter.data.get(position)
                mOneAdapter.data.forEach {
                    it.isCheck = false
                }
                moveToPosition(linearLayoutManager, inflate.mRecyclerViewOne, position)
                if(mOneAdapter.data.get(position).cities !=null) {
                    var dataTow = mOneAdapter.data.get(position).cities as ArrayList<AreaBean.CitiesBean>
                    if (dataTow.size > 0 && !dataTow.get(0).areaName.equals("不限")) {
                        dataTow.add(0, AreaBean.CitiesBean())
                    }
                }
                mTwoAdapter.setNewData(mOneAdapter.data.get(position).cities)
                mTwoAdapter.loadMoreEnd()
                if (position < 1) {
                    mOneAdapter.data.forEach {
                        it.isCheck = false
                        it.cities.forEach { it.isCheck = false }
                    }

                    listener.invoke("区域","","",false)
                }
                mOneAdapter.data.get(position).isCheck = true
                mOneAdapter.notifyDataSetChanged()

            }
        })

        // 2级分类
        mTwoAdapter = AreaTwoAdapter()
        linearLayoutManager = LinearLayoutManager(context)
        inflate.mRecyclerViewTwo.layoutManager = LinearLayoutManager(context)
        inflate.mRecyclerViewTwo.addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.app_bg)))
        mTwoAdapter.bindToRecyclerView(inflate.mRecyclerViewTwo)

        inflate.mRecyclerViewTwo.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

                mOneAdapter.data.forEach {
                    it.isCheck = false
                    it.cities.forEach { it.isCheck = false }
                }
                mTwoAdapter.data.get(position).isCheck = true
                mTwoAdapter.notifyDataSetChanged()
                var cityEntity=mTwoAdapter.data.get(position)
                if(position>0){
                    listener.invoke(mTwoAdapter.data.get(position).areaName,entity.areaId,cityEntity.areaId,true)
                }else{
                    listener.invoke(if(entity.areaName.equals("不限")) "区域" else  entity.areaName,entity.areaId,cityEntity.areaId,true)
                }
            }
        })
    }


    override fun getClickToDismissView(): View = popupWindowView

    override fun onCreatePopupView(): View? {
        inflate = View.inflate(context, R.layout.pop_area, null)
        return inflate
    }

    override fun initAnimaView(): View = findViewById(R.id.ll_Animation)

    override fun initShowAnimation(): Animation {
        val shakeAnimate = AnimationUtils.loadAnimation(context, R.anim.translate_show_start)
        return shakeAnimate
    }

//    override fun initExitAnimation(): Animation {
//        val shakeAnimate = AnimationUtils.loadAnimation(context, R.anim.translate_show_exit)
//        return shakeAnimate
//    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param mRecyclerView  当前的RecyclerView
     * @param n  要跳转的位置
     */
    fun moveToPosition(manager: LinearLayoutManager, mRecyclerView: RecyclerView,
                       n: Int) {

        val firstItem = manager.findFirstVisibleItemPosition()
        val lastItem = manager.findLastVisibleItemPosition()
        when {
            n <= firstItem -> mRecyclerView.scrollToPosition(n)
            n <= lastItem -> {
                val top = mRecyclerView.getChildAt(n - firstItem).top
                mRecyclerView.scrollBy(0, top)
            }
            else -> mRecyclerView.scrollToPosition(n)
        }
    }


}
