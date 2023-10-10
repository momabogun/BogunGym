package com.example.bogungym.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.adapter.SupplementsAdapter
import com.example.bogungym.databinding.FragmentSupplementsBinding

class SupplementsFragment : Fragment() {


    private val viewModel: ExercisesViewModel by activityViewModels()


    private lateinit var binding: FragmentSupplementsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupplementsBinding.inflate(inflater, container, false)
        return binding.root


    }


    private fun hideKeyboard(context: Context, editText: EditText) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val adapter = SupplementsAdapter(emptyList())


        binding.supplementsRV.adapter = adapter

        viewModel.supplements.observe(viewLifecycleOwner) {
            adapter.newData(it)
        }




//        binding.searchView.setOnEditorActionListener { v, actionId, event ->
//
//            if (actionId == EditorInfo.IME_ACTION_SEARCH
//                || actionId == EditorInfo.IME_ACTION_SEND
//                || (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
//            ) {
//                hideKeyboard(requireContext(),binding.searchView)
//                return@setOnEditorActionListener true
//            }
//            // Return true if you have consumed the action, else false.
//            false
//        }












        binding.searchView.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.userInput(s.toString())

            }
        })


    }


}