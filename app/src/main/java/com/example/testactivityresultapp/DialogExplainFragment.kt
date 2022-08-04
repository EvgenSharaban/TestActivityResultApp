package com.example.testactivityresultapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.testactivityresultapp.databinding.DialogExplainBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogExplainFragment : BottomSheetDialogFragment() {

    private var _binding: DialogExplainBinding? = null
    private val binding: DialogExplainBinding
        get() = _binding!!

    override fun getTheme() = R.style.BottomSheetTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogExplainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.explainButton.setOnClickListener {
            setFragmentResult(
                CameraFragment.EXPLAIN_KEY,
                bundleOf(CameraFragment.RESULT_KEY to true)
            )
            dismiss()
        }
    }

    companion object {
        const val TAG = "explain tag"
    }
}