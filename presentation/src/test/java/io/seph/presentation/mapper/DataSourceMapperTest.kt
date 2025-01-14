package io.seph.presentation.mapper

import io.seph.domain.model.DataSourceModel
import io.seph.presentation.models.DataSource
import org.junit.Assert.assertEquals
import org.junit.Before
import kotlin.test.Test

class DataSourceMapperTest {

    //class to test
    private lateinit var dataSourceMapper: DataSourceMapper

    @Before
    fun setUp() {
        dataSourceMapper = DataSourceMapper()
    }

    @Test
    fun `toUiModel should map DataSourceModel LOCAL to DataSource LOCAL`() {
        val expectedDataSource = DataSource.LOCAL
        val actualDataSource = dataSourceMapper.toUiModel(DataSourceModel.LOCAL)
        assertEquals(expectedDataSource, actualDataSource)
    }

    @Test
    fun `toUiModel should map DataSourceModel REMOTE to DataSource REMOTE`() {
        val expectedDataSource = DataSource.REMOTE
        val actualDataSource = dataSourceMapper.toUiModel(DataSourceModel.REMOTE)
        assertEquals(expectedDataSource, actualDataSource)
    }

    @Test
    fun `toUiModel should map DataSourceModel UNKNOWN to DataSource UNKNOWN`() {
        val expectedDataSource = DataSource.UNKNOWN
        val actualDataSource = dataSourceMapper.toUiModel(DataSourceModel.UNKNOWN)
        assertEquals(expectedDataSource, actualDataSource)
    }
}