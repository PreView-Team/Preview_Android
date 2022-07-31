package preview.android.activity.management.chat

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.management.chat.fragment.ChatFragment
import preview.android.activity.management.receiveform.ReceiveFormAdapter
import preview.android.activity.management.receiveform.fragment.ReceiveFormDetailFragment
import preview.android.data.AccountStore
import preview.android.data.MentorInfoStore
import preview.android.databinding.ActivityChatBinding
import preview.android.model.Message
import java.util.HashMap
import java.util.stream.Collectors

class ChatActivity : BaseActivity<ActivityChatBinding, ChatViewModel>(
    R.layout.activity_chat
) {
    override val vm: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Firebase.database
        val myRef = database.getReference("mentorNickName")
        val nickname = AccountStore.nickname.value.toString()
        var count = 0
//        val token = AccountStore.token.value.toString()
//        val createList = arrayListOf<Message>()
//        createList.add(Message(nickname = "admin", message = "새로운 채팅이 시작되었습니다"))
//        myRef.child(nickname).setValue(createList)



        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val chatRoomList = arrayListOf<String>()
                    dataSnapshot.children.forEach { children ->
                        // key값 -> 닉네임
                        if (children.key != null) {
                            chatRoomList.add(children.key!!)
                        }
                    }
                    vm.updateChatRoomList(chatRoomList)
                }

                //vm.updateMessageList(hashMap.values.toList())

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        binding.rvChatRoom.run {
//            setHasFixedSize(true)
//            setItemViewCacheSize(10)
            adapter = ChatRoomAdpater(
                onClicked = { nickname ->
                    val bundle = Bundle()
                    bundle.putString("nickname", nickname)
                    val fragment = ChatFragment()
                    fragment.arguments = bundle

                    supportFragmentManager.beginTransaction()
                        .add(R.id.layout_chat, fragment)
                        .commit()
                }
            ).apply {
                submitList(listOf<String>())
            }
        }

        vm.chatRoomList.observe(this) { list ->
            Log.e("list", list.toString())
            (binding.rvChatRoom.adapter as ChatRoomAdpater).submitList(list.toMutableList())
        }

        binding.btnCreate.setOnClickListener {
            val message = Message(nickname = "0729", message = "", count = 0)
            myRef.child("07292" + "/" + count).setValue(message)
        }
    }
}