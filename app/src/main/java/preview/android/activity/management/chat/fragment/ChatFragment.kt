package preview.android.activity.management.chat.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.chat.ChatAdapter
import preview.android.activity.management.chat.ChatViewModel
import preview.android.data.AccountStore
import preview.android.databinding.FragmentChatBinding
import preview.android.model.Message
import java.util.HashMap

class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>(
    R.layout.fragment_chat
) {
    override val vm: ChatViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments

        val database = Firebase.database
        val myRef = database.getReference("mentorNickName")
        val nickname = bundle!!.getString("nickname")!!
//        val token = AccountStore.token.value.toString()
//        val createList = arrayListOf<Message>()
//        createList.add(Message(nickname = "admin", message = "새로운 채팅이 시작되었습니다"))
//        myRef.child(nickname).setValue(createList)


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val list = arrayListOf<Message>()
                    dataSnapshot.child(nickname).children.forEach { dataSnapshot ->
                        val map = dataSnapshot.value as HashMap<String, String>
                        list.add(
                            Message(
                                nickname = map.get("nickname")!!,
                                message = map.get("message")!!,
                                count = list.size
                            )
                        )

                    }
                    Log.e("list value1", list.size.toString())
                    Log.e("list foreach", list.toString())
                    vm.updateMessageList(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })

        binding.rvChat.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = ChatAdapter(
            ).apply {
                submitList(listOf<Message>())
            }
        }

        binding.btnSendOther.setOnClickListener {
            val message = Message(nickname = "other", message = binding.etMessage.text.toString(), vm.messageList.value!!.size)
            myRef.child(nickname + "/" + vm.messageList.value!!.size).setValue(message)
        }

        binding.btnSend.setOnClickListener {
            val message = Message(nickname = AccountStore.nickname.value!!, message = binding.etMessage.text.toString(), vm.messageList.value!!.size)
            myRef.child(nickname + "/" + vm.messageList.value!!.size).setValue(message)
        }
        lifecycleScope.launch {
            vm.messageList.collect { list ->
                Log.e("CHATFRAMGNET LISt", list.toString())
                (binding.rvChat.adapter as ChatAdapter).submitList(list.toMutableList())
                (binding.rvChat.smoothScrollToPosition((binding.rvChat.adapter as ChatAdapter).itemCount + 1))
            }
        }

    }
}

