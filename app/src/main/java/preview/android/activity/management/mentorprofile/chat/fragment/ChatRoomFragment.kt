package preview.android.activity.management.mentorprofile.chat.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.mentorprofile.chat.ChatRoomAdpater
import preview.android.activity.management.mentorprofile.chat.ChatViewModel
import preview.android.data.AccountStore
import preview.android.databinding.FragmentChatRoomBinding
import preview.android.model.ChatRoom


class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding, ChatViewModel>(
    R.layout.fragment_chat_room
) {
    override val vm: ChatViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.clearMessageMap()
        vm.clearSendFormThumbnailList()
        //vm.getSendForms(AccountStore.token.value!!)
        vm.readChatRoomList(AccountStore.mentorNickname.value!!)

        val layoutManager = LinearLayoutManager(requireContext())
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
                        R.id.action_chatRoomFragment_to_chatFragment,
                        bundle
                    )
                },
                onDeleteClicked = { chatRoom ->
                    val menteeNickname = chatRoom.nickname.replace(" 멘티", "")
                    vm.deleteChatRoom(AccountStore.mentorNickname.value!!, menteeNickname, chatRoom)
                }
            ).apply {
                submitList(listOf<ChatRoom>())
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
            val currentList = (binding.rvChatRoom.adapter as ChatRoomAdpater).currentList.toMutableList()
            currentList.remove(chatRoom)
            (binding.rvChatRoom.adapter as ChatRoomAdpater).submitList(currentList.toMutableList())
        }
    }

}