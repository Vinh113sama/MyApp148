package com.example.myapp.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapp.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var imgbtnMenu: ImageButton
    private lateinit var imgbtnSearch: ImageButton
    private lateinit var edtSearch: EditText
    private lateinit var tvPopularSongs: TextView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        setupView()
    }

    private fun setupView() {
        imgbtnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    private fun initViews() {
        imgbtnMenu = binding.imgbtnMenu
        imgbtnSearch = binding.imgbtnSearch
        edtSearch = binding.edtSearch
        tvPopularSongs = binding.labelPopularSongs
        drawerLayout = (binding.drawerLayout)
        imgbtnMenu = (binding.imgbtnMenu)

    }
}