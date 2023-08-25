/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.myapplication.presentation

import AlgorithmAStar
import Graph
import android.Manifest
import android.app.ActionBar.LayoutParams
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import ru.mosmetro.metro.map.data.models.schema.Answer
import ru.mosmetro.metro.map.data.models.schema.data.LineResponse
import ru.mosmetro.metro.map.data.models.schema.data.StationResponse
import java.lang.Math.abs

data class Coordinates(
    val lat: Double,
    val lon: Double
) : Graph.Vertex

data class Route(
    override val a: Coordinates,
    override val b: Coordinates,
    val cost: Int
) : Graph.Edge<Coordinates> {
    val distance: Int
        get() {
            val dLon = abs(a.lon - b.lon)
            val dLat = abs(a.lat - b.lat)
            return cost
        }
}

class AlgorithmAStarImpl(edges: List<Route>) : AlgorithmAStar<Coordinates, Route>(edges) {
    override fun costToMoveThrough(edge: Route): Double {
        return edge.distance.toDouble()
    }

    override fun createEdge(from: Coordinates, to: Coordinates): Route {
        return Route(from, to, 0)
    }
}


class Node{
    var type = 0
    var from = 0
    var to = 0
    var cost = 0
}



class MainActivity : ComponentActivity() {
    var list = mutableListOf<Int>(1, 2)
    var listOfId = mutableListOf<Int>(0, 0)
    private val client = OkHttpClient()
    private val jsonText = ""
    var StationsList = mutableListOf<StationResponse>();
    var Lines = mutableListOf<LineResponse>();
    var Nods = mutableListOf<Node>();
    var currentId = 0

    private lateinit var fusedLocationClient: FusedLocationProviderClient




    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading)
        GlobalScope.launch (Dispatchers.Main){
            update();
            setContentView(R.layout.main_activity)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
            location()
            openMenu()}






    }

    fun location(){

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener { location->
                if (location != null) {
                    Toast.makeText(applicationContext, location.latitude.toString(), Toast.LENGTH_SHORT).show()
                     }
                else{

                }

            }


    }

    fun AddStation(viev: View){


        list += list[list.lastIndex] + 1;
        listOfId += 0
        openMenu()

    }
    fun openMenu(){
        val lay = findViewById<LinearLayout>(R.id.Lay);
        lay.removeAllViews()
        for (i in list){
            var layHor = LinearLayout(this)
            layHor.orientation = LinearLayout.HORIZONTAL

            val button = Button(this)
            val buttonM = ImageButton(this)

            if(listOfId[list.indexOf(i)] == 0){
                button.text = "Выберите станию"
            }
            else{
                for(j in StationsList){
                    if (j.id == listOfId[list.indexOf(i)]){
                        button.text = j.name.ru
                    }
                }

                buttonM.setBackgroundResource(R.drawable.minus)
                buttonM.setLayoutParams(
                    LayoutParams(
                        100,
                        100

                    )
                )
                buttonM.scaleType = ImageView.ScaleType.FIT_END


            }

            button.id = i;
            button.setOnClickListener {

                ClickedStationButton(button.id)

            }



            if(listOfId[list.indexOf(i)] != 0){

                button.setLayoutParams(
                    LayoutParams(
                        220,
                        LayoutParams.WRAP_CONTENT
                    )
                )

                buttonM.id = button.id + 125431590
                buttonM.setOnClickListener {

                    minusStation(buttonM.id - 125431590)

                }

                layHor.addView(button)
                layHor.addView(buttonM)
            }
            else{
                layHor.addView(button)
            }
            lay.addView(layHor)
        }
    }
    fun minusStation(id: Int){


        if(list.count() > 2){
            var lay = findViewById<LinearLayout>(R.id.Lay);
            lay.removeAllViews()
            var index = list.indexOf(id)
            list.removeAt(index)
            listOfId.removeAt(index)

            openMenu()

        }
        else{
            var lay = findViewById<LinearLayout>(R.id.Lay);
            lay.removeAllViews()
            var index = list.indexOf(id)
            listOfId[index] = 0

            openMenu()
        }
    }
   fun ClickedStationButton(id: Int){
       setContentView(R.layout.pick_sort)
       currentId = id;




    }


    suspend fun update() {

val client = HttpClient(CIO){
    install(ContentNegotiation){
        json(Json { ignoreUnknownKeys=true })
    }
}
        val customer: Answer = client.get("https://devapp.mosmetro.ru/api/schema/v1.0/").body()

        StationsList = customer.data.stations.toMutableList();
        Lines = customer.data.lines.toMutableList()

        for(i in customer.data.transitions){
            val node = Node()
            node.cost = i.pathLength
            node.from = i.stationFromId
            node.to = i.stationToId
            node.type = 1
            Nods += node
        }

        for(i in customer.data.connections){
            val node = Node()
            node.cost = i.pathLength
            node.from = i.stationFromId
            node.to = i.stationToId
            node.type = 0
            Nods += node
        }


//        var flag = true;
//        for (i in connectionslist) {
//            for (k in listOfNods) {
//                if (i.id in k.idTo) {
//                    flag = false;
//                }
//            }
//            if (flag) {
//                var timeNode = Node();
//                timeNode.setId(i.stationFromId)
//                timeNode.currentCost = 10000000
//                timeNode.idTo += i.id
//                timeNode.currentCost += i.
//            } else {
//
//            }
//
//
//
//        }




    }


    fun PickStation(id: Int){
        listOfId[list.indexOf(currentId)] = id
        setContentView(R.layout.main_activity)
        openMenu()
    }


    fun result(view: View){
        if(0 in listOfId){
            Toast.makeText(applicationContext, "Необходимо заполнить все поля", Toast.LENGTH_SHORT).show()
            return
        }
        val routes = mutableListOf<Route>()
        for (i in Nods){
            routes += Route(Coordinates(i.from.toDouble(), i.from.toDouble()), Coordinates(i.to.toDouble(), i.to.toDouble()), i.cost)
        }
        var currentStart = 0;
        var currentTarget = 1;
        val resultList = mutableListOf<Int>()



        while (listOfId.count() != currentTarget){
            val result = AlgorithmAStarImpl(routes)
                .findPath(
                    begin = Coordinates(listOfId[currentStart].toDouble(), listOfId[currentStart].toDouble()),
                    end = Coordinates(listOfId[currentTarget].toDouble(), listOfId[currentTarget].toDouble())
                )

            val pathString = result.first

            if(currentStart == 0)
            {
                for(i in pathString){
                    resultList += i.lat.toInt()
                }
            }
            else{
                var flag = false

                for(i in pathString){
                    if(flag){
                    resultList += i.lat.toInt()
                }
                    flag = true}

            }
            currentStart += 1
            currentTarget += 1
        }

        setContentView(R.layout.result_activity)
        var LayoutR = findViewById<LinearLayout>(R.id.layoutR)
        var lastLine = -1
        for(i in resultList){

            for (j in StationsList){
                if (i == j.id){


                    val text = TextView(this)
                    val layOutText = LinearLayout(this)
                    layOutText.orientation = LinearLayout.HORIZONTAL
                    val round = TextView(this)
                    if(j.lineId == lastLine)
                    {
                        for(s in Lines){
                            if (s.id == j.lineId){
                                val color: Int = Color.parseColor(s.color)
                                round.setTextColor(color)
                            }
                        }


                        round.setText("●")
                    }
                    else
                    {
                        round.setText("-->")
                    }
                    lastLine = j.lineId
                    round.width = 40

                    text.setText(j.name.ru)
                    layOutText.addView(round)
                    layOutText.addView(text)
                    LayoutR.addView(layOutText)
                }
            }
        }


    }


    fun letterSort(viev: View){
        val b = viev as Button
        setContentView(R.layout.pick_activity)
        var Lay = findViewById<LinearLayout>(R.id.Layout)

       Lay.removeAllViews()
       for(i in StationsList){
           if(i.name.ru!![0].lowercase() == b.text){
               val button = Button(this)
               button.text = i.name.ru.toString()
               button.id = i.id;
               for(j in Lines){
                   if(j.id == i.lineId) {
                       button.setTextColor(Color.parseColor(j.color))
                   }
               }

               button.setOnClickListener {

                   PickStation(button.id)

               }
               Lay.addView(button)
           }

       }

    }

    fun openalphavite(viev: View){
        setContentView(R.layout.sort_letters)
    }


    fun openMain(viev: View){
        setContentView(R.layout.main_activity)
        openMenu()
    }
    fun openSortMenu(viev: View){
        setContentView(R.layout.pick_sort)
    }

    fun lineSort(viev: View){
        var bat = viev as ImageButton
        setContentView(R.layout.pick_activity)
        var Lay = findViewById<LinearLayout>(R.id.Layout)

        Lay.removeAllViews()
        for(i in StationsList){
            if(i.lineId == bat.contentDescription.toString().toInt()){
                val button = Button(this)
                button.text = i.name.ru.toString()
                button.id = i.id;
                for(j in Lines){
                    if(j.id == i.lineId) {
                        button.setTextColor(Color.parseColor(j.color))
                    }
                }
                button.setOnClickListener {

                    PickStation(button.id)

                }
                Lay.addView(button)
            }

        }
    }

    fun openLineSort(viev: View){
        setContentView(R.layout.sort_lines)
    }




}


