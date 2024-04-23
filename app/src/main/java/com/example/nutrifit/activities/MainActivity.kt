package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrifit.R
import com.example.nutrifit.calendario.CalendarioAdapter
import com.example.nutrifit.calendario.CalendarioUtils
import com.example.nutrifit.pojo.Alimento
import java.time.LocalDate




class MainActivity : AppCompatActivity(), CalendarioAdapter.OnItemListener{
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView

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
            // Aquí colocas el código para abrir la AnhadirComidaActivity
            val intent = Intent(this, AnhadirComidaActivity::class.java)
            intent.putExtra("tipo", "Desayuno")

            startActivity(intent)

        }

// Definir el OnClickListener para addFoodTextView2
        addFoodTextView2.setOnClickListener {
            val intent = Intent(this, AnhadirComidaActivity::class.java)
            intent.putExtra("tipo", "Almuerzo")
            startActivity(intent)

        }

// Definir el OnClickListener para addFoodTextView3
        addFoodTextView3.setOnClickListener {
            // Aquí colocas el código para abrir la AnhadirComidaActivity
            val intent = Intent(this, AnhadirComidaActivity::class.java)
            intent.putExtra("tipo", "Merienda")
            startActivity(intent)

        }

// Definir el OnClickListener para addFoodTextView4
        addFoodTextView4.setOnClickListener {
            // Aquí colocas el código para abrir la AnhadirComidaActivity
            val intent = Intent(this, AnhadirComidaActivity::class.java)
            intent.putExtra("tipo", "Cena")
            startActivity(intent)

        }

    }

    private fun setWeekView() {
        // Obtener la fecha actual
        val currentDate = LocalDate.now()

        // Obtener el primer y último día de la semana
        val startOfWeek = CalendarioUtils.mondayForDate(CalendarioUtils.selectedDate ?: currentDate)
        val endOfWeek = startOfWeek?.plusDays(6)

        // Obtener el mes y año del primer y último día de la semana en español
        val startMonthYear = CalendarioUtils.monthYearFromDate(startOfWeek ?: currentDate, "es")
        val endMonthYear = CalendarioUtils.monthYearFromDate(endOfWeek ?: currentDate, "es")

        // Concatenar la información de mes/año solo si los meses son diferentes
        val monthYearTextString = if (startMonthYear != endMonthYear) {
            "$startMonthYear / $endMonthYear"
        } else {
            startMonthYear
        }

        // Establecer el texto en el TextView
        monthYearText.text = monthYearTextString

        // Obtener los días de la semana actual
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
    }
}

