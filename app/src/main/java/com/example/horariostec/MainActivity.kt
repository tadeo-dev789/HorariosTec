package com.example.horariostec

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Establece el diseño principal de la actividad
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Configuracion del TabLayout y ViewPager2
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager2)

        //Configura el adaptador para manejar los fragmentos

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter  = adapter

        //Asociando el TabLayout con el ViewPager2
        TabLayoutMediator(tabLayout,viewPager){tab,position ->
            tab.text = when (position){
                0 -> "Todas las materias"
                1 -> "Seleccionadas"
                else -> null
            }
        }.attach()

    }
}