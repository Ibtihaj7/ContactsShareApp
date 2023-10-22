import android.content.SharedPreferences
import com.example.contactsshareapp.model.UserInformation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.contactsshareapp.ui.addcontact.AddNewContact

object DataRepo {
    private var allUserList: ArrayList<UserInformation>? = null
    fun getAllUserList(): ArrayList<UserInformation> {
        if (allUserList == null) {
            allUserList = ArrayList()
        }
        return allUserList!!
    }
}
