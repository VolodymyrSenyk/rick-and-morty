package arch.log

import arch.log.Kermit.tag
import co.touchlab.kermit.Logger
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter

/**
 * Singleton logger utility wrapping Kermit [Logger] for structured and tagged logging.
 *
 * Provides a centralized entry point for logging with automatic tag
 * assignment based on the caller class or manual tagging via [tag].
 */
object Kermit {

    private var logger: Logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Verbose,
            logWriterList = listOf(platformLogWriter()),
        ),
    )

    /**
     * Initializes the logger with the given configuration.
     * @param config The configuration for the logger.
     */
    fun setUp(config: LoggerConfig) {
        logger = Logger(config = config)
    }

    /**
     * Returns a logger instance with the specified tag.
     * @param tag The tag to use for log messages.
     */
    fun tag(tag: String): Logger = logger.withTag(tag)

    fun v(message: String, throwable: Throwable? = null) =
        withCallerTag().v(throwable) { message }

    fun d(message: String, throwable: Throwable? = null) =
        withCallerTag().d(throwable) { message }

    fun i(message: String, throwable: Throwable? = null) =
        withCallerTag().i(throwable) { message }

    fun w(message: String, throwable: Throwable? = null) =
        withCallerTag().w(throwable) { message }

    fun e(message: String, throwable: Throwable? = null) =
        withCallerTag().e(throwable) { message }

    fun a(message: String, throwable: Throwable? = null) =
        withCallerTag().a(throwable) { message }

    /**
     * Returns a logger instance tagged with the caller's file name.
     */
    private fun withCallerTag(): Logger = logger.withTag(callerTag())

    /**
     * Extracts the file name (without extension) of the caller's stack frame to use as a log tag.
     * @return The caller's file name or `"Unknown"` if it cannot be determined.
     */
    private fun callerTag(): String {
        val stack = Throwable().stackTrace
        val caller = stack.firstOrNull {
            it.fileName != null && !it.className.contains(Kermit::class.simpleName ?: "Kermit")
        }
        return caller?.fileName?.substringBeforeLast(".") ?: "Unknown"
    }
}
