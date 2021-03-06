package com.dev.peralta.calculadoracidadao.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dev.peralta.calculadoracidadao.R
import kotlinx.android.synthetic.main.fragment_exemplo.view.*

class ExemploFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exemplo, container, false)
        val url = arguments?.getString("url") ?: ""
        view.webView.loadUrl(url)
        return view
    }
}
