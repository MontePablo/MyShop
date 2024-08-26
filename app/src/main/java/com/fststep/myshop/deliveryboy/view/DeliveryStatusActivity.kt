/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.deliveryboy.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityDeliveryStatusBinding
import com.fststep.myshop.deliveryboy.model.ItemListInfo
import com.fststep.myshop.deliveryboy.view.adapter.ItemListAdapter

/**
 * Created by Jay Kulshreshtha on 22/05/21.
 */
class DeliveryStatusActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDeliveryStatusBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemListAdapter: ItemListAdapter
    private lateinit var itemList: MutableList<ItemListInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_status)

        linearLayoutManager = LinearLayoutManager(this)
        mBinding.rvItemList.layoutManager = linearLayoutManager
        itemList = ArrayList()
        dummyData()
        itemListAdapter = ItemListAdapter(this, itemList as ArrayList<ItemListInfo>)
        mBinding.rvItemList.adapter = itemListAdapter

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun dummyData() {
        itemList.add(ItemListInfo("Item1", "not delivered"))
        itemList.add(ItemListInfo("Item2", "not delivered"))
        itemList.add(ItemListInfo("Item3", "delivered"))
        itemList.add(ItemListInfo("Item4", "not delivered"))
        itemList.add(ItemListInfo("Item5", "delivered"))
    }
}
