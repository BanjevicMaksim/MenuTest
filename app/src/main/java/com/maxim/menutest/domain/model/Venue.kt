package com.maxim.menutest.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Venue(
    @SerializedName("is_open")
    val isOpen: Boolean,
    @SerializedName("welcome_message")
    val welcomeMessage: String,
    val name: String,
    val description: String,
    val image: VenueImage?,
    val address: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(VenueImage::class.java.classLoader, VenueImage::class.java),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isOpen) 1 else 0)
        parcel.writeString(welcomeMessage)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeParcelable(image, flags)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Venue> {
        override fun createFromParcel(parcel: Parcel): Venue {
            return Venue(parcel)
        }

        override fun newArray(size: Int): Array<Venue?> {
            return arrayOfNulls(size)
        }
    }
}

data class VenueData(
    val distance: Double,
    val venue: Venue?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readParcelable(Venue::class.java.classLoader, Venue::class.java)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(distance)
        parcel.writeParcelable(venue, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VenueData> {
        override fun createFromParcel(parcel: Parcel): VenueData {
            return VenueData(parcel)
        }

        override fun newArray(size: Int): Array<VenueData?> {
            return arrayOfNulls(size)
        }
    }
}

data class VenueImage(
    @SerializedName("thumbnail_medium")
    val thumbnail: String
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "") {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VenueImage> {
        override fun createFromParcel(parcel: Parcel): VenueImage {
            return VenueImage(parcel)
        }

        override fun newArray(size: Int): Array<VenueImage?> {
            return arrayOfNulls(size)
        }
    }
}
