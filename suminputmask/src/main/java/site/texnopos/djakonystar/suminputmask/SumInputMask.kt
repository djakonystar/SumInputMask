package site.texnopos.djakonystar.suminputmask

import android.text.InputFilter
import android.widget.EditText

class SumInputMask(vararg editTexts: EditText, private val type: Int = NUMBER_DECIMAL) {
    companion object {
        const val NUMBER = 0
        const val NUMBER_DECIMAL = 1
    }

    init {
        editTexts.forEach {
            it.setFilter()
            it.addTextChangedListener(SumMaskWatcher(it))
        }
    }

    private fun EditText.setFilter() = when (type) {
        NUMBER -> {
            val filter = InputFilter { source, _, _, _, _, _ ->
                if (source != null && ".,-".contains("" + source)) "" else null
            }
            this.filters = arrayOf(filter)
        }
        NUMBER_DECIMAL -> {
            val filter = InputFilter { source, _, _, spanned, _, _ ->
                val afterPoint = if (spanned.contains('.')) {
                    spanned.toString().substringAfter('.').length
                } else {
                    0
                }

                val range = this.length() - 2..this.length()

                if (source != null && source.equals(".") && spanned.contains(".")) ""
                else if (source != null && afterPoint == 2 && this.selectionEnd in range) ""
                else if (source != null && source.equals(".") && spanned.isEmpty()) "0."
                else if (source != null && source.equals(".") && spanned.isNotEmpty()) "."
                else if (source != null && "-,.".contains("" + source)) ""
                else null
            }
            this.filters = arrayOf(filter)
        }
        else -> throw IllegalArgumentException("Unknown type parameter")
    }
}
