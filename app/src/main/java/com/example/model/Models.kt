package com.example.model

enum class NavigationTab(val title: String, val badge: String = "") {
  WAR_ROOM("AI Slack", "5"),
  AGENT_FLEET("Agent Fleet", "8"),
  AI_ROUTER("AI Stacks", "5"),
  ERP_LEDGER("ERP Ledger", "$"),
  CTO_BENCHMARK("CTO Audit", "⚡")
}

data class MncAgent(
  val id: String,
  val name: String,
  val department: String,
  val aiStack: String,
  val roleDescription: String,
  val skillKey: String,
  var tasksCompleted: Int,
  var roiSavingsUsd: Long,
  val iconEmoji: String,
  val status: String = "ACTIVE"
)

data class AiModelInfo(
  val id: String,
  val provider: String,
  val role: String,
  val latencyMs: Int,
  val costPer1kTokens: Double,
  val status: String,
  var callsHandled: Int,
  val supportsStreaming: Boolean = true,
  val contextWindow: String = "128k"
)

data class SlackMessage(
  val id: String,
  val channel: String,
  val sender: String,
  val roleTitle: String,
  val stackUsed: String,
  val text: String,
  val actionTag: String? = null,
  val prevHash: String,
  val sha256Hash: String,
  val timestamp: String,
  val isUser: Boolean = false
)

data class LedgerAccount(
  val code: String,
  val name: String,
  val category: String, // Asset, Revenue, Liability
  var debit: Double,
  var credit: Double
) {
  val balance: Double get() = debit - credit
}

data class JournalEntry(
  val entryId: String,
  val description: String,
  val debitAccount: String,
  val creditAccount: String,
  val amountUsd: Double,
  val prevHash: String,
  val currHash: String,
  val timestamp: String
)

data class LedgerSummary(
  val totalDebits: Double,
  val totalCredits: Double,
  val isBalanced: Boolean,
  val accounts: Map<String, LedgerAccount>
)

data class ProofOfWorkReceipt(
  val receiptId: String,
  val agent: String,
  val taskName: String,
  val stackUsed: String,
  val latencyP95Ms: Int,
  val testPassRate: String,
  val prevHash: String,
  val signatureHash: String,
  val timestamp: String,
  val verified: Boolean = true
)

data class BenchmarkResult(
  val runId: String,
  val operationsCount: Int,
  val concurrentThreads: Int,
  val totalDurationMs: Long,
  val opsPerSecond: Double,
  val p50LatencyMs: Long,
  val p95LatencyMs: Long,
  val p99LatencyMs: Long,
  val raceConditionsDetected: Int,
  val ledgerInvariantVerified: Boolean,
  val finalHeadHash: String,
  val timestamp: String
)

data class EnterpriseRfp(
  val id: String,
  val prospectCompany: String,
  val industry: String,
  val annualContractValue: Double,
  val estimatedHoursSavedPerMonth: Int,
  val soc2Compliant: Boolean,
  val status: String, // GENERATED, SIGNED, ACTIVE
  val ledgerEntryId: String? = null
)

data class ArchitectureDecisionRecord(
  val id: String,
  val title: String,
  val status: String,
  val context: String,
  val decision: String,
  val consequences: String
)
