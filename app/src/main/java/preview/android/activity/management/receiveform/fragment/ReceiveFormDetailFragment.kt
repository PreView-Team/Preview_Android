package preview.android.activity.management.receiveform.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.receiveform.ReceiveFormViewModel
import preview.android.activity.util.getCurrentTime
import preview.android.data.AccountStore
import preview.android.data.AlarmStore
import preview.android.databinding.FragmentReceiveFormDetailBinding
import preview.android.model.Alarm
import preview.android.model.AlarmObject


class ReceiveFormDetailFragment :
    BaseFragment<FragmentReceiveFormDetailBinding, ReceiveFormViewModel>(
        R.layout.fragment_receive_form_detail
    ) {
    override val vm: ReceiveFormViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments

        vm.getReceiveFormDetail(
            AccountStore.token.value!!,
            bundle!!.getInt("formId")
        )

        binding.btnAccept.setOnClickListener {
            vm.aceeptForm(AccountStore.token.value!!, bundle.getInt("formId"))
            vm.createRoom(vm.receiveFormDetail.value!!.username, vm.receiveFormDetail.value!!.fcmToken)
            var alarmList = mutableListOf<Alarm>()
            if (AlarmStore.alarmObject.value != null) {
                alarmList = AlarmStore.alarmObject.value!!.value.toMutableList()
                alarmList.add(
                    Alarm(
                        title = "${AccountStore.mentorNickname.value!!}님이 메시지를 보냈습니다.",
                        content = "지금 확인하기",
                        time = getCurrentTime()
                    )
                )
                alarmList.forEach {
                    Log.e("alarm if Item", it.toString())
                }
            }
            else{
                alarmList.add(
                    Alarm(
                        title = "${AccountStore.mentorNickname.value!!}님이 메시지를 보냈습니다.",
                        content = "지금 확인하기",
                        time = getCurrentTime()
                    )
                )
                alarmList.forEach {
                    Log.e("alarm else Item", it.toString())
                }
            }
            vm.addAlarm(
                vm.receiveFormDetail.value!!.username,
                AlarmObject().copy(value = alarmList)
            )
            AlarmStore.updateAlarmList(AlarmObject().copy(value = alarmList))
        }

        binding.btnRefuse.setOnClickListener {
            vm.refuseForm(AccountStore.token.value!!, bundle.getInt("formId"))
        }

        vm.receiveFormDetail.observe(viewLifecycleOwner) { receiveFormDetailResponse ->
            binding.receiveformdetail = receiveFormDetailResponse
        }
    }
}