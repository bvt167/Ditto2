package edu.uw.myapplication.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.uw.myapplication.BuildConfig
import edu.uw.myapplication.BuildConfig.VERSION_NAME
import edu.uw.myapplication.databinding.ActivityAboutBinding

private lateinit var binding: ActivityAboutBinding

fun navigateToAbout(context: Context) {
    val intent = Intent(context, AboutActivity::class.java)
    context.startActivity(intent)
}
class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        with(binding) {
            versionOf.text = BuildConfig.VERSION_NAME
        }
    }
}