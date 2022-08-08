package preview.android.activity.management.mentorprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.management.mentorprofile.chat.ChatActivity
import preview.android.activity.management.mentorprofile.editprofile.EditProfileActivity
import preview.android.activity.management.mentorprofile.receiveform.ReceiveFormActivity
import preview.android.activity.management.mentorprofile.writepost.WritePostActivity
import preview.android.databinding.ActivityMentorProfileBinding

class MentorProfileActivity : BaseActivity<ActivityMentorProfileBinding, MainViewModel>(
    R.layout.activity_mentor_profile
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.layoutProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        binding.layoutReceiveForm.setOnClickListener {
            val intent = Intent(this, ReceiveFormActivity::class.java)
            startActivity(intent)
        }
        binding.layoutChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        binding.layoutWritePosts.setOnClickListener {
            val intent = Intent(this, WritePostActivity::class.java)
            startActivity(intent)
        }


    }

    override val vm: MainViewModel by viewModels()
}