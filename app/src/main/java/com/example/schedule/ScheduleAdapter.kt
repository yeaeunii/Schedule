package com.example.schedule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class ScheduleAdapter(
    val context: Context,
    val layout: Int,
    val scheduleList: ArrayList<ScheduleVO>
) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    val inflater = LayoutInflater.from(context)

    //1.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = inflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    //2.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var tv_meet: TextView
        lateinit var tv_depart: TextView
        lateinit var tv_writer: TextView
        lateinit var tv_stime: TextView
        lateinit var tv_etime: TextView
        lateinit var img_add: ImageView
        

        init {
            tv_meet = view.findViewById(R.id.tv_res_meet)
            tv_depart = view.findViewById(R.id.tv_depart)
            tv_writer = view.findViewById(R.id.tv_writer)
            tv_stime = view.findViewById(R.id.tv_stime)
            tv_etime = view.findViewById(R.id.tv_etime)
            img_add = view.findViewById(R.id.img_add)

        }
    }

    //3.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv_meet.text = scheduleList[position].contents
        holder.tv_depart.text = scheduleList[position].department
        holder.tv_writer.text = scheduleList[position].name
        holder.tv_stime.text = scheduleList[position].start
        holder.tv_etime.text = "-" + scheduleList[position].end



        holder.itemView.setOnClickListener {
            val selectedBoard = scheduleList[position]
            val fragment = ScheduleDetail_fm()

            val bundle = Bundle()
            bundle.putParcelable("item", selectedBoard)

            /*
            bundle.putInt("id", selectedBoard.id)
            bundle.putInt("year", selectedBoard.year)
            bundle.putInt("month", selectedBoard.month)
            bundle.putInt("day", selectedBoard.day)
            bundle.putString("start", selectedBoard.start)
            bundle.putString("end", selectedBoard.end)
            bundle.putString("peopleNum", selectedBoard.peopleNum)
            bundle.putString("name", selectedBoard.name)
            bundle.putString("department", selectedBoard.department)
            bundle.putString("contents", selectedBoard.contents)
            bundle.putString("createNm", selectedBoard.createNm)
            */

            fragment.arguments = bundle

            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fl, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


    //4.
    override fun getItemCount(): Int {
        return scheduleList.size
    }
}

