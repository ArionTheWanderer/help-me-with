package com.hfad.helpmewith.util

interface EntityMapperForm <Entity, DomainModel> {
    fun mapToEntity(domainModel: DomainModel): Entity
}
