package com.radionov.tfcontests.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int,
    var email: String?,
    var birthday: String?,
    var region: String?,
    val avatar: String?,
    var school: String?,
    var university: String?,
    var faculty: String?,
    var department: String?,
    var resume: String?,
    @SerializedName("school_graduation") var schoolGraduation: Int?,
    @SerializedName("university_graduation") var universityGraduation: Int?,
    @SerializedName("current_work") var currentWork: String?,
    @SerializedName("phone_mobile") var phone: String?,
    @SerializedName("t_shirt_size") var shirtSize: String?,
    @SerializedName("first_name") var firstName: String?,
    @SerializedName("last_name") var lastName: String?,
    @SerializedName("middle_name") var middleName: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(email)
        parcel.writeString(birthday)
        parcel.writeString(region)
        parcel.writeString(avatar)
        parcel.writeString(school)
        parcel.writeString(university)
        parcel.writeString(faculty)
        parcel.writeString(department)
        parcel.writeString(resume)
        parcel.writeValue(schoolGraduation)
        parcel.writeValue(universityGraduation)
        parcel.writeString(currentWork)
        parcel.writeString(phone)
        parcel.writeString(shirtSize)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(middleName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }


}

data class UserWithStatus(
    val user: User,
    val status: String = "Ok"
)
