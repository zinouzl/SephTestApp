package io.seph.presentation.mapper

import io.seph.domain.model.DomainModel
import io.seph.presentation.models.UiModel

internal interface UiMapper<M : DomainModel, U : UiModel> {
    fun toUiModel(model: M): U
}