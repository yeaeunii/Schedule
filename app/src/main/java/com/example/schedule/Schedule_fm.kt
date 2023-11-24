package com.example.schedule

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.time.LocalDate

interface OnReservationCompleteListener {
    fun onReservationComplete(newSchedule: ScheduleVO)
}


class Schedule_fm : Fragment() {

    lateinit var InsertVO: InsertVO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var schedulefm = inflater.inflate(R.layout.fragment_schedule_fm, container, false)

        var tv_cl_date: TextView = schedulefm.findViewById(R.id.tv_cl_date)

        var et_s_time: TextView = schedulefm.findViewById(R.id.et_s_time)
        var et_e_time: TextView = schedulefm.findViewById(R.id.et_e_time)
        var et_person: TextView = schedulefm.findViewById(R.id.et_person)
        var et_writer: TextView = schedulefm.findViewById(R.id.et_writer)
        var et_depart: TextView = schedulefm.findViewById(R.id.tv_res_depart)
        var et_meet: TextView = schedulefm.findViewById(R.id.tv_res_meet)
        var btnReservation: Button = schedulefm.findViewById(R.id.btnReser)

        val reqQueue: RequestQueue = Volley.newRequestQueue(requireContext())

        // 예약시간
        val bundle = arguments
        val year = bundle?.getInt("pyear")
        val month = bundle?.getInt("pmonth")
        val day = bundle?.getInt("pday")
        tv_cl_date.text = "$year-$month-$day"


        btnReservation.setOnClickListener {

            var STime = et_s_time.text.toString()
            var ETime = et_e_time.text.toString()
            var Num = et_person.text.toString()
            var Writer = et_writer.text.toString()
            var Depart = et_depart.text.toString()
            var Meet = et_meet.text.toString()
            var preferences = requireContext().getSharedPreferences("KaKao", Context.MODE_PRIVATE)
            var userId = preferences.getString("userId", null)

            val insertVO = InsertVO(
                0,
                "",
                "",
                STime,
                ETime,
                Num,
                Writer,
                Depart,
                Meet,
                year!!,
                month!!,
                day!!,
                userId!!
            )
            val gson = Gson()
            val requestBody = gson.toJson(insertVO)

            val url = "http://meet.witches.co.kr/api/success"

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                { response ->
                    Log.d("comres", "서버 응답: $response")
                    val jsonResponse = JSONObject(response.toString())
                    val reDataString = jsonResponse.optString("result")
                    val result = JSONObject(reDataString)
                    val reCode = result.optString("reCode")

                    if (reCode == "00") {

                        val mainFragment = Main_fm()
                        val bundle = Bundle()
                        bundle.putString("message", "예약이 완료되었습니다") // 메시지 전달
                        mainFragment.arguments = bundle

                        val fragmentManager = requireActivity().supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.fl, mainFragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()


                    } else if (reCode == "03") {
                        Toast.makeText(context, "지난 날짜는 예약이 불가합니다", Toast.LENGTH_SHORT).show()
                    } else if (reCode == "01") {
                        Toast.makeText(context, "이미 예약된 시간입니다", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "필수 값을 입력해주세요", Toast.LENGTH_SHORT).show()
                    }


                },
                { error ->

                    Log.e("comerr", "에러 발생: ${error.toString()}")

                }) {


                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                override fun getBody(): ByteArray {
                    Log.d("requestBody", "Request Body: $requestBody")
                    return requestBody.toByteArray(Charset.forName("UTF-8"))
                }
            }

            reqQueue.add(stringRequest)

        }

        return schedulefm
    }

}
