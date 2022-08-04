package com.example.testactivityresultapp

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.TakePicturePreview
import androidx.activity.result.launch
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testactivityresultapp.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding: FragmentCameraBinding
        get() = _binding!!

    private val cameraPermission = registerForActivityResult(RequestPermission()) { granted ->
        when {
            granted -> {
                snapShot.launch()
            }
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showNotAllowedDialog()
            }
            else -> {
                showToast(R.string.access_denied_toast)
            }
        }
    }

    private val snapShot = registerForActivityResult(TakePicturePreview()) { image ->
        if (image != null) {
            binding.apply {
                setImage(image, snapShotImageView)
                setVisibility(true)
            }
        } else {
            showToast(R.string.something_wrong_toast)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            cameraButton.setOnClickListener {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    showExplainDialog()
                } else {
                    cameraPermission.launch(Manifest.permission.CAMERA)
                }
            }

            closeButton.setOnClickListener {
                setVisibility(false)
            }

            setFragmentResultListener(EXPLAIN_KEY) { _, bundle ->
                val isAllowAfterExplain = bundle.getBoolean(RESULT_KEY)
                if (isAllowAfterExplain) {
                    cameraPermission.launch(Manifest.permission.CAMERA)
                }
            }

            setFragmentResultListener(NOT_ALLOWED_KEY) { _, bundle ->
                val isOpenSettings = bundle.getBoolean(RESULT_KEY)
                if (isOpenSettings) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.fromParts("package", requireContext().packageName, null))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setImage(image: Bitmap, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(image)
            .apply(RequestOptions())
            .into(imageView)
    }

    private fun showToast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun setVisibility(isVisible: Boolean) {
        binding.apply {
            cameraButton.isVisible = !isVisible
            closeButton.isVisible = isVisible
            snapShotImageView.isVisible = isVisible
        }
    }

    private fun showNotAllowedDialog() {
        DialogNotAllowedFragment().show(parentFragmentManager, DialogNotAllowedFragment.TAG)
    }

    private fun showExplainDialog() {
        DialogExplainFragment().show(parentFragmentManager, DialogExplainFragment.TAG)
    }

    companion object {
        const val EXPLAIN_KEY = "explain key"
        const val NOT_ALLOWED_KEY = "not allowed key"
        const val RESULT_KEY = "result key"
    }

}