package com.example.schedule

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import org.json.JSONArray
import org.json.JSONException
import java.nio.charset.Charset
import java.time.DayOfWeek
import java.util.Calendar
import java.util.Collections


class Main_fm : Fragment(), OnReservationCompleteListener {

    private lateinit var calendar: MaterialCalendarView
    lateinit var schedule: ScheduleVO
    private lateinit var adapter: ScheduleAdapter
    lateinit var rv: RecyclerView
    private lateinit var fragmentContext: Context
    private val decoratedDates = mutableSetOf<CalendarDay>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onReservationComplete(newSchedule: ScheduleVO) {

        adapter.scheduleList.add(newSchedule)
        adapter.notifyDataSetChanged()
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var mainfm = inflater.inflate(R.layout.fragment_main_fm, container, false)


        calendar = mainfm.findViewById(R.id.mc)
        var tv_day: TextView = mainfm.findViewById(R.id.tv_day)
        var tv_month: TextView = mainfm.findViewById(R.id.tv_month)
        rv = mainfm.findViewById(R.id.rv)
        var img_add: ImageView = mainfm.findViewById(R.id.img_addd)

        val reqQueue: RequestQueue = Volley.newRequestQueue(requireContext())

        val todayDecorator = TodayDecorator(requireContext())
        val today = Calendar.getInstance()
        val month = today.get(Calendar.MONTH) + 1
        val day = today.get(Calendar.DAY_OF_MONTH)

        calendar.setSelectedDate(CalendarDay.today())
        tv_day.text = day.toString()
        tv_month.text = month.toString() + "월"

        img_add.setOnClickListener {
            val selectedDate = calendar.selectedDate
            val pyear = selectedDate.year
            val pmonth = selectedDate.month + 1
            val pday = selectedDate.day

            val scheduleFragment = Schedule_fm()

            val bundle = Bundle()
            bundle.putInt("pyear", pyear.toInt())
            bundle.putInt("pmonth", pmonth.toInt())
            bundle.putInt("pday", pday.toInt())

            scheduleFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fl, scheduleFragment)
                .addToBackStack(null)
                .commit()

        }


//        calendarConstraintBuilder.setValidator(DateValidatorPointForward.now())
        calendar.setOnDateChangedListener { widget, date, selected ->


            val selectedYear = date.year
            val selectedMonth = date.month + 1
            val selectedDay = date.day

            tv_day.text = date.day.toString()
            tv_month.text = "$selectedMonth" + "월"


            val apiUrl = "http://meet.witches.co.kr/api/FindScheduleListAll"

            val request = object : StringRequest(
                Request.Method.POST,
                apiUrl,
                { response ->

                    val jsonArray = String(
                        response.toByteArray(Charset.forName("iso-8859-1")),
                        Charset.forName("utf-8")
                    )
                    Log.d("msg", jsonArray.toString())

                    val scheduleList: ArrayList<ScheduleVO> =
                        Gson().fromJson(
                            jsonArray,
                            ScheduleListVO::class.java
                        )//parseSceduleList(msg)

                    val filteredList = scheduleList.filter { schedule ->
                        schedule.year == selectedYear && schedule.month == selectedMonth && schedule.day == selectedDay
                    }
                    setupRecyclerView(ArrayList(filteredList))

                    calendar.addDecorator(
                        EventDecorator(scheduleList.map {
                            CalendarDay.from(it.year, it.month, it.day)
                        })
                    )


                },
                { error ->
                    Log.d("seaerr", error.toString())

                }
            ) {


            }
            reqQueue.add(request)
        }


        // 주말색
        calendar.addDecorators(
            SundayDecorator(),
            SaturdayDecorator(),
            todayDecorator,

            )


        val message = arguments?.getString("message")
        if (!message.isNullOrEmpty()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }


        return mainfm
    }


    private fun setupRecyclerView(scheduleList: ArrayList<ScheduleVO>) {
        if (isAdded && ::fragmentContext.isInitialized) {

            val layoutManager = LinearLayoutManager(fragmentContext)
            rv.layoutManager = layoutManager

            adapter = ScheduleAdapter(fragmentContext, R.layout.schedule_list, scheduleList)
            rv.adapter = adapter
        }
    }


    fun parseSceduleList(response: String): ArrayList<ScheduleVO> {
        val scheduleList = ArrayList<ScheduleVO>()

        // JSON 형식의 응답을 파싱하여 scheduleList에 추가
        try {
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val id = jsonObject.getInt("id")
                val year = jsonObject.getInt("year")
                val month = jsonObject.getInt("month")
                val day = jsonObject.getInt("day")
                val start = jsonObject.getString("start")
                val end = jsonObject.getString("end")
                val content = jsonObject.getString("contents")
                val depart = jsonObject.getString("department")
                val writer = jsonObject.getString("name")
                val createNm = jsonObject.getString("createNm")
                val peopleNum = jsonObject.getString("peopleNum")


                schedule = ScheduleVO(
                    id,
                    year,
                    month,
                    day,
                    start,
                    end,
                    peopleNum,
                    writer,
                    createNm,
                    depart,
                    content,
                    "N",
                    "",
                    ""
                )
                scheduleList.add(schedule)
            }
        } catch (e: JSONException) {

            e.printStackTrace()
        }

        return scheduleList
    }
}

class TodayDecorator(context: Context) : DayViewDecorator {

    private var date = CalendarDay.today()
    private val drawable = ContextCompat.getDrawable(context, R.drawable.calendar_circle_gray)


    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(StyleSpan(Typeface.BOLD))
        view?.setBackgroundDrawable(drawable!!)

    }

}


class SaturdayDecorator : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = day?.date
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SATURDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.BLUE))
    }

}

class SundayDecorator : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = day?.date
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.RED))
    }


}


class EventDecorator(private val filteredDates: Collection<CalendarDay>) : DayViewDecorator {

    private val dotColors = listOf(Color.parseColor("#e26d5c"), Color.parseColor("#3a5a40"))
    private var colorIndex = 0
    
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val contains =
            day?.let { filteredDates.contains(CalendarDay.from(it.year, it.month + 1, it.day)) }
                ?: false
        Log.d("EventDecorator", "shouldDecorate - day: $day, contains: $contains")
        return contains
    }

    override fun decorate(view: DayViewFacade?) {
        try {
            if (view != null) {
                view.addSpan(DotSpan(7F, dotColors[colorIndex]))
                colorIndex = (colorIndex + 1) % dotColors.size
            }
        } catch (e: Exception) {
            Log.e("EventDecorator", "Error in decorate method: ${e.message}")
        }

    }
}


//class EventDecorator(private val filteredDates: Collection<CalendarDay>) : DayViewDecorator {
//
//    private val dotColors = listOf(Color.parseColor("#e26d5c"))
//    private val decoratedDates = mutableSetOf<CalendarDay>()
//
//    override fun shouldDecorate(day: CalendarDay?): Boolean {
//        return filteredDates.contains(day) && day != null && decoratedDates.add(day)
//    }
//
//    override fun decorate(view: DayViewFacade?) {
//        try {
//            if (view != null) {
//                view.addSpan(DotSpan(7F, dotColors.random()))
//            }
//        } catch (e: Exception) {
//            Log.e("EventDecorator", "Error in decorate method: ${e.message}")
//        }
//
//    }
//}

