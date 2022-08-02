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
import preview.android.activity.util.getFCMToken
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

        val nickname = requireArguments().getString("nickname")!!

        vm.readChatList(nickname)

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
            vm.sendChat(nickname, message,  vm.messageList.value!!.size)
        }

        binding.btnSend.setOnClickListener {
            val message = Message(nickname = AccountStore.nickname.value!!, message = binding.etMessage.text.toString(), vm.messageList.value!!.size)
            vm.sendChat(nickname, message,  vm.messageList.value!!.size)
            vm.sendNotice(getFCMToken(), nickname)
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

