package e2e
import io.quarkus.amazon.lambda.runtime.AmazonLambdaApi
import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@QuarkusTest
class LambdaE2E {

    @Test
    fun `should pass`() {
        Given {
            body("test" to "test")
        } When {
            post(AmazonLambdaApi.API_BASE_PATH_TEST)
        } Then {
            statusCode(200)
        } Extract {
            assert(body().asString() == "\"OK\"")
        }
    }

}
