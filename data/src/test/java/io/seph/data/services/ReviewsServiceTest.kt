package io.seph.data.services

import io.seph.data.base.BaseServiceTest
import io.seph.data.entities.ProductReviewDto
import io.seph.data.entities.ReviewDto
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import kotlin.test.assertEquals

internal class ReviewsServiceTest : BaseServiceTest() {

    //class to test
    private val service: ReviewsService by lazy {
        retrofit.create(ReviewsService::class.java)
    }

    @Test
    fun `should return the correct data`() = runBlocking {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    SUCCESS_REVIEWS_RESPONSE
                )
        )
        assertEquals(
            listOf(
                ProductReviewDto(
                    productId = 1461267310,
                    reviews = listOf(
                        ReviewDto(
                            reviewerName = "Jade Kent",
                            reviewText = "Ce produit est sublime",
                            reviewRating = 4.4f
                        ),
                        ReviewDto(
                            reviewText = "Prix attractif",
                            reviewRating = 3f
                        )
                    )
                ),
                ProductReviewDto(
                    productId = 1461267311,
                    reviews = listOf(
                        ReviewDto(
                            reviewText = "Ce produit est magnifique",
                            reviewRating = 3.4f,
                            reviewerName = null
                        ),
                        ReviewDto(
                            reviewerName = "Marina Stevens",
                            reviewText = "Pas encore testé",
                            reviewRating = null
                        ),
                        ReviewDto(
                            reviewText = "Ce produit est sublime",
                            reviewRating = null,
                            reviewerName = null
                        ),
                        ReviewDto(
                            reviewText = "Ce produit est attractif",
                            reviewRating = 4.4f,
                            reviewerName = null
                        )
                    )
                ),
                ProductReviewDto(
                    productId = 1461267312,
                    reviews = listOf(
                        ReviewDto(
                            reviewText = "Ce produit est magnifique",
                            reviewRating = 3.4f,
                            reviewerName = null
                        ),
                        ReviewDto(
                            reviewerName = "Melisa Landry",
                            reviewText = "Ce produit n'est pas top",
                            reviewRating = 0f
                        ),
                        ReviewDto(
                            reviewText = "Super !",
                            reviewRating = 4.4f,
                            reviewerName = null
                        ),
                        ReviewDto(

                            reviewText = "Ce pafum est incroyable",
                            reviewRating = 3f,
                            reviewerName = null
                        ),
                        ReviewDto(
                            reviewText = "Unique",
                            reviewRating = 5f,
                            reviewerName = null
                        )

                    )
                )
            ),
            service.getProductsReviews(),
        )

    }
}


private val SUCCESS_REVIEWS_RESPONSE = """
  [
    {
		"product_id": 1461267310,
		"hide": true,
		"reviews": [{
		  "name" : "Jade Kent",
				"text": "Ce produit est sublime",
				"rating": 4.4
			},
			{
				"text": "Prix attractif",
				"rating": 3
			}
		]
	},
	{
		"product_id": 1461267311,
		"reviews": [{
				"text": "Ce produit est magnifique",
				"rating": 3.4
			},
			{
			  		  "name" : "Marina Stevens",
				"text": "Pas encore testé"
			},
			{
				"text": "Ce produit est sublime",
				"rating": null
			},
			{
				"text": "Ce produit est attractif",
				"rating": 4.4
			}
		]
	},
	{
		"product_id": 1461267312,
		"hide": false,
		"reviews": [{
				"text": "Ce produit est magnifique",
				"rating": 3.4
			},
			{
			   "name" : "Melisa Landry",
				"text": "Ce produit n'est pas top",
				"rating": 0
			},
			{
				"text": "Super !",
				"rating": 4.4
			},
			{
				"text": "Ce pafum est incroyable",
				"rating": 3
			},
			{
				"text": "Unique",
				"rating": 5
			}
		]
	}
 ]
""".trimIndent()