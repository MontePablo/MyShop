/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.menu.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fststep.myshop.R
import com.fststep.myshop.core.data.Constants
import com.fststep.myshop.core.util.Utility
import com.fststep.myshop.core.view.CommonDialogs
import com.fststep.myshop.core.view.ImagePickerDialog
import com.fststep.myshop.databinding.ActivityAddBookingServiceBinding
import com.fststep.myshop.menu.adapter.BookingServiceAdapter
import com.fststep.myshop.menu.helper.MenuEditListener
import com.fststep.myshop.menu.model.Product
import com.fststep.myshop.menu.model.ProductMenuMode
import com.fststep.myshop.registration.model.ServiceType
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.ArrayList

/**
 * Created by Shubham Singh on 13/06/21.
 */
@AndroidEntryPoint
class AddBookingServicesActivity : AppCompatActivity(), MenuEditListener {

    private lateinit var mBinding: ActivityAddBookingServiceBinding

    private var products: ArrayList<Product> = arrayListOf()

    private lateinit var adapter: BookingServiceAdapter

    private var currentPhotoPath: String = ""

    private var editModeImageSelectionCallback: ((path: String) -> Unit)? = null

    private val commonDialogs = CommonDialogs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_booking_service)

        mBinding.toolbar.btBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.etMarketPrice.doOnTextChanged { text, start, before, count ->
            updateTotalPrice(text.toString().toDoubleOrNull() ?: 0.0, mBinding.etDiscount.text.toString().toIntOrNull() ?: 0)
        }

        mBinding.etDiscount.doOnTextChanged { text, start, before, count ->
            updateTotalPrice(mBinding.etMarketPrice.text.toString().toDoubleOrNull() ?: 0.0, text.toString().toIntOrNull() ?: 0)
        }

        mBinding.btAddMenu.setOnClickListener {
            if (isValid()) {
                addProductToMenu()
            }
        }

        mBinding.btAddMore.setOnClickListener {
            addMoreProducts()
        }

        mBinding.ivCamera.setOnClickListener {
            showImagePicker()
        }

        mBinding.ivClearImage.setOnClickListener {
            clearSelectedImage()
        }

        mBinding.buttonSave.setOnClickListener {
            onBackPressed()
        }

        mBinding.buttonSave.setOnClickListener {
            if (products.isEmpty()) {
                setResult(RESULT_CANCELED)
                finish()
            } else {
                val intent = Intent()
                intent.putParcelableArrayListExtra(Constants.KEY_NEW_MENU_ITEMS, products)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private val getImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            try {
                activityResult?.data?.data?.let {
                    val res = Utility.savefile(it, currentPhotoPath, contentResolver)
                    if (res) {
                        Utility.galleryAddPic(currentPhotoPath, this)
                        setPic()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            currentPhotoPath = ""
        }
    }

    private val getImageLauncherEditMode = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            try {
                activityResult?.data?.data?.let {
                    val res = Utility.savefile(it, currentPhotoPath, contentResolver)
                    if (res) {
                        Utility.galleryAddPic(currentPhotoPath, this)
                        setPic(true)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            currentPhotoPath = ""
        }
    }

    private val getImageCameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            try {
                Utility.galleryAddPic(currentPhotoPath, this)
                setPic()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            currentPhotoPath = ""
        }
    }

    private val getImageCameraLauncherEditMode = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            try {
                Utility.galleryAddPic(currentPhotoPath, this)
                setPic(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            currentPhotoPath = ""
        }
    }

    private fun pickImageIntent(isEditMode: Boolean = false) {
        Intent().also { galleryIntent ->
            // Create the File where the photo should go
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                null
            }

            // Continue only if the File was successfully created
            photoFile?.also {
                galleryIntent.type = "image/*"
                galleryIntent.action = Intent.ACTION_GET_CONTENT
                if (isEditMode) {
                    getImageLauncherEditMode.launch(Intent.createChooser(galleryIntent, "Select Picture"))
                } else {
                    getImageLauncher.launch(Intent.createChooser(galleryIntent, "Select Picture"))
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent(isEditMode: Boolean) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.fststep.myshop.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    if (isEditMode) {
                        getImageCameraLauncherEditMode.launch(takePictureIntent)
                    } else {
                        getImageCameraLauncher.launch(takePictureIntent)
                    }
                }
            }
        }
    }

    private fun setPic(isEditMode: Boolean = false) {
        if (currentPhotoPath.isEmpty()) return
        if (isEditMode) {
            editModeImageSelectionCallback?.invoke(currentPhotoPath)
        } else {
            setSelectedImage(currentPhotoPath)
        }
    }

    private fun setSelectedImage(path: String) {
        if (path.isEmpty()) return
        Glide.with(this)
            .load(File(path))
            .into(mBinding.ivSelectedImage)
        mBinding.layourSelectedImage.visibility = View.VISIBLE
        mBinding.ivCamera.visibility = View.GONE
    }

    private fun clearSelectedImage() {
        currentPhotoPath = ""
        mBinding.ivSelectedImage.setImageResource(0)
        mBinding.layourSelectedImage.visibility = View.GONE
        mBinding.ivCamera.visibility = View.VISIBLE
    }

    private fun isValid(): Boolean {
        cleanError()
        var result = true
        when {
            currentPhotoPath.isEmpty() -> {
                result = false
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show()
            }
            mBinding.etProductName.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlProductName.error = getString(R.string.lbl_required)
            }
            mBinding.etMarketPrice.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlMarketPrice.error = getString(R.string.lbl_required)
            }
            mBinding.etMarketPrice.text?.trim().toString().toDouble() <= 0.0 -> {
                result = false
                mBinding.tlMarketPrice.error = getString(R.string.lbl_invalid)
            }
            mBinding.etTotalCost.text?.trim().toString().toDouble() <= 0.0 -> {
                result = false
                mBinding.tlTotalCost.error = getString(R.string.lbl_invalid)
            }
        }

        return result
    }

    private fun cleanError() {
        mBinding.tlProductName.error = null
        mBinding.tlMarketPrice.error = null
        mBinding.tlTotalCost.error = null
    }

    private fun addProductToMenu() {
        if (!::adapter.isInitialized) {
            adapter = BookingServiceAdapter(products, this)
            val layoutManager = LinearLayoutManager(this)
            mBinding.rvMenu.layoutManager = layoutManager
            mBinding.rvMenu.setHasFixedSize(false)
            mBinding.rvMenu.setItemViewCacheSize(20)
            mBinding.rvMenu.adapter = adapter
        }

        if (mBinding.rvMenu.visibility == View.GONE) {
            mBinding.rvMenu.visibility = View.VISIBLE
        }
        val newProduct = Product(
            id = products.size,
            name = mBinding.etProductName.text?.trim().toString(),
            description = null,
            weight = null,
            unit = null,
            imagePath = currentPhotoPath,
            marketPrice = mBinding.etMarketPrice.text?.trim().toString().toDoubleOrNull() ?: 0.0,
            discountPercentage = mBinding.etDiscount.text?.trim().toString().toIntOrNull() ?: 0,
            totalCost = mBinding.etTotalCost.text?.trim().toString().toDoubleOrNull() ?: 0.0,
            type = ServiceType.BOOKING.value
        )

        products.add(newProduct)
        adapter.notifyItemInserted(products.lastIndex)
        mBinding.cardProductForm.visibility = View.GONE
    }

    private fun isUnsavedData(): Boolean {
        return products.isNotEmpty() || mBinding.etProductName.text?.trim().toString().isNotEmpty() ||
            currentPhotoPath.isNotEmpty() ||
            mBinding.etMarketPrice.text?.trim().toString().isNotEmpty() ||
            mBinding.etDiscount.text?.trim().toString().isNotEmpty() ||
            mBinding.etTotalCost.text?.trim().toString().isNotEmpty()
    }

    private fun addMoreProducts() {
        mBinding.etProductName.text?.clear()
        mBinding.etMarketPrice.text?.clear()
        mBinding.etDiscount.text?.clear()
        mBinding.etTotalCost.text?.clear()
        clearSelectedImage()

        mBinding.cardProductForm.visibility = View.VISIBLE
    }

    private fun updateTotalPrice(marketPrice: Double, discount: Int) {

        val discountedPrice: Double = marketPrice - (marketPrice * discount / 100)

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        mBinding.etTotalCost.setText(df.format(discountedPrice))
    }

    override fun onItemRemove(index: Int) {
        val delDialog = DeleteConfirmationDialog.newInstance()
        delDialog.setPositiveListener {
            if (::adapter.isInitialized) {
                products.removeAt(index)
                adapter.notifyItemRemoved(index)

                if (products.isEmpty()) {
                    mBinding.rvMenu.visibility = View.GONE
                    addMoreProducts()
                }
            }
            delDialog.dismiss()
        }
        delDialog.show(supportFragmentManager, DeleteConfirmationDialog.TAG)
    }

    override fun onItemEdit(index: Int) {
        if (!::adapter.isInitialized) return
        products[index].mode = ProductMenuMode.MODE_EDIT
        adapter.notifyItemChanged(index)

        mBinding.cardProductForm.visibility = View.GONE
    }

    override fun onItemUpdate(index: Int, product: Product) {
        if (!::adapter.isInitialized) return

        products[index] = product
        adapter.notifyItemChanged(index)
    }

    override fun showImagePicker(isEditMode: Boolean, setSelectedImage: ((path: String) -> Unit)?) {
        this.editModeImageSelectionCallback = setSelectedImage
        val dialog = ImagePickerDialog.newInstance(
            ::pickImageIntent,
            ::dispatchTakePictureIntent,
            isEditMode
        )
        dialog.show(supportFragmentManager, ImagePickerDialog.TAG)
    }

    override fun onBackPressed() {
        if (isUnsavedData()) {
            commonDialogs.showDialogWithTwoButtons(
                this, R.string.sure_title, R.string.lbl_save_confirmation, R.string.lbl_dialog_ok, R.string.lbl_dialog_cancel,
                { _, _ ->
                    setResult(RESULT_CANCELED)
                    super.onBackPressed()
                },
                { dialog, _ ->
                    dialog.dismiss()
                }
            )
        } else {
            setResult(RESULT_CANCELED)
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        commonDialogs.removeDialog()
    }

    override fun onPause() {
        super.onPause()
        commonDialogs.removeDialog()
    }
}
