package preview.android.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import preview.android.activity.util.getCurrentTime
import preview.android.activity.util.getFCMToken
import preview.android.data.AccountStore
import kotlin.coroutines.resume
import preview.android.model.Message
import java.util.HashMap

class ChatRepository {

    val database = Firebase.database

    //val myRef = database.getReference(AccountStore.nickname.value!!)

    fun createChatRoom(menteeNickname: String, menteeFCMToken: String) = callbackFlow {
        val myRef = database.getReference(AccountStore.mentorNickname.value!!)
        val createList = arrayListOf<Message>()
        createList.add(
            Message(
                nickname = "admin",
                message = "새로운 채팅이 시작되었습니다",
                count = 0,
                menteeToken = menteeFCMToken,
                mentorToken = AccountStore.myFCMToken.value!!,
                time = getCurrentTime()
            )
        ) // menteeToken = 멘티 fcmToken
        myRef.child(menteeNickname).setValue(createList).addOnSuccessListener {
            trySend("success")
        }.addOnFailureListener {
            trySend("fail")
        }
        close()
    }

    suspend fun readMentorsChatRoom(mentorNickname: String) = callbackFlow {
        val myRef = database.getReference(mentorNickname)
        myRef.addValueEventListener(object : ValueEventListener { // 들어온 닉네임으로 되어있는 realtimeDB에서 읽어옴
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val chatRoomList = arrayListOf<String>()
                    dataSnapshot.children.forEach { children ->
                        // key값 -> 닉네임
                        if (children.key != null) {
                            chatRoomList.add(children.key!!)
                        }
                    }
                    Log.e("READCHATROOM", chatRoomList.toString())
                    trySend(chatRoomList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                // Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                trySend(arrayListOf())
            }
        })
        awaitClose()
    }


    suspend fun readChat(mentorNickname: String, menteeNickname: String) = callbackFlow {
        val myRef = database.getReference(mentorNickname)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val list = arrayListOf<Message>()
                    dataSnapshot.child(menteeNickname).children.forEach { dataSnapshot ->
                        val map = dataSnapshot.value as HashMap<String, String>
                        list.add(
                            Message(
                                nickname = map.get("nickname")!!,
                                message = map.get("message")!!,
                                count = list.size,
                                mentorToken = map.get("mentorToken")!!,
                                menteeToken = map.get("menteeToken")!!,
                                time = map.get("time")!!
                            )
                        )

                    }
                    trySend(list)
                    // vm.updateMessageList(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
        awaitClose()
    }

    fun sendChat(mentorNickname: String, menteeNickname: String, message: Message, count: Int) =
        callbackFlow {
            val myRef = database.getReference(mentorNickname)
            myRef.child(menteeNickname + "/" + count).setValue(message).addOnSuccessListener {
                trySend("success")
            }.addOnFailureListener {
                trySend("fail")
            }
            close()
        }
}