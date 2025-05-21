package org.acme.lambda

import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger
import org.jboss.logging.MDC

@ApplicationScoped
class Processor(private val bucketService: BucketService, private val logger: Logger) {

    fun processRequest(): Uni<Unit> {
        val requestId = MDC.get("AWSRequestId").toString()
        logger.info("processRequest, requestId: $requestId")

        return bucketService.listBuckets()
            .onItem().transformToUniAndMerge(bucketService::getBucketAcl)
            .onFailure().invoke(logger::error)
            .collect().last()
            .replaceWithUnit()
    }
}
