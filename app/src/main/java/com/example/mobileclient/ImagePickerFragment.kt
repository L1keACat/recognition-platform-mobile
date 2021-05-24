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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobileclient.databinding.FragmentImagePickerBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class ImagePickerFragment : Fragment() {

    private var _binding: FragmentImagePickerBinding? = null

    private val binding get() = _binding!!

    val args: ImagePickerFragmentArgs by navArgs()

    private lateinit var imageView: ImageView
    private lateinit var pickButton: Button
    private lateinit var photoButton: Button
    private lateinit var uploadButton: Button
    private lateinit var historyButton: Button
    private lateinit var warningTextView: TextView

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
        historyButton = view.findViewById(R.id.history_button)
        warningTextView = view.findViewById(R.id.warning_textview)

        imageView.setImageResource(R.drawable.placeholder_image)

        uploadButton.setOnClickListener {
            val f = File(context?.cacheDir, "temp")
            f.createNewFile()

            val bitmap: Bitmap = imageView.drawable.toBitmap()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
            val bitmapdata = bos.toByteArray()

            val fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()

            val file = FileDataPart.from(f.absolutePath, name = "file")
            val (_, _, result) = Fuel.upload("$serverUrl/uploadFile/${args.username}")
                .add(file)
                .responseString()

            try {
                println(result.get())
                Toast.makeText(
                    context,
                    getString(R.string.success_decode_toast_text),
                    Toast.LENGTH_SHORT
                ).show()
                val action =
                    ImagePickerFragmentDirections.actionImagePickerFragmentToDetailsFragment(
                        result.get(),
                        args.username
                    )
                findNavController().navigate(action)
            } catch (e: Exception) {
                warningTextView.visibility = View.VISIBLE
                uploadButton.visibility = View.INVISIBLE
                println(e.message)
            }
        }

        pickButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        photoButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

        historyButton.setOnClickListener {
            val action =
                ImagePickerFragmentDirections.actionImagePickerFragmentToHistoryFragment(
                    args.username
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            warningTextView.visibility = View.INVISIBLE
            imageUri = data?.data
            imageView.setImageURI(imageUri)
            uploadButton.visibility = View.VISIBLE
        }
        if (requestCode == cameraRequest) {
            warningTextView.visibility = View.INVISIBLE
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
            uploadButton.visibility = View.VISIBLE
        }
    }
}