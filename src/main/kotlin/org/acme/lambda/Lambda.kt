package org.acme.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.jboss.logging.Logger

class Lambda(private val logger: Logger, private val processor: Processor) : RequestHandler<Map<String, String>, String> {
    override fun handleRequest(input: Map<String, String>, context: Context): String {
        logger.info("handleRequest, requestId: ${context.awsRequestId}")
        processor.processRequest().await().indefinitely()
        return "OK"
    }
}
