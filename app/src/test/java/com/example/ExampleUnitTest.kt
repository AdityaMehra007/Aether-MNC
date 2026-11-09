package com.example

import com.example.data.EnterpriseRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testDoubleEntryAccountingInvariant() {
    val repo = EnterpriseRepository()
    val summary = repo.getLedgerSummary()
    assertTrue("Double-entry debits must equal credits", summary.isBalanced)
    assertEquals(summary.totalDebits, summary.totalCredits, 0.001)
  }

  @Test
  fun testCryptographicSha256Chaining() {
    val repo = EnterpriseRepository()
    val hash1 = repo.calculateSha256("GENESIS_TEST")
    val hash2 = repo.calculateSha256("BLOCK_2_$hash1")
    assertNotNull(hash1)
    assertNotNull(hash2)
    assertNotEquals(hash1, hash2)
    assertEquals(64, hash1.length)
    assertEquals(64, hash2.length)
  }

  @Test
  fun testParallelConcurrencyBenchmark() = runBlocking {
    val repo = EnterpriseRepository()
    val result = repo.runConcurrencyStressBenchmark(totalOps = 100)
    assertEquals(100, result.operationsCount)
    assertEquals(0, result.raceConditionsDetected)
    assertTrue(result.ledgerInvariantVerified)
    assertTrue(result.opsPerSecond > 0)
    assertTrue(result.totalDurationMs >= 0)
  }

  @Test
  fun testAgentDispatchAndSlashCommands() = runBlocking {
    val repo = EnterpriseRepository()
    val initialMsgCount = repo.slackMessages.value.size
    repo.sendUserMessage("#sre-war-room", "/incident-heal Kubernetes memory leak")
    val updatedMessages = repo.slackMessages.value
    assertTrue("Should have added user and agent messages", updatedMessages.size >= initialMsgCount + 2)
  }
}
