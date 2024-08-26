/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.helper

import com.fststep.myshop.registration.model.Category
import com.fststep.myshop.registration.model.ServiceType
import com.fststep.myshop.registration.model.SubCategory

/**
 * Created by Shubham Singh on 16/05/21.
 */
object CategoryList {

    private fun getCategoriesData(): ArrayList<Category> {
        return arrayListOf(
            Category(
                0,
                "Select an Option...",
                arrayListOf(
                    SubCategory(
                        0,
                        "Select an Option...",
                        ServiceType.GOODS_DELIVERY
                    )
                )
            ),
            Category(
                1,
                "Daily Essentials",
                arrayListOf(
                    SubCategory(
                        0,
                        "Select an Option...",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        1,
                        "Grocery",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        2,
                        "Veg & Fruits",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        3,
                        "Dairy",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        4,
                        "Meat & Fish",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        5,
                        "Beer & Wine",
                        ServiceType.WINE
                    ),
                    SubCategory(
                        6,
                        "Pharmacy",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        7,
                        "Laundry",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        8,
                        "Fresh from Farm",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        9,
                        "Bakery & Sweets",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        10,
                        "Book a Ride",
                        ServiceType.NONE
                    ),
                )
            ),
            Category(
                2,
                "Beauty Care",
                arrayListOf(
                    SubCategory(
                        0,
                        "Select an Option...",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        1,
                        "Salon",
                        ServiceType.BOOKING
                    ),
                    SubCategory(
                        2,
                        "Beauty Parlor",
                        ServiceType.BOOKING
                    ),
                    SubCategory(
                        3,
                        "Unisex Salon",
                        ServiceType.BOOKING
                    ),
                    SubCategory(
                        4,
                        "Massage Parlour",
                        ServiceType.BOOKING
                    ),
                    SubCategory(
                        5,
                        "Spa",
                        ServiceType.BOOKING
                    ),
                    SubCategory(
                        6,
                        "Aurveda",
                        ServiceType.BOOKING
                    ),
                    SubCategory(
                        7,
                        "Boutique",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        8,
                        "Cosmetic",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        9,
                        "Salon at Home",
                        ServiceType.BOOKING
                    ),
                    SubCategory(
                        10,
                        "Massage at Home",
                        ServiceType.BOOKING
                    )
                )
            ),
            Category(
                3,
                "Health Care",
                arrayListOf(
                    SubCategory(
                        0,
                        "Select an Option...",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        1,
                        "Doctor",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        2,
                        "Nurse",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        3,
                        "Dietitian",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        4,
                        "Gym",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        5,
                        "Health Supplements",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        6,
                        "Physiotherapist",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        7,
                        "Zuma Class",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        8,
                        "Yoga Class",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        9,
                        "Dance Class",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        10,
                        "Martial Art Coach",
                        ServiceType.PHONE
                    ),
                )
            ),
            Category(
                4,
                "Miscellaneous",
                arrayListOf(
                    SubCategory(
                        0,
                        "Select an Option...",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        1,
                        "Gifts and Article",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        2,
                        "Flower Shop",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        3,
                        "Ice Cream Parlour",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        4,
                        "Baby Care",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        5,
                        "Daycare",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        6,
                        "Electronic Shop",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        7,
                        "Movers & Packer",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        8,
                        "Pet Shop",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        9,
                        "Garage",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        10,
                        "Car Accessories",
                        ServiceType.GOODS_DELIVERY
                    ),
                )
            ),
            Category(
                5,
                "Professional service",
                arrayListOf(
                    SubCategory(
                        0,
                        "Select an Option...",
                        ServiceType.GOODS_DELIVERY
                    ),
                    SubCategory(
                        1,
                        "Doctor",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        2,
                        "Nurse",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        3,
                        "Physiotherapist",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        4,
                        "Tailor",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        5,
                        "Computer Class",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        6,
                        "Cooking Class",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        7,
                        "Coaching Class",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        8,
                        "Singing Class",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        9,
                        "Personality Development",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        10,
                        "Drawing Class",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        11,
                        "Astrologer",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        12,
                        "Tarot Card Reader",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        13,
                        "Interior Designer",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        14,
                        "Charter Accountant",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        15,
                        "Insurance Broker",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        16,
                        "Investment Advisor",
                        ServiceType.PHONE
                    ),
                    SubCategory(
                        17,
                        "Real Estate",
                        ServiceType.PHONE
                    )
                )
            )
        )
    }

    fun getCategoryList(): List<String> {
        return getCategoriesData().map { it.categoryName }
    }

    fun getSubCategoryList(position: Int = 0): List<String> {
        return getCategoriesData()[position].subCategories.map { it.subCategoryName }
    }

    fun getServiceType(businessCatPos: Int, subCatPos: Int): ServiceType {
        return getCategoriesData()[businessCatPos].subCategories[subCatPos].serviceType
    }

    fun getBusinessCategory(businessCatPos: Int): String {
        return getCategoriesData()[businessCatPos].categoryName
    }

    fun getTypeOfBusiness(businessCatPos: Int, subCatPos: Int): String {
        return getCategoriesData()[businessCatPos].subCategories[subCatPos].subCategoryName
    }
}
