package com.example.horariostec

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // Lista de fragmentos (uno para cada pestaña)
    private val fragments = listOf(
        TodasMateriasFragment(),          // Primera pestaña
        MateriasSeleccionadasFragment()  // Segunda pestaña
    )

    // Número de pestañas (fragmentos)
    override fun getItemCount(): Int = fragments.size

    // Devuelve el fragmento correspondiente a la posición
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
