package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrifit.R
import com.example.nutrifit.calendario.CalendarioAdapter
import com.example.nutrifit.calendario.CalendarioUtils
import java.time.LocalDate

class MainActivity : AppCompatActivity(), CalendarioAdapter.OnItemListener {
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private var selectedLongClickDate: LocalDate? =  LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        if (CalendarioUtils.selectedDate == null) {
            CalendarioUtils.selectedDate = LocalDate.now()
        }
        setWeekView()
    }

    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)

        val addFoodTextView1: TextView = findViewById(R.id.anhadircomida)
        val addFoodTextView2: TextView = findViewById(R.id.anhadircomida2)
        val addFoodTextView3: TextView = findViewById(R.id.anhadircomida3)
        val addFoodTextView4: TextView = findViewById(R.id.anhadircomida4)

        addFoodTextView1.setOnClickListener {
            val intent = Intent(this, AnhadirComidaActivity::class.java)
            intent.putExtra("tipo", "Desayuno")
            startActivity(intent)
        }

        addFoodTextView2.setOnClickListener {
            val intent = Intent(this, AnhadirComidaActivity::class.java)
            intent.putExtra("tipo", "Almuerzo")
            startActivity(intent)
        }

        addFoodTextView3.setOnClickListener {
            val intent = Intent(this, AnhadirComidaActivity::class.java)
            intent.putExtra("tipo", "Merienda")
            startActivity(intent)
        }

        addFoodTextView4.setOnClickListener {
            val intent = Intent(this, AnhadirComidaActivity::class.java)
            intent.putExtra("tipo", "Cena")
            startActivity(intent)
        }
    }

    private fun setWeekView() {
        val currentDate = LocalDate.now()
        val startOfWeek = CalendarioUtils.mondayForDate(CalendarioUtils.selectedDate ?: currentDate)
        val endOfWeek = startOfWeek?.plusDays(6)
        val startMonthYear = CalendarioUtils.monthYearFromDate(startOfWeek ?: currentDate, "es")
        val endMonthYear = CalendarioUtils.monthYearFromDate(endOfWeek ?: currentDate, "es")
        val monthYearTextString = if (startMonthYear != endMonthYear) {
            "$startMonthYear / $endMonthYear"
        } else {
            startMonthYear
        }

        monthYearText.text = monthYearTextString

        val days = CalendarioUtils.daysInWeekArray(CalendarioUtils.selectedDate ?: currentDate)

        val calendarAdapter = CalendarioAdapter(days, this)
        val layoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    fun previousWeekAction(view: View?) {
        CalendarioUtils.selectedDate = CalendarioUtils.selectedDate?.minusWeeks(1)
        setWeekView()
    }

    fun nextWeekAction(view: View?) {
        CalendarioUtils.selectedDate = CalendarioUtils.selectedDate?.plusWeeks(1)
        setWeekView()
    }

    override fun onItemClick(position: Int, date: LocalDate) {
        CalendarioUtils.selectedDate = date
        setWeekView()
        selectedLongClickDate = date
    }


}
