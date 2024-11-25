package com.myapp.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel() {

    private val equationText = MutableLiveData("")
    val equation : LiveData<String> =equationText
    private val resultText = MutableLiveData("0")
    val result : LiveData<String> =resultText

    fun onButtonClick( btn :String){
        Log.i("Button Clicked", btn)

        equationText.value?.let{
            if (btn=="AC"){
                equationText.value=""
                resultText.value="0"
                return
            }
            if (btn=="C"){
                equationText.value=it.dropLast(1)
                return
            }
            if (btn=="="){
                equationText.value=resultText.value
                return
            }
            equationText.value=it+btn //Concat All Buttons
            //Calculate result

            Log.i("Equation", equationText.value.toString())

            try {
            calculateResult(equationText.value.toString())
        }catch ( e:Exception){

        }
    }

    }
    private fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        val result = context.evaluateString(scriptable, equation, "JavaScript", 1, null).toString()
        return if (result.endsWith(".0")) {
            result.replace(".0", "")
        } else {
            result
        }
    }

}
