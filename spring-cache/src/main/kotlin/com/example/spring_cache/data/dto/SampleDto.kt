package com.example.spring_cache.data.dto

import com.hazelcast.core.Hazelcast
import com.hazelcast.nio.ObjectDataInput
import com.hazelcast.nio.ObjectDataOutput
import com.hazelcast.nio.serialization.StreamSerializer
import java.io.Serializable
import java.util.UUID

data class SampleDto(val id: Int, val name: String, val description: String)

class SampleSerializer : StreamSerializer<SampleDto> {

    override fun getTypeId(): Int = 1

    override fun write(out: ObjectDataOutput, obj: SampleDto) {
        println("serialize sample: $obj")
        println("instances: ${Hazelcast.getAllHazelcastInstances()}")
        out.writeInt(obj.id)
        out.writeString(obj.name)
        out.writeString(obj.description)
    }

    override fun read(`in`: ObjectDataInput): SampleDto {
        println("deserialize sample: $`in`")
        val id = `in`.readInt()
        val name = `in`.readString()!!
        val description = `in`.readString()!!
        return SampleDto(id, name, description)
    }

    override fun destroy() {}
}