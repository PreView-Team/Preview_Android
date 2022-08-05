package preview.android.activity.management.chat.fragment

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.chat.ChatAdapter
import preview.android.activity.management.chat.ChatRoomAdpater
import preview.android.activity.management.chat.ChatViewModel
import preview.android.data.AccountStore
import preview.android.databinding.FragmentChatRoomBinding
import preview.android.model.ChatRoom
import preview.android.model.Message


class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding, ChatViewModel>(
    R.layout.fragment_chat_room
) {
    override val vm: ChatViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.clearMessageMap()
        vm.clearSendFormThumbnailList()
        vm.getSendForms(AccountStore.token.value!!)
        vm.readChatRoomList(AccountStore.mentorNickname.value!!)

        binding.rvChatRoom.run {
            setHasFixedSize(true)
//            setItemViewCacheSize(10)
            adapter = ChatRoomAdpater(
                onClicked = { chatRoom ->
                    val bundle = bundleOf("chatRoom" to chatRoom)
                    findNavController().navigate(
                        R.id.action_chatRoomFragment_to_chatFragment,
                        bundle
                    )
                }
            ).apply {
                submitList(listOf<ChatRoom>())
            }
        }

        vm.sendFormThumbnailList.observe(viewLifecycleOwner) { list -> // 멘티 case 실행 2
            // 내가 보낸 신청서중에 상태가 수락인 것들의 멘토 닉네임을 다 가져옴
            list.forEach { sendFormThumbnail ->
                if (sendFormThumbnail.status == "수락") {
                    vm.readChatList(
                        sendFormThumbnail.mentorNickname,
                        AccountStore.menteeNickname.value!!
                    )
                }
            }
        }

        vm.messageMap.observe(viewLifecycleOwner) { mapList ->

            val chatRoomList = mutableListOf<ChatRoom>()
            mapList.forEach { map ->
                Log.e("map", map.toString())
                map.forEach { nickname, list ->
                    chatRoomList.add(ChatRoom(nickname, list))
                }
            }
            chatRoomList.sortBy {
                it.date
            }
            (binding.rvChatRoom.adapter as ChatRoomAdpater).submitList(chatRoomList.toMutableList())

        }


    }

}