package preview.android.activity.management.chat.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.chat.ChatAdapter
import preview.android.activity.management.chat.ChatViewModel
import preview.android.activity.util.getCurrentTime
import preview.android.activity.util.getFCMToken
import preview.android.data.AccountStore
import preview.android.data.AlarmStore
import preview.android.databinding.FragmentChatBinding
import preview.android.model.Alarm
import preview.android.model.AlarmObject
import preview.android.model.Message

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

            val alarmList =  AlarmStore.alarmObject.value!!.value.toMutableList()
            alarmList.add(Alarm(title = "${AccountStore.nickname.value!!}님이 메시지를 보냈습니다.", content = "지금 확인하기", time = getCurrentTime()))
            alarmList.forEach {
                Log.e("btnSend alarm Item", it.toString())
            }
            vm.addAlarm(nickname, AlarmObject().copy(value = alarmList))
            AlarmStore.updateAlarmList(AlarmObject().copy(value = alarmList))
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

