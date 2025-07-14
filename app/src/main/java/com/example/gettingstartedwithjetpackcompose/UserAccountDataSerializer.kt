package com.example.gettingstartedwithjetpackcompose

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import com.google.protobuf.InvalidProtocolBufferException

object UserAccountDataSerializer : Serializer<UserAccountData> {
    override val defaultValue: UserAccountData = UserAccountData.getDefaultInstance()
    //used when there is no data saved yet in the proto file
    override suspend fun readFrom(input : InputStream) : UserAccountData {
        return try {
            UserAccountData.parseFrom(input)
        } catch (exception : InvalidProtocolBufferException) {
            throw CorruptionException("Error reading UserAccountData proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserAccountData, output: OutputStream) {
        t.writeTo(output)
    }

}