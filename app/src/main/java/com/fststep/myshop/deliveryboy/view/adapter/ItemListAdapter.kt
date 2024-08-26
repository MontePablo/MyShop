/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.deliveryboy.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fststep.myshop.R
import com.fststep.myshop.deliveryboy.model.ItemListInfo

/**
 * Created by Jay Kulshreshtha on 22/05/21.
 */
class ItemListAdapter(var context: Context, private val dataSet: ArrayList<ItemListInfo>) :
    RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvItemName: AppCompatTextView = view.findViewById(R.id.tvItemName)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_item_list, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if (dataSet[position].deliveryStatus.contentEquals("not delivered", true)) {
            viewHolder.tvItemName.text = String.format(
                "%d. %s %s", position + 1,
                dataSet[position].itemName, dataSet[position].deliveryStatus
            )
            viewHolder.tvItemName.setTextColor(Color.parseColor("#f05a28"))
        } else {
            viewHolder.tvItemName.text = String.format(
                "%d. %s %s", position + 1,
                dataSet[position].itemName, dataSet[position].deliveryStatus
            )
            viewHolder.tvItemName.setTextColor(Color.parseColor("#00a550"))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
