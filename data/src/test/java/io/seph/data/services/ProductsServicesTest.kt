package io.seph.data.services

import io.seph.data.base.BaseServiceTest
import io.seph.data.entities.BrandDto
import io.seph.data.entities.ImagesUrlsDto
import io.seph.data.entities.ProductDto
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import kotlin.test.assertEquals

class ProductsServicesTest : BaseServiceTest() {

    //class to test
    private val service: ProductsService by lazy {
        retrofit.create(ProductsService::class.java)
    }

    @Test
    fun `should return the correct data`() = runBlocking {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    SUCCESS_PRODUCTS_RESPONSE
                )
        )
        assertEquals(
            listOf(
                ProductDto(
                    productId = 1461267310,
                    productName = "Size Up - Mascara Volume Extra Large Immédiat",
                    description = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
                    productPrice = 140.00F,
                    productImages = ImagesUrlsDto(
                        small = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
                        large = ""
                    ),
                    brand = BrandDto(
                        id = "SEPHO",
                        name = "SEPHORA COLLECTION"
                    ),
                    isProductSet = false,
                    isSpecialBrand = false
                ),
                ProductDto(
                    productId = 1461267311,
                    productName = "Kit bain fleur de coton",
                    description = "Un gel nettoyant mains qui lave vos mains tout en douceur avec sa mousse aussi onctueuse que généreuse.",
                    productPrice = 120.00F,
                    productImages = ImagesUrlsDto(
                        small = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
                        large = ""
                    ),
                    brand = BrandDto(
                        id = "SEPHO",
                        name = "SEPHORA COLLECTION"
                    ),
                    isProductSet = true,
                    isSpecialBrand = false
                )
            ),
            service.getProducts()
        )
    }
}

private val SUCCESS_PRODUCTS_RESPONSE = """
    [
   {
      "product_id":1461267310,
      "product_name":"Size Up - Mascara Volume Extra Large Immédiat",
      "description":"Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
      "price":140.00,
      "images_url":{
         "small":"https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
         "large":""
      },
      "c_brand":{
         "id":"SEPHO",
         "name":"SEPHORA COLLECTION"
      },
      "is_productSet":false,
      "is_special_brand": false
   },
   {
      "product_id":1461267311,
      "product_name":"Kit bain fleur de coton",
      "description":"Un gel nettoyant mains qui lave vos mains tout en douceur avec sa mousse aussi onctueuse que généreuse.",
      "price":120.00,
      "images_url":{
         "small":"https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
         "large":""
      },
      "c_brand":{
         "id":"SEPHO",
         "name":"SEPHORA COLLECTION"
      },
      "is_productSet":true,
      "is_special_brand": false
   }
   ]
""".trimIndent()