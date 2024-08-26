/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.deliveryboy.view

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityHireDeliveryBoyBinding
import com.fststep.myshop.deliveryboy.model.DeliveryBoyInfo
import com.fststep.myshop.deliveryboy.view.adapter.DeliveryBoyListAdapter

/**
 * Created by Jay Kulshreshtha on 15/05/21.
 */
class HireDeliveryBoyActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityHireDeliveryBoyBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var deliveryBoyListAdapter: DeliveryBoyListAdapter
    private lateinit var deliveryBoyList: MutableList<DeliveryBoyInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_hire_delivery_boy)

        linearLayoutManager = LinearLayoutManager(this)
        mBinding.rvDeliveyBoyList.layoutManager = linearLayoutManager
        deliveryBoyList = ArrayList()
        dummyData()
        deliveryBoyListAdapter = DeliveryBoyListAdapter(this, deliveryBoyList as ArrayList<DeliveryBoyInfo>)
        mBinding.rvDeliveyBoyList.adapter = deliveryBoyListAdapter

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun dummyData() {
        deliveryBoyList.add(DeliveryBoyInfo("Ravi Shankar", 1, 1, 2))
        deliveryBoyList.add(DeliveryBoyInfo("Ravi Shankar", 2, 3, 4))
        deliveryBoyList.add(DeliveryBoyInfo("Ravi Shankar", 3, 5, 6))
        deliveryBoyList.add(DeliveryBoyInfo("Ravi Shankar", 4, 7, 8))
        deliveryBoyList.add(DeliveryBoyInfo("Ravi Shankar", 5, 9, 10))
    }

    fun showErrorDialog(errorTitle: String, errorDesc: String, isCancelable: Boolean) {
        val dialog = Dialog(this)
        dialog.setCancelable(isCancelable)
        dialog.setContentView(R.layout.dialog_error)

        val tvErrorTitle = dialog.findViewById(R.id.tvErrorTitle) as TextView
        tvErrorTitle.text = errorTitle

        val tvErrorDesc = dialog.findViewById(R.id.tvErrorDesc) as TextView
        tvErrorDesc.text = errorDesc

        val buttonOK = dialog.findViewById(R.id.buttonOK) as Button
        buttonOK.setOnClickListener {
            dialog.dismiss()
        }
    }
}
