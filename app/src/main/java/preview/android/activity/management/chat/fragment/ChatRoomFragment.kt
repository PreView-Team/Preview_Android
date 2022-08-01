package preview.android.activity.management.chat.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.chat.ChatRoomAdpater
import preview.android.activity.management.chat.ChatViewModel
import preview.android.data.AccountStore
import preview.android.databinding.FragmentChatRoomBinding
import preview.android.model.Message


class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding, ChatViewModel>(
    R.layout.fragment_chat_room
) {
    override val vm: ChatViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })

        binding.rvChatRoom.run {
//            setHasFixedSize(true)
//            setItemViewCacheSize(10)
            adapter = ChatRoomAdpater(
                onClicked = { nickname ->
                    val bundle = bundleOf("nickname" to nickname)
                    findNavController().navigate(R.id.action_chatRoomFragment_to_chatFragment, bundle)

                }
            ).apply {
                submitList(listOf<String>())
            }
        }

        vm.chatRoomList.observe(viewLifecycleOwner) { list ->
            Log.e("list", list.toString())
            (binding.rvChatRoom.adapter as ChatRoomAdpater).submitList(list.toMutableList())
        }

        binding.btnCreate.setOnClickListener {
            val message = Message(nickname = "0729", message = "", count = 0)
            myRef.child("07292" + "/" + count).setValue(message)
        }

    }
}