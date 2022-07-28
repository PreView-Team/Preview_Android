package preview.android.activity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import preview.android.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}
// 닉네임 설정 -> 중복확인
// 직군설정
