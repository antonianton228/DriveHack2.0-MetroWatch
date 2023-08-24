/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.myapplication.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
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
import ru.mosmetro.metro.map.data.models.schema.data.StationResponse
import java.util.Arrays


class Node {
    var currentCost = 0;
    var id = 0
    var idTo = mutableListOf<Int>();
    var CostTo = mutableListOf<Int>();


    fun setId(id_: Int) {
        id = id_;
    }

    fun setCost(Cost_: Int) {
        currentCost = Cost_;
    }

}




class MainActivity : ComponentActivity() {
    var list = mutableListOf<Int>(1, 2)
    var listOfId = mutableListOf<Int>(0, 0)
    var counter = 1;
    private val client = OkHttpClient()
    private val jsonText = ""
    var StationsList = mutableListOf<StationResponse>();

    var currentId = 0





    var s = mutableSetOf<Node>()
    var w = 0


   var listOfNods = mutableListOf<Node>()






    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        GlobalScope.launch (Dispatchers.Main){
           update();}
        openMenu()


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
            val button = Button(this)
            if(listOfId[list.indexOf(i)] == 0){
                button.text = "Выберите станию"
            }
            else{
                for(j in StationsList){
                    if (j.id == listOfId[list.indexOf(i)]){
                        button.text = j.name.ru
                    }
                }
            }

            button.id = i;
            button.setOnClickListener {

                ClickedStationButton(button.id)

            }
            lay.addView(button)
        }
    }
    fun minusStation(viev: View){


        if(list.count() > 2){
            var lay = findViewById<LinearLayout>(R.id.Lay);
            lay.removeAllViews()
            list.removeLast()
            listOfId.removeLast()
            openMenu()

        }
    }
   fun ClickedStationButton(id: Int){
       setContentView(R.layout.pick_activity)
       var Lay = findViewById<LinearLayout>(R.id.Layout)
       Toast.makeText(applicationContext, "" + id, Toast.LENGTH_SHORT).show();
        currentId = id;
       Lay.removeAllViews()
       for(i in StationsList){
           val button = Button(this)
           button.text = i.name.ru.toString()
           button.id = i.id;
           button.setOnClickListener {

               PickStation(button.id)

           }
Lay.addView(button)
       }




    }


    suspend fun update() {

val client = HttpClient(CIO){
    install(ContentNegotiation){
        json(Json { ignoreUnknownKeys=true })
    }
}
        val customer: Answer = client.get("https://devapp.mosmetro.ru/api/schema/v1.0/").body()
        StationsList = customer.data.stations.toMutableList();
        var connectionslist = customer.data.connections.toMutableList()
    var flag = true;
        for( i in connectionslist){
            for(k in listOfNods){
                if(i.id in k.idTo){
                    flag = false;
                }
            }
            if(flag){
                var timeNode = Node();
                timeNode.setId(i.stationFromId)
                timeNode.currentCost = 10000000
                timeNode.idTo += i.id
                timeNode.currentCost += i.
            }
            else{

            }



        }


        Toast.makeText(applicationContext, "" + customer.success, Toast.LENGTH_SHORT).show()


    }


    fun PickStation(id: Int){
        listOfId[list.indexOf(currentId)] = id
Toast.makeText(applicationContext, "" + id, Toast.LENGTH_SHORT).show()
        setContentView(R.layout.main_activity)
        openMenu()
    }






}


