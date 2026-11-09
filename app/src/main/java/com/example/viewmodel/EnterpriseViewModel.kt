package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.EnterpriseRepository
import com.example.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EnterpriseViewModel(
  private val repository: EnterpriseRepository = EnterpriseRepository()
) : ViewModel() {

  val agents = repository.agents
  val aiModels = repository.aiModels
  val slackMessages = repository.slackMessages
  val accounts = repository.accounts
  val journalEntries = repository.journalEntries
  val powReceipts = repository.powReceipts
  val rfpDeals = repository.rfpDeals
  val benchmarkResult = repository.benchmarkResult
  val isBenchmarking = repository.isBenchmarking
  val headHash = repository.headHash

  private val _selectedTab = MutableStateFlow(NavigationTab.WAR_ROOM)
  val selectedTab: StateFlow<NavigationTab> = _selectedTab.asStateFlow()

  private val _selectedChannel = MutableStateFlow("#sre-war-room")
  val selectedChannel: StateFlow<String> = _selectedChannel.asStateFlow()

  private val _chatInput = MutableStateFlow("")
  val chatInput: StateFlow<String> = _chatInput.asStateFlow()

  private val _selectedAgent = MutableStateFlow<MncAgent?>(null)
  val selectedAgent: StateFlow<MncAgent?> = _selectedAgent.asStateFlow()

  private val _selectedPoW = MutableStateFlow<ProofOfWorkReceipt?>(null)
  val selectedPoW: StateFlow<ProofOfWorkReceipt?> = _selectedPoW.asStateFlow()

  private val _showNewTxDialog = MutableStateFlow(false)
  val showNewTxDialog: StateFlow<Boolean> = _showNewTxDialog.asStateFlow()

  private val _showNewDealDialog = MutableStateFlow(false)
  val showNewDealDialog: StateFlow<Boolean> = _showNewDealDialog.asStateFlow()

  val channels = listOf(
    "#sre-war-room",
    "#cfo-ledger-erp",
    "#global-sales-deals",
    "#secops-compliance",
    "#agent-swarm-all"
  )

  fun selectTab(tab: NavigationTab) {
    _selectedTab.value = tab
  }

  fun selectChannel(channel: String) {
    _selectedChannel.value = channel
  }

  fun onChatInputChanged(text: String) {
    _chatInput.value = text
  }

  fun sendCurrentMessage() {
    val text = _chatInput.value.trim()
    if (text.isEmpty()) return
    _chatInput.value = ""
    viewModelScope.launch {
      repository.sendUserMessage(_selectedChannel.value, text)
    }
  }

  fun executeQuickCommand(command: String) {
    viewModelScope.launch {
      repository.sendUserMessage(_selectedChannel.value, command)
    }
  }

  fun openAgentDetail(agent: MncAgent) {
    _selectedAgent.value = agent
  }

  fun closeAgentDetail() {
    _selectedAgent.value = null
  }

  fun openPoWDetail(pow: ProofOfWorkReceipt) {
    _selectedPoW.value = pow
  }

  fun closePoWDetail() {
    _selectedPoW.value = null
  }

  fun toggleNewTxDialog(show: Boolean) {
    _showNewTxDialog.value = show
  }

  fun toggleNewDealDialog(show: Boolean) {
    _showNewDealDialog.value = show
  }

  fun recordTransaction(desc: String, debit: String, credit: String, amount: Double) {
    viewModelScope.launch {
      repository.recordManualTransaction(desc, debit, credit, amount)
      _showNewTxDialog.value = false
    }
  }

  fun triggerDealCloser(prospect: String, industry: String, acv: Double) {
    viewModelScope.launch {
      repository.sendUserMessage(
        channel = "#global-sales-deals",
        userText = "/rfp-close $prospect ($industry) $${acv.toInt()}"
      )
      _showNewDealDialog.value = false
    }
  }

  fun runConcurrencyBenchmark() {
    viewModelScope.launch {
      repository.runConcurrencyStressBenchmark(totalOps = 100)
    }
  }

  fun getLedgerSummary(): LedgerSummary = repository.getLedgerSummary()

  fun getAdrs(): List<ArchitectureDecisionRecord> = repository.getArchitectureDecisionRecords()
}
