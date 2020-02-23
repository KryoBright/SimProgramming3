package com.example.lab2simulation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.LineData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.math.sqrt
import com.github.mikephil.charting.components.LimitLine
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.time.toDuration


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeMiH  =TimeHolder()

        button.setOnClickListener {
            timeMiH.currentTime=0f
            val chart = findViewById<LineChart>(R.id.chart1)
            val entries = ArrayList<Entry>()
            var ball=PhysicalBody(0f, editText3.text.toString().toFloat(),editText.text.toString().toFloat(),(editText2.text.toString().toFloat()*Math.PI/180.0).toFloat())
            timeMiH.countTimeInStep(ball.getMaxTime(),1024f)
            var maxs=max(ball.getMaxX(),ball.getMaxY())*1.05F //обеспечивает небольшой отступ справа и сверху,чтоб график не склеивался
                                            //и равенство измерений осей x и y
            val ll = LimitLine(ball.getY(), "Start height")
            ll.lineColor = Color.LTGRAY
            ll.lineWidth = 2f
            ll.textColor = Color.BLACK
            ll.textSize = 10f
            chart.axisLeft.removeAllLimitLines()
            chart.axisLeft.addLimitLine(ll);
            chart.description.text=""
            entries.add(Entry(0f,ball.getY()))
            ball.moveFor(timeMiH.getTimeInStep())
            var laststep=System.currentTimeMillis()
            GlobalScope.launch {
                withContext(Dispatchers.Default)
                {
                    while (!ball.isTouchingGround()) {
                        if ((!timeMiH.isPaused())&&((System.currentTimeMillis()-laststep)>timeMiH.getStepLen())) {
                                laststep=System.currentTimeMillis()
                                ball.moveFor(timeMiH.getTimeInStep())
                                entries.add(Entry(ball.getX(), ball.getY()))
                                val dataSet = LineDataSet(entries, "Траектория"); // add entries to dataset
                                dataSet.color = Color.CYAN;
                                dataSet.valueTextColor = Color.WHITE;
                                val lineData = LineData(dataSet)
                                GlobalScope.launch {
                                    withContext(Dispatchers.Main)
                                    {
                                        chart.data = lineData
                                        chart.setVisibleXRangeMaximum(maxs)
                                        chart.setVisibleXRangeMinimum(maxs)
                                        chart.fitScreen()
                                        chart.xAxis.axisMaximum = maxs
                                        chart.axisLeft.axisMaximum = maxs
                                        chart.axisLeft.axisMinimum = 0f
                                        chart.axisRight.axisMaximum = maxs
                                        chart.axisRight.axisMinimum = 0f
                                        chart.invalidate()
                                        textView4.text=timeMiH.currentTime.toString()+" seconds"
                                        timeMiH.currentTime+=timeMiH.getTimeInStep()
                                    }
                            }
                        }
                    }
                }
            }
        }

        button2.setOnClickListener {
            timeMiH.pauseUnpause()
        }
    }
}
