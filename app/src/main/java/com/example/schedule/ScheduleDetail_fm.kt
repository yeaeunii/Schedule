package com.example.schedule

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class ScheduleDetail_fm : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var detailfm = inflater.inflate(R.layout.fragment_schedule_detail_fm, container, false)

        val bundle = arguments
        val schedule = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.getParcelable("item", ScheduleVO::class.java)
        } else {
            bundle?.getParcelable("item")
        }

        /*
        val id = bundle?.getInt("id")
        val year = bundle?.getInt("year")
        val month = bundle?.getInt("month")
        val day = bundle?.getInt("day")
        val stime = bundle?.getString("start")
        val etime = bundle?.getString("end")
        val person = bundle?.getString("peopleNum")
        val writer = bundle?.getString("name")
        val depart = bundle?.getString("department")
        val meet = bundle?.getString("contents")
        val createNm = bundle?.getString("createNm")
        */

        var tv_re_date: TextView = detailfm.findViewById(R.id.tv_cl_date)
        var tv_res_stime: TextView = detailfm.findViewById(R.id.et_s_time)
        var tv_res_etime: TextView = detailfm.findViewById(R.id.et_d_time)
        var tv_res_person: TextView = detailfm.findViewById(R.id.et_person)
        var tv_res_writer: TextView = detailfm.findViewById(R.id.et_writer)
        var tv_res_depart: TextView = detailfm.findViewById(R.id.tv_res_depart)
        var tv_res_meet: TextView = detailfm.findViewById(R.id.tv_res_meet)


        var btnRevoke: Button = detailfm.findViewById(R.id.btnReservation)


        //<예약 디테일>
        schedule?.run {
            tv_re_date.text = "$year-$month-$day"
            tv_res_stime.text = start
            tv_res_etime.text = end
            tv_res_person.text = peopleNum + "명"
            tv_res_writer.text = name
            tv_res_depart.text = department
            tv_res_meet.text = contents
        }


        //예약취소
        btnRevoke.setOnClickListener {
            schedule?.let {
                CommonDialog(requireContext(), it) {}.show()
            }

        }


        return detailfm
    }


}