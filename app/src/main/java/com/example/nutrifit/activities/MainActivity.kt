package com.example.nutrifit.activities

import com.example.nutrifit.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrifit.calendario.CalendarioAdapter
import com.example.nutrifit.calendario.CalendarioUtils
import com.example.nutrifit.calendario.CalendarioUtils.daysInWeekArray
import com.example.nutrifit.calendario.CalendarioUtils.monthYearFromDate
import java.time.LocalDate
import java.time.YearMonth

class MainActivity : AppCompatActivity(), CalendarioAdapter.OnItemListener{
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var eventListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        setWeekView()
    }

    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
        eventListView = findViewById(R.id.eventListView)
    }

    private fun setWeekView() {
        // Obtener la fecha actual
        val currentDate = LocalDate.now()
        // Obtener el mes y año actual formateado
        val currentMonthYear = CalendarioUtils.monthYearFromDate(currentDate)
        // Establecer el texto en el TextView
        monthYearText.text = currentMonthYear
        // Obtener los días de la semana actual
        val days = CalendarioUtils.daysInWeekArray(currentDate)

        val calendarAdapter = CalendarioAdapter(days, this)
        val layoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    override fun onItemClick(position: Int, date: LocalDate) {
        TODO("Not yet implemented")
    }
}
