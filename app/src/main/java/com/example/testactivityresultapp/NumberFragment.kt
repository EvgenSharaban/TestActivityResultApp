package com.example.testactivityresultapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.testactivityresultapp.databinding.FragmentNumberBinding

class NumberFragment : Fragment() {

    private var _binding: FragmentNumberBinding? = null
    private val binding: FragmentNumberBinding
        get() = _binding!!

    private val secondActivity = registerForActivityResult(SecondActivityContract()) { result ->
        binding.apply {
            if (result != null) {
                resultText.text = result.toString()
                noResultText.isVisible = false
                resultText.isVisible = true
                buttonGetResult.setText(R.string.get_result_again_text)
            } else {
                resultText.isVisible = false
                noResultText.isVisible = true
                buttonGetResult.setText(R.string.get_result_text)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNumberBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonGetResult.setOnClickListener {
            secondActivity.launch()
        }
    }

}