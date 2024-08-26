/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.fststep.myshop.core.model.JwtTokenPayload
import com.fststep.myshop.core.view.CommonDialogs
import com.fststep.myshop.core.retrofit.State
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by Shubham Singh on 05/06/21.
 */
object Utility {

    fun getBitmapOptions(): BitmapFactory.Options {
        // Get the dimensions of the View
        val targetW: Int = 250
        val targetH: Int = 151

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = 1.coerceAtLeast((photoW / targetW).coerceAtMost(photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }
        return bmOptions
    }

    fun galleryAddPic(currentPhotoPath: String, context: Context) {
        if (currentPhotoPath.isEmpty()) return
        val file = File(currentPhotoPath)
        MediaScannerConnection.scanFile(
            context, arrayOf(file.toString()),
            null
        ) { path: String?, uri: Uri? ->
            Log.d("ExternalStorage", "Scanned $path:")
            Log.d("ExternalStorage", "-> uri=$uri")
        }
    }

    fun savefile(sourceuri: Uri, currentPhotoPath: String, contentResolver: ContentResolver): Boolean {
        if (currentPhotoPath.isEmpty()) return false
        var result = false
        val destinationFilename = currentPhotoPath
        var bis: BufferedInputStream? = null
        var bos: BufferedOutputStream? = null
        try {
            bis = BufferedInputStream(contentResolver.openInputStream(sourceuri))
            bos = BufferedOutputStream(FileOutputStream(destinationFilename, false))
            val buf = ByteArray(1024)
            bis.read(buf)
            do {
                bos.write(buf)
            } while (bis.read(buf) != -1)
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
            result = false
        } finally {
            try {
                bis?.close()
                bos?.close()
                return result
            } catch (e: IOException) {
                e.printStackTrace()
                return result
            }
        }
    }
    fun isFieldsEmpty(array: ArrayList<EditText>): Boolean {
        for(a in array){
            if(a.text!!.trim().isEmpty()){
                return true
            }
        }
        return false
    }
    fun performErrorState(context: AppCompatActivity, state: State.ErrorState, msg: String) {
        Log.i(context.localClassName, state.exception.errorMessage)
        Log.d("TAG","state.error"+state.exception.errorMessage + state.exception.errorCode)
        val dialogs= CommonDialogs()
        dialogs.showDialogWithOneButton(
            context,msg,state.exception.errorMessage,"OK",
            { dialog, _ ->
                dialog.dismiss()
            }
        )
    }
    fun provideTokenPayload(token:String): JwtTokenPayload {
        val p= token.split(".")[1]
        val payload= String(Base64.decode(p, Base64.DEFAULT))
        val gson= Gson()
        return gson.fromJson(payload, JwtTokenPayload::class.java)
    }

    fun disableFocus(arr:ArrayList<View>){
        for(view in arr){
            view.isFocusable=false
        }
    }
    fun enableFocus(arr:ArrayList<View>){
        for(view in arr){
            view.isFocusableInTouchMode=true
        }
    }
}
