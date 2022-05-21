package site.texnopos.djakonystar.suminputmask

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

internal class SumMaskWatcher(private val editText: EditText) : TextWatcher {
    private lateinit var before: String
    private lateinit var beforeFiltered: String
    private lateinit var after: String
    private lateinit var afterFiltered: String
    private var beforePosition = 0
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        before = p0.toString()
        beforeFiltered = before.filter { it != ' ' }
        beforePosition = editText.selectionStart
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {
        afterFiltered = p0.toString().filter { it != ' ' }
        after = afterFiltered.sumFormat

        editText.removeTextChangedListener(this)
        var position: Int

        editText.setText(after)

        if (before.notContains('.') && after.contains('.')) {
            val ssBefore = before.substring(0 until beforePosition)
            position =
                if (beforePosition == 0) 2 else ssBefore.filter { it != ' ' }.sumFormat.length + 1
        } else {
            val ss = before.substring(beforePosition)
            position = after.length - ss.length
            if (beforeFiltered == afterFiltered) position--
            if (beforeFiltered.length > afterFiltered.length && beforePosition == 1 && before.length > 1) {
                if (before[beforePosition] == ' ') position = 0
            }
        }

        editText.setSelection(position)
        editText.addTextChangedListener(this)
    }

    private fun CharSequence.notContains(char: Char, ignoreCase: Boolean = false): Boolean {
        return !this.contains(char, ignoreCase)
    }

    private val String.sumFormat: String
        get() {
            val beforePoint = this.substringBefore('.')
            var text = beforePoint.reversed()
            text = text.chunked(3).joinToString(" ")
            if (this.contains('.')) {
                val afterPoint = this.substringAfter('.')
                return "${text.reversed().ifEmpty { "0" }}.${
                if (afterPoint.length > 1) afterPoint.substring(0..1) else afterPoint
                }"
            }
            return text.reversed()
        }
}
