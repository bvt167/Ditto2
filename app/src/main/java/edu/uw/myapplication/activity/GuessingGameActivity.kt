package edu.uw.myapplication.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import coil.load
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.R
import edu.uw.myapplication.databinding.ActivityGuessingGameBinding
import edu.uw.myapplication.databinding.ActivityMainBinding
import edu.uw.myapplication.databinding.ActivityPokemonDetailBinding
import edu.uw.myapplication.model.Pokemon
import edu.uw.myapplication.model.Species
import kotlinx.coroutines.launch
import java.util.*

val PREFERED_LANGUAGE_KEY = "en"

fun navigateToGuessingGameActivity(context: Context) = with(context) {
    val intent = Intent(this, GuessingGameActivity::class.java)
    startActivity(intent)
}

class GuessingGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuessingGameBinding
    private val application by lazy { applicationContext as DittoApplication }
    private val dataRepository by lazy { application.dataRepository }
    private var numberCorrect: Int = 0
    private var currentRound: Int = 1
    private val totalNumRounds: Int = 5
    private lateinit var correctAnswer: String
    private lateinit var correctPokemon: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessing_game)

        binding = ActivityGuessingGameBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            startGameBtn.setOnClickListener{
                beginGame()
            }
        }
    }

    fun resetGame() {
        with (binding) {
            tvGameResults.setText("")
            tvGameResults.visibility = View.GONE
            playAgainBtn.visibility = View.GONE
        }

        numberCorrect = 0
        currentRound = 1
        resetQuestion()
    }

    fun beginGame() {
        with (binding) {
            tvGameTitle.visibility = View.GONE
            tvGameInstructions.visibility = View.GONE
            gameInstructionsLayout.visibility = View.GONE
            startGameBtn.visibility = View.GONE
            nextQuestionBtn.visibility = View.GONE
            pokemonName.visibility = View.GONE
        }
        resetQuestion()
    }

    fun showGameResults() {
        with (binding) {
            tvGameResults.setText(getResources().getString(R.string.guess_game_results, numberCorrect, totalNumRounds));
            tvGameResults.visibility = View.VISIBLE
            playAgainBtn.visibility = View.VISIBLE

            nextQuestionBtn.visibility = View.GONE
            pokemonName.visibility = View.GONE
            pokemonName.text = ""

            descHintTv.visibility = View.GONE
            idHintTv.visibility = View.GONE
            pkmnSprite.visibility = View.GONE
            playAgainBtn.setOnClickListener {
                resetGame()
            }
        }
    }

    fun resetQuestion() {
        if (currentRound > totalNumRounds) {
            showGameResults()
        } else {
            with(binding) {
                setUpQuestion()
                nextQuestionBtn.visibility = View.GONE
                pokemonName.visibility = View.INVISIBLE
                pokemonName.text = ""

                descHintTv.visibility = View.VISIBLE
                idHintTv.visibility = View.VISIBLE
                pkmnSprite.setImageResource(R.drawable.who_pkmn)
                userGuessInput.visibility = View.VISIBLE
                makeGuessBtn.visibility = View.VISIBLE

                makeGuessBtn.setOnClickListener {
                    checkUserGuess()
                }
            }
        }

    }

    fun checkUserGuess() {
        with(binding) {
            userGuessInput.visibility = View.INVISIBLE
            var descHintTvText = descHintTv.text.toString()
            descHintTvText = descHintTvText.replace("?", correctAnswer, true) // unclassifies pkmn name in description
            descHintTv.text = descHintTvText
            correctPokemon.sprites.other.`official-artwork`?.front_default.let { pkmnSprite.load(it) }
            var input = userGuessInput.text.toString()
            input = input.lowercase().trim()

            userGuessInput.setText("")
            makeGuessBtn.visibility = View.GONE
//            Log.i("PokeHint", input)
            if (input == correctAnswer) {
//                Log.i("PokeHint", "Correct Answer!")
                Toast.makeText(this@GuessingGameActivity, "Correct!", Toast.LENGTH_SHORT).show()
                numberCorrect++
            } else {
//                Log.i("PokeHint", "Incorrect Answer!")
                Toast.makeText(this@GuessingGameActivity, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
            val pkmnName = correctAnswer.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            pokemonName.visibility = View.VISIBLE
            pokemonName.text = pkmnName
            nextQuestionBtn.visibility = View.VISIBLE
            nextQuestionBtn.setOnClickListener {
                currentRound++
                resetQuestion()
            }
        }
    }

    fun setUpQuestion() {
        with(binding) {
            descHintTv.text = ""
            idHintTv.text = ""
            pkmnSprite.visibility = View.VISIBLE
            lifecycleScope.launch {
                val randomPokemonName = dataRepository.getPokemonList().results.random().name
                val pokemon = dataRepository.getPokemon(randomPokemonName)
                correctPokemon = pokemon
//                Log.i("PokeHint", "ID: ${pokemon.name}, Name: ${pokemon.name}")
                val hints = dataRepository.getPokemonHint(randomPokemonName)
                correctAnswer = pokemon.name
                var langIndex = findLangFlavorText(hints)
                var pkmnID = pokemon.id
                var hintFlavorText = hints.flavor_text_entries[langIndex].flavor_text
                hintFlavorText = hintFlavorText.replace("\\n".toRegex(), " ") // removes new lines
                hintFlavorText = hintFlavorText.replace(correctAnswer, "?", true) // removes pokemon name in case it's in the flavor text string
                descHintTv.text = hintFlavorText
                idHintTv.text = getResources().getString(R.string.guess_game_dex_hint, pkmnID)
//                Log.i("PokeHint", hints.flavor_text_entries[langIndex].flavor_text)

            }
        }
    }

    fun findLangFlavorText(hints: Species): Int {
        var index = 0

        for (i in hints.flavor_text_entries){
//            Log.i("PokeHint", i.language.name)
            if (i.language.name == PREFERED_LANGUAGE_KEY) {
                return index
            }
            index++
        }
        return index
    }
}