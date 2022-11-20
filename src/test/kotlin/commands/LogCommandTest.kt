package commands

import ch.qos.logback.classic.Level.INFO
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class LogCommandTest {
    private val testAppender = ListAppender<ILoggingEvent>()

    @Mock
    private lateinit var move: Move

    @BeforeEach
    fun init() {
        val logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger
        logger.addAppender(testAppender)
        testAppender.start()
    }

    @AfterEach
    fun free() {
        testAppender.stop()
    }

    @Test
    fun logTest() {
        Log(move, IllegalArgumentException("Some message")).execute()
        val lastLoggedEvent = testAppender.list.lastOrNull()

        assertNotNull(lastLoggedEvent)
        assertEquals(INFO, lastLoggedEvent.level)
        assertTrue(lastLoggedEvent.message.contains("Some message"))
        assertTrue(lastLoggedEvent.message.contains("${Move::class.simpleName}"))
    }
}
