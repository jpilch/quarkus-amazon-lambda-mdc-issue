package org.acme.lambda

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger
import org.jboss.logging.MDC
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.Bucket
import software.amazon.awssdk.services.s3.model.GetBucketAclResponse

@ApplicationScoped
class BucketService(private val s3Client: S3AsyncClient, private val logger: Logger) {

    fun listBuckets(): Multi<Bucket> {
        val requestId = MDC.get("AWSRequestId").toString()
        logger.info("listBuckets, requestId: $requestId")
        return Uni.createFrom().completionStage { s3Client.listBuckets() }
            .onItem().transformToMulti { Multi.createFrom().iterable(it.buckets()) }
    }

    fun getBucketAcl(bucket: Bucket): Uni<GetBucketAclResponse> {
        val requestId = MDC.get("AWSRequestId").toString()
        logger.info("getBucketAcl, requestId: $requestId")
        return Uni.createFrom().completionStage {
            s3Client.getBucketAcl {
                it.bucket(bucket.name())
            }
        }
    }
}
