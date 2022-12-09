package com.piri.probodia.view.fragment.etc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piri.probodia.R
import com.piri.probodia.adapter.ShowDoctorListAdapter
import com.piri.probodia.data.remote.model.GlucoseDto
import com.piri.probodia.data.remote.model.RecordDatasBase
import com.piri.probodia.data.remote.model.ShowDoctorDto
import com.piri.probodia.databinding.FragmentShowDoctorGlucoseBinding
import com.piri.probodia.repository.PreferenceRepository
import com.piri.probodia.viewmodel.ShowDoctorViewModel
import com.piri.probodia.widget.utils.Convert
import java.lang.Math.ceil
import java.time.LocalDateTime

class ShowDoctorGlucoseFragment : Fragment() {

    private lateinit var binding : FragmentShowDoctorGlucoseBinding
    private lateinit var viewModel : ShowDoctorViewModel
    private lateinit var showDoctorRVAdapter : ShowDoctorListAdapter

    private var minItemCount = 0
    private var lastItemDate = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_doctor_glucose, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(ShowDoctorViewModel::class.java)
        binding.vm = viewModel

        showDoctorRVAdapter = ShowDoctorListAdapter(::setItemMinCount)
        binding.recordRv.adapter = showDoctorRVAdapter
        binding.recordRv.layoutManager = LinearLayoutManager(context)

        viewModel.localDateTime.observe(viewLifecycleOwner) {
            viewModel.getGlucoseRecord(PreferenceRepository(requireContext()), it)
        }

        viewModel.result.observe(viewLifecycleOwner) { result ->
            addResult(result.second)
        }

        binding.recordRv.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager : LinearLayoutManager = binding.recordRv.layoutManager as LinearLayoutManager
                    val totalCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()

                    val listLastItem : ShowDoctorDto? =
                        showDoctorRVAdapter.getLastItem()

                    if (listLastItem != null && lastVisible >= totalCount - 1 &&
                        lastItemDate != listLastItem.date
                    ) {
                        lastItemDate = listLastItem.date

                        viewModel.setNextLocalDateTime()
                    }
                }
            }
        )

        setStartDateTime()

        return binding.root
    }

    private fun setStartDateTime() {
        viewModel.setLocalDateTime(LocalDateTime.now().plusMonths(1).withDayOfMonth(1))
    }

    private fun addResult(result : MutableList<RecordDatasBase>) {
        val glucoseList = result as MutableList<GlucoseDto>
        glucoseList.sortByDescending {
            it.record.recordDate
        }

        val groupGlucose = glucoseList.groupBy { it.record.recordDate.split(' ')[0] }.values

        val showDoctorList = buildList {
            for (item in groupGlucose) {
                val date = item[0].record.recordDate.split(' ')[0].split('-')
                val showDoctorDto = ShowDoctorDto("${date[1]}.${date[2]}", MutableList(6) { -1 })
                for (glucose in item) {
                    showDoctorDto.values[Convert.timeTagToInt(glucose.record.timeTag)] = glucose.record.glucose
                }
                add(showDoctorDto)
            }
        }.toMutableList()

        showDoctorRVAdapter.addDataSet(showDoctorList)
        showDoctorRVAdapter.notifyDataSetChanged()

        if (minItemCount >= showDoctorRVAdapter.itemCount) {
            viewModel.setNextLocalDateTime()
        }
    }

    private fun setItemMinCount(itemHeight : Int) {
        minItemCount = ceil(binding.recordRv.height.toDouble() / itemHeight).toInt()
    }
}