package io.seph.presentation.mapper

import io.seph.domain.model.BrandModel
import io.seph.presentation.models.Brand
import org.junit.Assert.assertEquals
import org.junit.Before
import kotlin.test.Test

private const val BRAND_ID = "1"
private const val BRAND_NAME = "Brand Name"
val brandModel = BrandModel(
    id = BRAND_ID,
    name = BRAND_NAME
)

class BrandMapperTest {

    //class to test
    private lateinit var brandMapper: BrandMapper

    @Before
    fun setUp() {
        brandMapper = BrandMapper()
    }

    @Test
    fun `toUiModel should map BrandModel to Brand`() {
        val expectedBrand = Brand(
            id = BRAND_ID,
            name = BRAND_NAME
        )
        val actualBrand = brandMapper.toUiModel(brandModel)
        assertEquals(expectedBrand, actualBrand)
    }

}