/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.myapplication.presentation

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers
import android.app.ActionBar
import android.os.Bundle
import android.os.Debug
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.google.android.gms.common.Feature
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpRequest
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import java.net.URL
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.http.ContentType.Application.Cbor
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import ru.mosmetro.metro.map.data.models.schema.Answer
import ru.mosmetro.metro.map.data.models.schema.SchemaResponse
import ru.mosmetro.metro.map.data.models.schema.data.AdditionalResponse
import ru.mosmetro.metro.map.data.models.schema.data.ConnectionResponse
import ru.mosmetro.metro.map.data.models.schema.data.LineResponse
import ru.mosmetro.metro.map.data.models.schema.data.StationResponse
import ru.mosmetro.metro.map.data.models.schema.data.TransitionResponse

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.cbor.*
import kotlinx.serialization.cbor.*
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity() {
    var list = mutableListOf<Int>()
    var counter = 1;
    private val client = OkHttpClient()
    private val jsonText = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        AddStation(View(this))
        AddStation(View(this))

    }


    fun AddStation(viev: View){


        var lay = findViewById<LinearLayout>(R.id.Lay);
        val button = Button(this)
        button.text = "Выберите станию"
        button.id = counter;
        button.setOnClickListener {
            ClickedStationButton(button.id)
        }
        list += counter;
        counter += 1;
        lay.addView(button)

    }
    fun openMenu(){
        var lay = findViewById<LinearLayout>(R.id.Lay);
        for (i in list){
            val button = Button(this)
            button.text = "Выберите станию"
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
            openMenu()

        }
    }
   fun ClickedStationButton(id: Int){
       GlobalScope.launch (Dispatchers.Main){
        update();}
        setContentView(R.layout.pick_activity)
    }


    suspend fun update() {

val client = HttpClient(CIO){
    install(ContentNegotiation){
        json(Json { ignoreUnknownKeys=true })
    }







}
        val customer: Answer = client.get("https://devapp.mosmetro.ru/api/schema/v1.0/").body()
        Toast.makeText(applicationContext, "" + customer.success, Toast.LENGTH_SHORT).show()

    }

}

