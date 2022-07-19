package preview.android.activity.mentorinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import preview.android.BaseActivity
import preview.android.R
import preview.android.databinding.ActivityMentorInfoBinding

class MentorInfoActivity : BaseActivity<ActivityMentorInfoBinding, MentorInfoViewModel>(
    R.layout.activity_mentor_info
) {

    override val vm: MentorInfoViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController =
            (supportFragmentManager.findFragmentById(R.id.fcv_fragment) as NavHostFragment).navController

    }

}