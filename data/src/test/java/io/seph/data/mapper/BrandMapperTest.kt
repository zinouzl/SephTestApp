package io.seph.data.mapper

import io.seph.data.entities.BrandDto
import io.seph.domain.model.BrandModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

private const val BRAND_ID = "brand_id"
private const val BRAND_NAME = "Nestor Joseph"

class BrandMapperTest {

    // class to test
    private lateinit var brandMapper: BrandMapper

    @Before
    fun setUp() {
        brandMapper = BrandMapper()
    }

    @Test
    fun `toRaw should map BrandModel to BrandDto`() {
        val brandModel = BrandModel(
            id = BRAND_ID,
            name = BRAND_NAME
        )
        val expectedBrandDto = BrandDto(
            id = BRAND_ID,
            name = BRAND_NAME
        )

        val actualBrandDto = brandMapper.toDomainModel(expectedBrandDto)
        assertEquals(brandModel, actualBrandDto)
    }

    @Test
    fun `toDomainModel should map BrandDto to  BrandModel to `() {
        val brandDto = BrandDto(
            id = BRAND_ID,
            name = BRAND_NAME
        )

        val expectedBrandModel = BrandModel(
            id = BRAND_ID,
            name = BRAND_NAME
        )

        val actualBrandModel = brandMapper.toDomainModel(brandDto)
        assertEquals(expectedBrandModel, actualBrandModel)
    }


}