package com.recon.filmaster.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.recon.filmaster.R
import com.recon.filmaster.Utils.OnSwipeTouchListener


class MatchFragment : Fragment() {

    private lateinit var card: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card = view.findViewById(R.id.card)

        card.setOnTouchListener(object : OnSwipeTouchListener(this.requireContext()) {
            @SuppressLint("ClickableViewAccessibility")
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                Toast.makeText(
                    requireContext(), "Swipe Left gesture detected",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                Toast.makeText(
                    requireContext(),
                    "Swipe Right gesture detected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

}