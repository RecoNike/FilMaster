package com.recon.filmaster.Utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.recon.filmaster.R

class CustomDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.full_info_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val filmName = arguments?.getString("filmName")
        val filmDesc = arguments?.getString("filmDesc")
        val nameField: TextView = view.findViewById(R.id.textViewTitle)
        val descriptionField : TextView = view.findViewById(R.id.textViewDescription)
        nameField.text = filmName
        descriptionField.text = filmDesc

//        val imageField: ImageView = view.findViewById(R.id.cubeImageView)
//        imageField.startAnimation(animation)
//        val imageResource = cubeImages[cubeName]
//        if (imageResource != null) {
//            imageField.setImageResource(imageResource)
//        } else {
//            // Если изображение не найдено, загрузите дефолтное изображение
//            imageField.setImageResource(R.drawable.block_placeholder)
//        }


    }

    companion object {
        fun newInstance(filmName: String, filmDesc: String) = CustomDialogFragment().apply {
            arguments = Bundle().apply {
                putString("filmName", filmName)
                putString("filmDesc", filmDesc)
            }
        }
    }


}
