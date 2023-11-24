package com.example.schedule

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.schedule.databinding.ActivityRevokeBinding
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset


class CommonDialog(
    context: Context,
    val schedule: ScheduleVO,
    val cancelCallback: () -> Unit
) : Dialog(context) {

    lateinit var binding: ActivityRevokeBinding
    lateinit var requestQueue: RequestQueue
    lateinit var RevokeVO: RevokeVO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRevokeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestQueue = Volley.newRequestQueue(context)


        binding.btnOk.setOnClickListener {

            val inputRevoker = binding.dialogRevoker.text.toString()
            val inputReasoon = binding.dialogComment.text.toString()
            var preferences = context.getSharedPreferences("KaKao", Context.MODE_PRIVATE)
            var userId = preferences.getString("userId", null)

            val revokeVo =
                RevokeVO(schedule.id, schedule.createNm, inputRevoker, inputReasoon, userId!!)

            Log.e("rverr", "revokeVo: ${revokeVo.toString()}")
            val gson = Gson()
            val requestBody = gson.toJson(revokeVo)

            val url = "http://meet.witches.co.kr/api/cancel"


            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST, url, JSONObject(requestBody),
                Response.Listener { response ->
                    try {
                        Log.d("req", response.toString())
                        
                        val jsonResponse = JSONObject(response.toString())
                        val reDataString = jsonResponse.optString("reData")
                        val reData = JSONObject(reDataString)
                        val reCode = reData.optString("reCode")

                        if (reCode == "00") {
                            Toast.makeText(context, "예약이 취소 되었습니다", Toast.LENGTH_LONG).show()
                            dismiss()

                            //main_fm()

                        } else if (reCode == "02") {
                            Toast.makeText(context, "기존 신청인이 아닙니다", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "필수값을 입력해주세요", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()

                    }
                },
                Response.ErrorListener { error ->
                    Log.e("rverr", "에러 발생: ${error.toString()}")
                    error.printStackTrace()
                }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                override fun getBody(): ByteArray {
                    Log.d("reqq", "Request Body: $requestBody")
                    return requestBody.toByteArray(Charset.defaultCharset())
                }

            }
            requestQueue.add(jsonObjectRequest)

        }


        binding.btnCancel.setOnClickListener {
            cancelCallback?.let {
                cancelCallback()
            }
            dismiss()
        }


    }
}

