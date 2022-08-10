package preview.android.activity.management.menteechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.management.mentorprofile.chat.ChatViewModel
import preview.android.databinding.ActivityMenteeChatBinding

@AndroidEntryPoint
class MenteeChatActivity : BaseActivity<ActivityMenteeChatBinding, MenteeChatViewModel>(
    R.layout.activity_mentee_chat
) {
    override val vm: MenteeChatViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController =
            (supportFragmentManager.findFragmentById(R.id.fcv_fragment) as NavHostFragment).navController

    }
}