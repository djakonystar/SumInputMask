package site.texnopos.djakonystar.suminputmask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import site.texnopos.djakonystar.suminputmask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            SumInputMask(editText1, type = SumInputMask.NUMBER)
            SumInputMask(editText2) // NUMBER_DECIMAL is default value of type
        }
    }
}
