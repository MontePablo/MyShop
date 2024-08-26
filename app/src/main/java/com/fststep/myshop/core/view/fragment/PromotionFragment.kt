/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fststep.myshop.databinding.FragmentPromotionBinding
import com.fststep.myshop.promotions.BigBannerAdvertisementActivity
import com.fststep.myshop.promotions.LaunchNewProductActivity
import com.fststep.myshop.promotions.OwnPromotionActivity
import com.fststep.myshop.promotions.SmallBannerAdvertisementActivity
import com.fststep.myshop.registration.model.ServiceType

/**
 * Created by Jay Kulshreshtha on 5/06/21.
 */
class PromotionFragment : Fragment() {

    private lateinit var mBinding: FragmentPromotionBinding
    private var serviceType: ServiceType? = null

    companion object {
        fun newInstance(serviceType: ServiceType): PromotionFragment {
            val frag = PromotionFragment()
            frag.serviceType = serviceType
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentPromotionBinding.inflate(layoutInflater)
        val promotion: View = mBinding.root

        mBinding.clBigBannerAdvertisement.setOnClickListener {
            val intent = Intent(requireContext(), BigBannerAdvertisementActivity::class.java)
            requireActivity().startActivity(intent)
        }

        mBinding.clSmallBannerAdvertisement.setOnClickListener {
            val intent = Intent(requireContext(), SmallBannerAdvertisementActivity::class.java)
            requireActivity().startActivity(intent)
        }

        mBinding.clLaunchNewProduct.setOnClickListener {
            val intent = Intent(requireContext(), LaunchNewProductActivity::class.java)
            requireActivity().startActivity(intent)
        }

        mBinding.clOwnPromotion.setOnClickListener {
            val intent = Intent(requireContext(), OwnPromotionActivity::class.java)
            requireActivity().startActivity(intent)
        }

        return promotion
    }
}
