package edu.uw.myapplication.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import coil.load
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.R
import edu.uw.myapplication.databinding.ActivityProfileBinding
import kotlin.random.Random

fun navigateToProfileActivity(context: Context) {
    val intent = Intent(context, ProfileActivity::class.java)
    context.startActivity(intent)
}

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val application by lazy { applicationContext as DittoApplication }
    private val idValue = Random.nextInt(1001) + 1000
    private val scoreValue = Random.nextInt(51)
    private var selectedImgVal = "first"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding) {
            ibChangeProfileImg.setOnClickListener{ changeProfileImg(binding) }
            tvIDNoValue.text = idValue.toString()
            tvScoreValue.text = scoreValue.toString()
        }
    }

    private fun changeProfileImg(binding: ActivityProfileBinding) {
        with(binding) {
            vChooseNewContainer.visibility = View.VISIBLE
            tvChooseProfileImg.visibility = View.VISIBLE
            ibFirstImg.visibility = View.VISIBLE
            ibSecondImg.visibility = View.VISIBLE
            btnApplyChange.visibility = View.VISIBLE

            ibFirstImg.setOnClickListener{ selectImg(binding, "first") }
            ibSecondImg.setOnClickListener{ selectImg(binding, "second") }
            btnApplyChange.setOnClickListener{ closeSelect(binding) }
        }
    }

    private fun selectImg(binding: ActivityProfileBinding, selected: String) {
        with(binding) {

            selectedImgVal = if (selected == "first") "first" else "second"
            var firstBackground = if (selected == "first") R.drawable.rounded_corners else R.color.white
            var secondBackground = if (selected == "second") R.drawable.rounded_corners else R.color.white

            ibFirstImg.setBackgroundResource(firstBackground)
            ibSecondImg.setBackgroundResource(secondBackground)
        }
    }

    private fun closeSelect(binding: ActivityProfileBinding) {
        with(binding) {

            var newProfileImg = if (selectedImgVal == "first") {
                Log.i("ProfileActivity", "First image is selected")
                R.drawable.trainer_ash
            } else {
                Log.i("ProfileActivity", "Second image is selected")
                R.drawable.trainer_brock
            }

            ivProfileImage.load(newProfileImg)
            vChooseNewContainer.visibility = View.GONE
            tvChooseProfileImg.visibility = View.GONE
            ibFirstImg.visibility = View.GONE
            ibSecondImg.visibility = View.GONE
            btnApplyChange.visibility = View.GONE
        }
    }
}