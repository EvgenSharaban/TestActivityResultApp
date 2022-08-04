package com.example.testactivityresultapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.testactivityresultapp.databinding.DialogNotAllowedBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogNotAllowedFragment : BottomSheetDialogFragment() {

    private var _binding: DialogNotAllowedBinding? = null
    private val binding: DialogNotAllowedBinding
        get() = _binding!!

    override fun getTheme() = R.style.BottomSheetTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNotAllowedBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.explainButton.setOnClickListener {
            setFragmentResult(
                CameraFragment.NOT_ALLOWED_KEY,
                bundleOf(CameraFragment.RESULT_KEY to true)
            )
            dismiss()
        }
    }

    companion object {
        const val TAG = "not allowed tag"
    }
}