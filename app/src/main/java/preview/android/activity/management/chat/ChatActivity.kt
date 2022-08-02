package preview.android.activity.management.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.databinding.ActivityChatBinding

@AndroidEntryPoint
class ChatActivity : BaseActivity<ActivityChatBinding, ChatViewModel>(
    R.layout.activity_chat
) {
    override val vm: ChatViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController =
            (supportFragmentManager.findFragmentById(R.id.fcv_fragment) as NavHostFragment).navController

    }
}