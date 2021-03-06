package preview.android.activity.error

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import preview.android.R
import preview.android.activity.main.MainActivity
import preview.android.databinding.ActivityErrorBinding

class ErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityErrorBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lastActivityIntent = intent.getParcelableExtra<Intent>(EXTRA_INTENT)
        val errorText = intent.getStringExtra(EXTRA_ERROR_TEXT)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_error)
        Log.e("onCreate", "!!")

        binding.tvErrorLog.text = errorText

        binding.btnRefresh.setOnClickListener {
            val intent = Intent()
            intent.setComponent(lastActivityIntent!!.component)
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRA_INTENT = "EXTRA_INTENT"
        const val EXTRA_ERROR_TEXT = "EXTRA_ERROR_TEXT"
    }

}