package com.example.mobileclient

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobileclient.databinding.FragmentImagePickerBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class ImagePickerFragment : Fragment() {

    private var _binding: FragmentImagePickerBinding? = null

    private val binding get() = _binding!!

    private lateinit var imageView: ImageView
    private lateinit var pickButton: Button
    private lateinit var photoButton: Button
    private lateinit var uploadButton: Button

    private val pickImage = 100
    private val cameraRequest = 1888
    private var imageUri: Uri? = null
    private val serverUrl: String = "http://10.0.2.2:8080"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        imageView = view.findViewById(R.id.picked_imageview)
        pickButton = view.findViewById(R.id.pick_button)
        photoButton = view.findViewById(R.id.capture_button)
        uploadButton = view.findViewById(R.id.upload_button)

        imageView.setImageResource(R.drawable.placeholder_image)

        uploadButton.setOnClickListener {
            val f = File(context?.cacheDir, "temp")
            f.createNewFile()

            val bitmap: Bitmap = imageView.drawable.toBitmap()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata = bos.toByteArray()

            val fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()

            val file = FileDataPart.from(f.absolutePath, name = "file")
            val (_, _, result) = Fuel.upload("$serverUrl/uploadFile/test")
                .add(file)
                .responseString()

            println(result)

            findNavController().navigate(R.id.action_imagePickerFragment_to_FirstFragment)
        }

        pickButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        photoButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
        if (requestCode == cameraRequest) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
        }
    }
}