package edu.uw.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.adapter.PokeListAdapter
import edu.uw.myapplication.R
import edu.uw.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val application by lazy { applicationContext as DittoApplication }
    private val dataRepository by lazy { application.dataRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding){
            val list = runBlocking { dataRepository.getPokemonList() }
            val adapter = PokeListAdapter(list)

            fullPokeList.adapter = adapter

            adapter.onPokemonClickListener = { name ->
                lifecycleScope.launch {
                    navigateToPokemonDetailActivity(this@MainActivity, name)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // add action buttons
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_profile -> {
            navigateToProfileActivity(this@MainActivity)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}