package com.fststep.nxt2me.core.data.models

data class CategoryResponse (
    val timestamp: String? = null,
    val data: Data? = null,
    val errors: Any? = null
)

data class Data (
    val categoryTypeMappingList: List<CategoryTypeMappingList>? = null,
    val categoryList: List<CategoryList>? = null
)

data class CategoryList (
    val id: Long? = null,
    val categoryName: String? = null,
    val subCategoryList: List<SubCategoryList>? = null
)

data class SubCategoryList (
    val id: Long? = null,
    val subCategoryName: String? = null,
    val categoryType: CategoryTypeClass? = null
)

data class CategoryTypeClass (
    val id: Long? = null,
    val categoryType: CategoryTypeEnum? = null,
    val categoryCode: CategoryCode? = null
)

enum class CategoryCode {
    B,
    D,
    G,
    P
}

enum class CategoryTypeEnum {
    Booking,
    Delivery,
    Good,
    PhoneNumber
}

data class CategoryTypeMappingList (
    val id: Long? = null,
    val catTypeOptionName: CatTypeOptionName? = null,
    val categoryType: CategoryTypeClass? = null
)

enum class CatTypeOptionName {
    Both,
    Home,
    Shop
}
