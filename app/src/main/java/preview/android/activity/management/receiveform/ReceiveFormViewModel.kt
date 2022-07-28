package preview.android.activity.management.receiveform

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class ReceiveFormViewModel @Inject constructor(
    private val mentorRepository: MentorRepository
) : BaseViewModel() {

    fun getReceiveForms() = viewModelScope.launch{
        //mentorRepository
    }

}