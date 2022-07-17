package preview.android.activity.mentorinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import preview.android.BaseActivity
import preview.android.R
import preview.android.databinding.ActivityMentorInfoBinding

class MentorInfoActivity : BaseActivity<ActivityMentorInfoBinding, MentorInfoViewModel>(
    R.layout.activity_mentor_info
) {

    override val vm: MentorInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

}