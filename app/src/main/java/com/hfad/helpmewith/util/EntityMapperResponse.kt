package com.hfad.helpmewith.util

interface EntityMapperResponse <Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
}
