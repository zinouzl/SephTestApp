package io.seph.presentation.mapper

import io.seph.domain.model.DataSourceModel
import io.seph.presentation.models.DataSource

class DataSourceMapper : UiMapper<DataSourceModel, DataSource> {
    override fun toUiModel(model: DataSourceModel): DataSource {
        return when (model) {
            DataSourceModel.LOCAL -> DataSource.LOCAL
            DataSourceModel.REMOTE -> DataSource.REMOTE
            DataSourceModel.UNKNOWN -> DataSource.UNKNOWN
        }
    }
}