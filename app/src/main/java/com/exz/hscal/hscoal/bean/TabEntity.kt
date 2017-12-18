package com.exz.hscal.hscoal.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Created by pc on 2017/12/4.
 */

class TabEntity(var title: String, private var selectedIcon: Int, private var unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabTitle(): String = title

    override fun getTabSelectedIcon(): Int = selectedIcon

    override fun getTabUnselectedIcon(): Int = unSelectedIcon
}
