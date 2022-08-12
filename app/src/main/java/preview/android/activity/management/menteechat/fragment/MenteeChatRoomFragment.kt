package preview.android.activity.management.menteechat.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.menteechat.MenteeChatViewModel
import preview.android.activity.management.mentorprofile.chat.ChatRoomAdpater
import preview.android.activity.management.mentorprofile.chat.ChatViewModel
import preview.android.activity.management.mentorprofile.chat.fragment.ChatRoomDecoration
import preview.android.data.AccountStore
import preview.android.databinding.FragmentMenteeChatroomBinding
import preview.android.model.ChatRoom

@AndroidEntryPoint
class MenteeChatRoomFragment : BaseFragment<FragmentMenteeChatroomBinding, MenteeChatViewModel>(
    R.layout.fragment_mentee_chatroom
) {
    override val vm: MenteeChatViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())

        vm.getSendForms(AccountStore.token.value!!)

        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvChatRoom.layoutManager = layoutManager
        binding.rvChatRoom.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(ChatRoomDecoration(requireContext()))
            adapter = ChatRoomAdpater(
                onClicked = { chatRoom ->
                    val bundle = bundleOf("chatRoom" to chatRoom)
                    findNavController().navigate(
                        R.id.action_menteeChatRoomFragment_to_menteeChatFragment,
                        bundle
                    )
                },
                onDeleteClicked = { chatRoom ->
                    val mentorNickname = chatRoom.nickname.replace(" 멘토", "")
                    vm.deleteChatRoom(mentorNickname, AccountStore.menteeNickname.value!!, chatRoom)
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
            if (mapList.isNotEmpty()) {
                val chatRoomList = mutableListOf<ChatRoom>()
                mapList.forEach { map ->
                    map.forEach { nickname, list ->
                        chatRoomList.add(
                            ChatRoom(
                                nickname = nickname,
                                chatList = list,
                                date = ""+list.last().time
                            )
                        )
                    }
                }
                (binding.rvChatRoom.adapter as ChatRoomAdpater).submitList(chatRoomList.toMutableList())

            }
        }

        vm.deleteResponse.observe(viewLifecycleOwner){ chatRoom ->
            Log.e("deleteResponse", chatRoom.toString())
            val currentList = (binding.rvChatRoom.adapter as ChatRoomAdpater).currentList.toMutableList()
            currentList.remove(chatRoom)
            (binding.rvChatRoom.adapter as ChatRoomAdpater).submitList(currentList.toMutableList())
        }
    }

}