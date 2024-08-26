/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.shopkeeperselfdelivery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fststep.myshop.R
import com.fststep.myshop.shopkeeperselfdelivery.model.OrderItemInfo

/**
 * Created by Jay Kulshreshtha on 28/05/21.
 */
class OrderItemListAdapter(var context: Context, private val dataSet: ArrayList<OrderItemInfo>) :
    RecyclerView.Adapter<OrderItemListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvSerialNumber: AppCompatTextView = view.findViewById(R.id.tvSerialNumber)
        var tvItemName: AppCompatTextView = view.findViewById(R.id.tvItemName)
        var tvItemWeight: AppCompatTextView = view.findViewById(R.id.tvItemWeight)
        var tvItemNumber: AppCompatTextView = view.findViewById(R.id.tvItemNumber)
        var tvItemAmount: AppCompatTextView = view.findViewById(R.id.tvItemAmount)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_order_item_list, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvSerialNumber.text = (position + 1).toString() + "."
        viewHolder.tvItemName.text = dataSet[position].itemName
        viewHolder.tvItemWeight.text = dataSet[position].itemWeight + "gm"
        viewHolder.tvItemNumber.text = dataSet[position].itemNumber + "pp"
        viewHolder.tvItemAmount.text = (dataSet[position].itemAmount).toString() + "/-"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
