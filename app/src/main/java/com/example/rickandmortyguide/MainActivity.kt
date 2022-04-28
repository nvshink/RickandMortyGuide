package com.example.rickandmortyguide

import AdapterList
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


class MainActivity : AppCompatActivity() {
    var characters = mutableListOf<HashMap<String, Any>>()
    var pageNumber: Int = 1
    lateinit var podsAdapter: AdapterList
    lateinit var infoResult: InfoList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    fun initViews() {

        val subscriber: Subscriber<Characters> = object : Subscriber<Characters> {
            //загрузка данных
            override fun onNext(@NonNull charactersAll: Characters) {
                //получаем всех персонажей на странице
                val resultLists: List<ResultList> = charactersAll.resultLists

                //проверяем наличие страниц до и после текущей
                infoResult = charactersAll.infoList
                val nextPageButton = findViewById<Button>(R.id.nextPageButton)
                nextPageButton.isEnabled = infoResult.next != null
                val prevPageButton = findViewById<Button>(R.id.previousPageButton)
                prevPageButton.isEnabled = infoResult.prev != null

                //запись всех персонажей в HashMap
                for (i in resultLists.indices) {
                    var characterHashMap: HashMap<String, Any> = HashMap()
                    val resultList: ResultList = resultLists.get(i)
                    characterHashMap.put("Id", resultList.id)
                    characterHashMap.put("Name", resultList.name)
                    characterHashMap.put("Species", resultList.species)
                    characterHashMap.put("Gender", resultList.gender)
                    characterHashMap.put("Image", resultList.image)
                    characters.add(characterHashMap)
                }

                val podsList: ListView = findViewById(R.id.pods_list)
                //обращение к кастомному адаптеру
                podsAdapter = AdapterList(this@MainActivity, characters)
                podsList.adapter = podsAdapter
                //назначение обрыботчика события при нажатии на элемент списка
                podsList.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this@MainActivity, CharacterActivity::class.java)
                    val id = characters.get(id.toInt()).getValue("Id").toString().toInt()
                    intent.putExtra("Id", id)
                    startActivity(intent)
                }
            }

            //если возникла ошибка при загрузке данных
            override fun onError(e: Throwable) {
                //выводим сообщение об ошибке
                Toast.makeText(
                    applicationContext,
                    "Не удалось загрузить данные",
                    Toast.LENGTH_LONG
                ).show()
            }

            //если загрузка данных успешна
            override fun onComplete() {

            }

            override fun onSubscribe(s: Subscription) {
                s.request(Long.MAX_VALUE)
            }
        }
        //Запрос API для загрузки требуемой страницы
        ListService().getInstance()?.getCharactersApi()?.getCharacters(pageNumber)
            ?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(subscriber)
    }

    //открытие следующей страницы
    fun onNextPageButton(view: View) {
        pageNumber++
        val pageNumberTextView = findViewById<TextView>(R.id.pageNumberTextView)
        pageNumberTextView.text = pageNumber.toString()
        characters.clear()
        initViews()
        podsAdapter.notifyDataSetChanged()
    }

    //открытие предыдущей страницы
    fun onPrevPageButton(view: View) {
        pageNumber--
        val pageNumberTextView = findViewById<TextView>(R.id.pageNumberTextView)
        pageNumberTextView.text = pageNumber.toString()
        characters.clear()
        initViews()
        podsAdapter.notifyDataSetChanged()
    }
}

