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
import preview.android.model.ChatRoom
import preview.android.model.Message

class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>(
    R.layout.fragment_chat
) {
    override val vm: ChatViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatRoom = requireArguments().getSerializable("chatRoom")!! as ChatRoom

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
        binding.btnSend.setOnClickListener {
            if (binding.etMessage.text.toString() != "") {
                // 내가 멘토일때
                Log.e("chatroom nick", chatRoom.nickname)
                if (chatRoom.nickname.contains("멘티")) {
                    val menteeNickname = chatRoom.nickname.replace(" 멘티", "")
                    Log.e("I am mentor", "menteenick = " + menteeNickname)
                    val message = Message(
                        nickname = AccountStore.mentorNickname.value!!,
                        message = binding.etMessage.text.toString(),
                        vm.messageList.value.size
                    )
                    vm.sendChat(
                        AccountStore.mentorNickname.value!!,
                        menteeNickname,
                        message,
                        vm.messageList.value.size
                    )
                    vm.sendNotice(getFCMToken(), menteeNickname)

                    val alarmList = AlarmStore.alarmObject.value!!.value.toMutableList()
                    alarmList.add(
                        Alarm(
                            title = "${AccountStore.mentorNickname.value!!}님이 메시지를 보냈습니다.",
                            content = "지금 확인하기",
                            time = getCurrentTime()
                        )
                    )
                    alarmList.forEach {
                        Log.e("btnSend alarm Item", it.toString())
                    }
                    vm.addAlarm(menteeNickname, AlarmObject().copy(value = alarmList))
                    AlarmStore.updateAlarmList(AlarmObject().copy(value = alarmList))
                } else {
                    Log.e("I am mente", "!")
                    val mentorNickname = chatRoom.nickname.replace(" 멘토", "")
                    val message = Message(
                        nickname = AccountStore.menteeNickname.value!!,
                        message = binding.etMessage.text.toString(),
                        vm.messageList.value.size
                    )
                    vm.sendChat(
                        mentorNickname,
                        AccountStore.menteeNickname.value!!,
                        message,
                        vm.messageList.value.size
                    )
                    vm.sendNotice(getFCMToken(), mentorNickname)

                    val alarmList = AlarmStore.alarmObject.value!!.value.toMutableList()
                    alarmList.add(
                        Alarm(
                            title = "${AccountStore.menteeNickname.value!!}님이 메시지를 보냈습니다.",
                            content = "지금 확인하기",
                            time = getCurrentTime()
                        )
                    )
                    alarmList.forEach {
                        Log.e("btnSend alarm Item", it.toString())
                    }
                    vm.addAlarm(mentorNickname, AlarmObject().copy(value = alarmList))
                    AlarmStore.updateAlarmList(AlarmObject().copy(value = alarmList))
                }
            }
            // TODO: 멘토 멘티 여부에따라서 메시지 닉네임 변경할것

//            val message = Message(nickname = AccountStore.menteeNickname.value!!, message = binding.etMessage.text.toString(), vm.messageList.value!!.size) // TODO: 멘토/멘티 뭐로 설정해야하는지 확인
//            vm.sendChat(otherNickname, message,  vm.messageList.value!!.size)
//            vm.sendNotice(getFCMToken(), otherNickname)
//
//            val alarmList =  AlarmStore.alarmObject.value!!.value.toMutableList()
//            alarmList.add(Alarm(title = "${AccountStore.menteeNickname.value!!}님이 메시지를 보냈습니다.", content = "지금 확인하기", time = getCurrentTime())) // TODO: 멘토/멘티 뭐로 설정해야하는지 확인
//            alarmList.forEach {
//                Log.e("btnSend alarm Item", it.toString())
//            }
//            vm.addAlarm(otherNickname, AlarmObject().copy(value = alarmList))
//            AlarmStore.updateAlarmList(AlarmObject().copy(value = alarmList))
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

