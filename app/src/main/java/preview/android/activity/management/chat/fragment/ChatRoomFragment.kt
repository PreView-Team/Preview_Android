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

        vm.readChatRoomList()
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

    }
}