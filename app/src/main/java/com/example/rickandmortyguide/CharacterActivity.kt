package com.example.rickandmortyguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class CharacterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        initViews()
        //добавление в ActionBar кнопки возврата на предущее активити
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }
    //обработка нажатия на кнопку возврата на предыдшуее активити
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    //получение id персонажа, на которого нажали в списке
    fun getId(): Int {
        return intent.extras?.getInt("Id").toString().toInt()
    }

    fun initViews() {
        val id: Int = getId()
        val subscriber: Subscriber<ResultCharacter> = object : Subscriber<ResultCharacter> {
            //загрузка данных
            override fun onNext(@NonNull character: ResultCharacter) {
                //вывод данных о персонаже
                val nameTextView: TextView = findViewById(R.id.nameTextView)
                nameTextView.text = character.name
                val genderTextView: TextView = findViewById(R.id.genderTextView)
                genderTextView.text = character.gender
                val speciesTextView: TextView = findViewById(R.id.speciesTextView)
                speciesTextView.text = character.species
                val statusTextView: TextView = findViewById(R.id.statusTextView)
                statusTextView.text = character.status
                val locationTextView: TextView = findViewById(R.id.locationTextView)
                locationTextView.text = character.location.get("name")
                val episodeTextView: TextView = findViewById(R.id.episodeTextView)
                episodeTextView.text = character.episode.size.toString()
                val imageView: ImageView = findViewById(R.id.avatarView)
                Picasso.get().load(character.image).into(imageView)
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
        //Запрос API для загрузки требуемого персонажа
        CharacterService().getInstance()?.getCharacterApi()?.getCharacter(id)
            ?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(subscriber)
    }
}