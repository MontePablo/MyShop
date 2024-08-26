/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fststep.myshop.bookings.SlotActivity
import com.fststep.myshop.core.data.Constants
import com.fststep.myshop.core.util.Utility
import com.fststep.myshop.core.view.GenerateCouponsActivity
import com.fststep.myshop.core.view.ImagePickerDialog
import com.fststep.myshop.databinding.FragmentMenuBinding
import com.fststep.myshop.menu.adapter.BaseProductAdapter
import com.fststep.myshop.menu.adapter.BookingServiceAdapter
import com.fststep.myshop.menu.adapter.GoodsMenuAdapter
import com.fststep.myshop.menu.adapter.PhoneServiceAdapter
import com.fststep.myshop.menu.helper.MenuEditListener
import com.fststep.myshop.menu.model.Product
import com.fststep.myshop.menu.model.ProductMenuMode
import com.fststep.myshop.menu.view.AddBookingServicesActivity
import com.fststep.myshop.menu.view.AddPhoneServicesActivity
import com.fststep.myshop.menu.view.CreateMenuActivity
import com.fststep.myshop.menu.view.DeleteConfirmationDialog
import com.fststep.myshop.registration.model.ServiceType
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.ArrayList

class MenuFragment : Fragment(), MenuEditListener {

    private lateinit var mBinding: FragmentMenuBinding

    private var products: ArrayList<Product> = arrayListOf()

    private lateinit var adapter: BaseProductAdapter

    private var currentPhotoPath: String = ""

    private var editModeImageSelectionCallback: ((path: String) -> Unit)? = null

    private var serviceType: ServiceType? = null

    companion object {
        fun newInstance(serviceType: ServiceType): MenuFragment {
            val frag = MenuFragment()
            frag.serviceType = serviceType
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMenuBinding.inflate(inflater)
        serviceType?.let {
            mBinding.serviceType = it
        }
        return mBinding.root
    }

    private val createMenuLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            val data = activityResult.data?.getParcelableArrayListExtra<Product>(Constants.KEY_NEW_MENU_ITEMS)
            data?.let {
                val newProducts: ArrayList<Product> = it as ArrayList<Product>
                updateProductList(newProducts)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btAddItem.setOnClickListener {
            startCreateMenuActivity()
        }

        mBinding.btAddService.setOnClickListener {
            startAddBookingServiceActivity()
        }

        mBinding.btAddServicePhone.setOnClickListener {
            startAddPhoneServiceActivity()
        }

        mBinding.btSlotsBooking.setOnClickListener {
            startActivity(Intent(requireActivity(), SlotActivity::class.java))
        }

        initAdapter()

        val layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvSavedProducts.layoutManager = layoutManager
        mBinding.rvSavedProducts.setHasFixedSize(false)
        mBinding.rvSavedProducts.setItemViewCacheSize(20)
        mBinding.rvSavedProducts.adapter = adapter

        mBinding.products = products

        mBinding.btGenerateCouponBooking.setOnClickListener { goToGenerateCoupon() }
        mBinding.btGenerateCouponGoods.setOnClickListener { goToGenerateCoupon() }
        mBinding.btGenerateCouponPhone.setOnClickListener { goToGenerateCoupon() }
    }

    private fun goToGenerateCoupon() {
        startActivity(Intent(requireActivity(), GenerateCouponsActivity::class.java))
    }

    private fun initAdapter() {
        when (serviceType) {
            ServiceType.GOODS_DELIVERY -> {
                adapter = GoodsMenuAdapter(products, this)
            }

            ServiceType.PHONE -> {
                adapter = PhoneServiceAdapter(products, this)
            }

            ServiceType.BOOKING -> {
                adapter = BookingServiceAdapter(products, this)
            }
            else -> adapter = GoodsMenuAdapter(products, this)
        }
    }

    private fun startCreateMenuActivity() {
        val intent = Intent(requireContext(), CreateMenuActivity::class.java)
        intent.putExtra(Constants.KEY_SERVICE_TYPE, serviceType?.value)
        createMenuLauncher.launch(intent)
    }

    private fun startAddPhoneServiceActivity() {
        val intent = Intent(requireContext(), AddPhoneServicesActivity::class.java)
        intent.putExtra(Constants.KEY_SERVICE_TYPE, serviceType?.value)
        createMenuLauncher.launch(intent)
    }

    private fun startAddBookingServiceActivity() {
        val intent = Intent(requireContext(), AddBookingServicesActivity::class.java)
        intent.putExtra(Constants.KEY_SERVICE_TYPE, serviceType?.value)
        createMenuLauncher.launch(intent)
    }

    private fun updateProductList(newProducts: ArrayList<Product>? = null) {
        if (!::adapter.isInitialized) return
        newProducts?.let { products.addAll(newProducts) }
        mBinding.products = products
        adapter.notifyDataSetChanged()
    }

    private val getImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            try {
                activityResult?.data?.data?.let {
                    activity?.contentResolver?.let { cr ->
                        val res = Utility.savefile(it, currentPhotoPath, cr)
                        if (res) {
                            Utility.galleryAddPic(currentPhotoPath, requireContext())
                            setPic()
                        }
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
        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            try {
                Utility.galleryAddPic(currentPhotoPath, requireContext())
                setPic()
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
                Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
                null
            }

            // Continue only if the File was successfully created
            photoFile?.also {
                galleryIntent.type = "image/*"
                galleryIntent.action = Intent.ACTION_GET_CONTENT
                getImageLauncher.launch(Intent.createChooser(galleryIntent, "Select Picture"))
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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
            activity?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "com.fststep.myshop.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        getImageCameraLauncher.launch(takePictureIntent)
                    }
                }
            }
        }
    }

    private fun setPic(isEditMode: Boolean = false) {
        if (currentPhotoPath.isEmpty()) return
        editModeImageSelectionCallback?.invoke(currentPhotoPath)
    }

    override fun onItemRemove(index: Int) {
        val delDialog = DeleteConfirmationDialog.newInstance()
        delDialog.setPositiveListener {
            if (::adapter.isInitialized) {
                products.removeAt(index)
                adapter.notifyItemRemoved(index)
                mBinding.products = products
            }
            delDialog.dismiss()
        }
        delDialog.show(childFragmentManager, DeleteConfirmationDialog.TAG)
    }

    override fun onItemEdit(index: Int) {
        if (!::adapter.isInitialized) return
        products[index].mode = ProductMenuMode.MODE_EDIT
        adapter.notifyItemChanged(index)
        mBinding.products = products
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
        dialog.show(childFragmentManager, ImagePickerDialog.TAG)
    }
}
