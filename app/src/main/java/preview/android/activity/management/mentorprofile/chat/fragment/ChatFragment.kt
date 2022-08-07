package preview.android.activity.management.mentorprofile.chat.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.mentorprofile.chat.ChatAdapter
import preview.android.activity.management.mentorprofile.chat.ChatViewModel
import preview.android.activity.util.getCurrentTime
import preview.android.data.AccountStore
import preview.android.databinding.FragmentChatBinding
import preview.android.model.Alarm
import preview.android.model.AlarmObject
import preview.android.model.ChatRoom
import preview.android.model.Message

class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>(
    R.layout.fragment_chat
) {
    override val vm: ChatViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatRoom = requireArguments().getSerializable("chatRoom")!! as ChatRoom
        Log.e("chatRoom", chatRoom.toString())
        binding.rvChat.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = ChatAdapter(
            ).apply {
                submitList(listOf<Message>())
            }
        }
        if (chatRoom.nickname.contains("멘티")) {
            val menteeNickname = chatRoom.nickname.replace(" 멘티", "")
            vm.readMessageList(AccountStore.mentorNickname.value!!, menteeNickname)
            (binding.rvChat.adapter as ChatAdapter).setMentoredTrue()
        } else {
            val mentorNickname = chatRoom.nickname.replace(" 멘토", "")
            vm.readMessageList(mentorNickname, AccountStore.menteeNickname.value!!)
        }
        binding.layoutSend.setOnClickListener {
            if (binding.etMessage.text.toString() != "") {

                Log.e("mentorToken!!", vm.messageList.value.get(0).mentorToken)
                Log.e("menteeToken!!", vm.messageList.value.get(0).menteeToken)
                if (chatRoom.nickname.contains("멘티")) {
                    Log.e("i am mentor", "!!")
                    val menteeNickname = chatRoom.nickname.replace(" 멘티", "")
                    val message = Message(
                        nickname = AccountStore.mentorNickname.value!!,
                        message = binding.etMessage.text.toString(),
                        count = vm.messageList.value.size,
                        time = getCurrentTime()
                    )
                    vm.sendChat(
                        AccountStore.mentorNickname.value!!,
                        menteeNickname,
                        message,
                        vm.messageList.value.size
                    )

                    // 알람
                    vm.sendNotice(
                        vm.messageList.value.get(0).menteeToken, //상대방 토큰으로
                        AccountStore.mentorNickname.value!!, //내 이름으로 보냄
                        binding.etMessage.text.toString()
                    )

                    val alarmList = mutableListOf<Alarm>()
                    alarmList.add(
                        Alarm(
                            title = "${AccountStore.mentorNickname.value!!}님이 메시지를 보냈습니다.",
                            content = "지금 확인하기",
                            time = getCurrentTime()
                        )
                    )
                    vm.addAlarm(menteeNickname, AlarmObject().copy(value = alarmList))

                } else {
                    Log.e("i am mentor", "!!")
                    val mentorNickname = chatRoom.nickname.replace(" 멘토", "")
                    val message = Message(
                        nickname = AccountStore.menteeNickname.value!!,
                        message = binding.etMessage.text.toString(),
                        count = vm.messageList.value.size,
                        time = getCurrentTime()
                    )
                    vm.sendChat(
                        mentorNickname,
                        AccountStore.menteeNickname.value!!,
                        message,
                        vm.messageList.value.size
                    )

                    // 알람은 상대방꺼로
                    vm.sendNotice(
                        vm.messageList.value.get(0).mentorToken, // 상대방 토큰
                        AccountStore.menteeNickname.value!!, // 내 이름
                        binding.etMessage.text.toString()
                    )

                    val alarmList = mutableListOf<Alarm>()
                    alarmList.add(
                        Alarm(
                            title = "${AccountStore.menteeNickname.value!!}님이 메시지를 보냈습니다.",
                            content = "지금 확인하기",
                            time = getCurrentTime()
                        )
                    )
                    vm.addAlarm(mentorNickname, AlarmObject().copy(value = alarmList))


                }
            }

        }
        lifecycleScope.launch {
            vm.messageList.collect { list ->
                (binding.rvChat.adapter as ChatAdapter).submitList(list.toMutableList())
                (binding.rvChat.smoothScrollToPosition((binding.rvChat.adapter as ChatAdapter).itemCount + 1))
                // 포커스가 맨 마지막이 아니면 맨 아래로 바로 이동하지않고 아래로 내릴 수 있는 버튼으로 내리게 (카톡)
            }
        }
    }

}

