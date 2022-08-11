package preview.android.activity.management.menteechat.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.menteechat.MenteeChatViewModel
import preview.android.activity.management.mentorprofile.chat.ChatAdapter
import preview.android.activity.util.*
import preview.android.data.AccountStore
import preview.android.databinding.FragmentMenteeChatBinding
import preview.android.model.Alarm
import preview.android.model.AlarmObject
import preview.android.model.ChatRoom
import preview.android.model.Message
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MenteeChatFragment : BaseFragment<FragmentMenteeChatBinding, MenteeChatViewModel>(
    R.layout.fragment_mentee_chat
) {
    override val vm: MenteeChatViewModel by activityViewModels()

    lateinit var chatRoom: ChatRoom
    var mentorNickname = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatRoom = requireArguments().getSerializable("chatRoom")!! as ChatRoom
        mentorNickname = chatRoom.nickname.replace(" 멘토", "")

        binding.rvChat.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = ChatAdapter(
                onAcceptClicked = { message ->
                    Log.e("accept message", message.toString())
                    checkCalendarProgressOn(requireContext(), btnOkClicked = {
                        vm.acceptMentoring(
                            mentorNickname = mentorNickname,
                            menteeNickname = AccountStore.menteeNickname.value!!,
                            count = vm.messageList.value.size,
                            date = message.message
                        )
                    })
                },
                onEndClicked = { message ->
                    Log.e("END CLICKED",message.toString())
                    val message = Message(
                        nickname = "admin",
                        message = "멘토링이 종료되었습니다.",
                        count = vm.messageList.value.size,
                        time = getCurrentTime()
                    )
                    vm.sendChat(
                        mentorNickname,
                        AccountStore.menteeNickname.value!!,
                        message,
                        vm.messageList.value.size
                    )
                }
            ).apply {
                submitList(listOf<Message>())
            }
        }

        vm.readMessageList(mentorNickname, AccountStore.menteeNickname.value!!)

        val onSendClickListener = object : View.OnClickListener {
            override fun onClick(view: View?) {
                if(vm.messageList.value.last().nickname == "admin" && vm.messageList.value.last().message == "멘토링이 종료되었습니다."){

                }
                else if (view == binding.layoutSend || view == binding.btnSend) {
                    if (binding.etMessage.text.toString() != "") {
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

                    binding.etMessage.setText("")
                }

            }

        }
        binding.layoutSend.setOnClickListener(onSendClickListener)
        binding.btnSend.setOnClickListener(onSendClickListener)

        lifecycleScope.launch {
            vm.messageList.collect { list ->
                (binding.rvChat.adapter as ChatAdapter).submitList(list.toMutableList())
                (binding.rvChat.smoothScrollToPosition((binding.rvChat.adapter as ChatAdapter).itemCount + 1))
                // 포커스가 맨 마지막이 아니면 맨 아래로 바로 이동하지않고 아래로 내릴 수 있는 버튼으로 내리게 (카톡)
            }
        }
    }

}