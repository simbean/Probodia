package com.piri.probodia.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.piri.probodia.R
import com.piri.probodia.databinding.FragmentAnalysisHemoglobinBinding
import com.piri.probodia.viewmodel.RecordAnalysisViewModel
import com.piri.probodia.widget.utils.Convert
import java.lang.Double.NaN
import kotlin.math.roundToInt

class AnalysisHemoglobinFragment : Fragment() {

    private lateinit var binding : FragmentAnalysisHemoglobinBinding

    private lateinit var viewModel : RecordAnalysisViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisHemoglobinBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(requireParentFragment()).get(RecordAnalysisViewModel::class.java)
        binding.vm = viewModel

        viewModel.hemoglobinResult.observe(viewLifecycleOwner) {
            if (!it.isNaN()) {
                binding.hemoglobinText.textSize = 32f
                binding.hemoglobinText.text = "${String.format("%.1f", it)}%"
            }
        }

        return binding.root
    }
}