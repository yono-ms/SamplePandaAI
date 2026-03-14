package com.example.samplepandaai

import org.junit.Test
import org.slf4j.LoggerFactory

/**
 * JVM上でのユニットテストにおけるSLF4Jの動作確認用テスト。
 * testImplementation(libs.slf4j.simple) により、コンソールにログが出力される。
 */
class LoggingTest {
    private val logger = LoggerFactory.getLogger(LoggingTest::class.java)

    @Test
    fun testLogOutput() {
        logger.info("Info log for testing")
        logger.debug("Debug log for testing")
        logger.error("Error log for testing")

        // このテストが実行された際、Android StudioのRunタブに上記のログが表示されれば成功。
        assert(true)
    }
}
