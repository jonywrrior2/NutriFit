package com.example.nutrifit.pojo


import android.os.Parcel
import android.os.Parcelable

data class Menu(
    val alimentos: String,
    val cantidad: Int,
    val kcal: Double,
    val proteinas: Double,
    val unidad: String,
    val usuario: String,
    val tipo: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alimentos)
        parcel.writeInt(cantidad)
        parcel.writeDouble(kcal)
        parcel.writeDouble(proteinas)
        parcel.writeString(unidad)
        parcel.writeString(usuario)
        parcel.writeString(tipo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Menu> {
        override fun createFromParcel(parcel: Parcel): Menu {
            return Menu(parcel)
        }

        override fun newArray(size: Int): Array<Menu?> {
            return arrayOfNulls(size)
        }
    }
}



