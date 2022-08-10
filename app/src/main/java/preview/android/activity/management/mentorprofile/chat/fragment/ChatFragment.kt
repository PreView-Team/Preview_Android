package preview.android.activity.management.mentorprofile.chat.fragment

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.mentorprofile.chat.ChatAdapter
import preview.android.activity.management.mentorprofile.chat.ChatViewModel
import preview.android.activity.util.*
import preview.android.data.AccountStore
import preview.android.databinding.FragmentChatBinding
import preview.android.model.Alarm
import preview.android.model.AlarmObject
import preview.android.model.ChatRoom
import preview.android.model.Message
import java.text.SimpleDateFormat
import java.util.*


class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>(
    R.layout.fragment_chat
) {
    override val vm: ChatViewModel by activityViewModels()
    lateinit var chatRoom: ChatRoom
    var menteeNickname = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatRoom = requireArguments().getSerializable("chatRoom")!! as ChatRoom
        menteeNickname = chatRoom.nickname.replace(" 멘티", "")

        binding.rvChat.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = ChatAdapter(
                onAcceptClicked = { message ->
                    Log.e("accept message", message.toString())
                    checkCalendarProgressOn(requireContext(), btnOkClicked = {
                        vm.acceptMentoring(
                            mentorNickname = AccountStore.mentorNickname.value!!,
                            menteeNickname = menteeNickname,
                            count = vm.messageList.value.size,
                            date = message.message
                        )
                    })
                }
            ).apply {
                submitList(listOf<Message>())
            }
        }
            vm.readMessageList(AccountStore.mentorNickname.value!!, menteeNickname)
            (binding.rvChat.adapter as ChatAdapter).setMentoredTrue()
        val onSendClickListener = object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (view == binding.layoutSend || view == binding.btnSend) {
                    if (binding.etMessage.text.toString() != "") {
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
                    }
                    binding.etMessage.setText("")
                }
            }
        }
        binding.layoutSend.setOnClickListener(onSendClickListener)
        binding.btnSend.setOnClickListener(onSendClickListener)

        binding.fabCalendar.setOnClickListener {
            if (isFabOpened(binding.fabCalendar)) {
                changeFabClose(binding.fabCalendar, binding.layoutFabClick, R.drawable.ic_baseline_calendar_check)
            } else {
                changeFabOpen(binding.fabCalendar, binding.layoutFabClick)
            }
        }

        binding.btnCreateCalendar.setOnClickListener {
            showDatePicker()
        }
        binding.btnEditCalendar.setOnClickListener {
            showDatePicker()
        }

        binding.btnCompleteMentoring.setOnClickListener {
            // 종료요청 보내기
        }

        lifecycleScope.launch {
            vm.messageList.collect { list ->
                (binding.rvChat.adapter as ChatAdapter).submitList(list.toMutableList())
                (binding.rvChat.smoothScrollToPosition((binding.rvChat.adapter as ChatAdapter).itemCount + 1))
                // 포커스가 맨 마지막이 아니면 맨 아래로 바로 이동하지않고 아래로 내릴 수 있는 버튼으로 내리게 (카톡)
            }
        }
    }

    private fun showDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("날짜 선택하기")
                .build()
        val fragmentManager = requireActivity().supportFragmentManager
        datePicker.show(fragmentManager, "tag")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy")
            val date = Date()
            date.setTime(it)
            val dateValue = dateFormat.format(date)
            Log.e("pos it", it.toString())
            showTimePicker(dateValue)
        }
        datePicker.addOnNegativeButtonClickListener {
            Log.e("NEG", "!!")
        }
    }

    private fun showTimePicker(date: String) {
        val listener =
            OnTimeSetListener { view, hourOfDay, minute ->
                Log.e("listener date", "" + date)
                Log.e("listener hour", "" + hourOfDay)
                Log.e("listener minute", "" + minute)
                if (chatRoom.nickname.contains("멘티")) {
                    val menteeNickname = chatRoom.nickname.replace(" 멘티", "")
                    val message = Message(
                        nickname = "calendar",
                        message = "날짜$date-시간$hourOfDay:$minute",
                        count = vm.messageList.value.size,
                        time = getCurrentTime()
                    )
                    vm.createMentoringDate(
                        AccountStore.mentorNickname.value!!,
                        menteeNickname,
                        message,
                        vm.messageList.value.size
                    )
                } else {
                    val mentorNickname = chatRoom.nickname.replace(" 멘토", "")
                    val message = Message(
                        nickname = AccountStore.menteeNickname.value!!,
                        message = "날짜$date 시간$hourOfDay:$minute",
                        count = vm.messageList.value.size,
                        time = getCurrentTime()
                    )
                    vm.createMentoringDate(
                        mentorNickname,
                        AccountStore.menteeNickname.value!!,
                        message,
                        vm.messageList.value.size
                    )
                }
            }

        val dialog = TimePickerDialog(
            requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
            listener,
            15,
            24,
            false
        )
        dialog.setTitle("시간 선택")
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

}

