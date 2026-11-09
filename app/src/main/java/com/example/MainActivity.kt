package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.NavigationTab
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SlateDark950
import com.example.viewmodel.EnterpriseViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        EnterpriseAppRoot()
      }
    }
  }
}

@Composable
fun EnterpriseAppRoot(
  viewModel: EnterpriseViewModel = viewModel()
) {
  val currentTab by viewModel.selectedTab.collectAsState()
  val selectedChannel by viewModel.selectedChannel.collectAsState()
  val chatInput by viewModel.chatInput.collectAsState()
  val messages by viewModel.slackMessages.collectAsState()
  val agents by viewModel.agents.collectAsState()
  val aiModels by viewModel.aiModels.collectAsState()
  val accounts by viewModel.accounts.collectAsState()
  val journalEntries by viewModel.journalEntries.collectAsState()
  val powReceipts by viewModel.powReceipts.collectAsState()
  val rfpDeals by viewModel.rfpDeals.collectAsState()
  val benchmarkResult by viewModel.benchmarkResult.collectAsState()
  val isBenchmarking by viewModel.isBenchmarking.collectAsState()
  val headHash by viewModel.headHash.collectAsState()

  val selectedAgent by viewModel.selectedAgent.collectAsState()
  val selectedPoW by viewModel.selectedPoW.collectAsState()
  val showNewTxDialog by viewModel.showNewTxDialog.collectAsState()
  val showNewDealDialog by viewModel.showNewDealDialog.collectAsState()

  val ledgerSummary = viewModel.getLedgerSummary()

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(SlateDark950),
    topBar = {
      TopMncAppBar(
        headHash = headHash,
        isBalanced = ledgerSummary.isBalanced,
        onOpenCtoAudit = { viewModel.selectTab(NavigationTab.CTO_BENCHMARK) }
      )
    },
    bottomBar = {
      MncBottomNavigationBar(
        currentTab = currentTab,
        onTabSelected = { viewModel.selectTab(it) }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentTab) {
        NavigationTab.WAR_ROOM -> {
          SlackWarRoomScreen(
            channels = viewModel.channels,
            selectedChannel = selectedChannel,
            messages = messages,
            chatInput = chatInput,
            onChannelSelected = { viewModel.selectChannel(it) },
            onChatInputChanged = { viewModel.onChatInputChanged(it) },
            onSendMessage = { viewModel.sendCurrentMessage() },
            onExecuteCommand = { viewModel.executeQuickCommand(it) }
          )
        }
        NavigationTab.AGENT_FLEET -> {
          AgentFleetScreen(
            agents = agents,
            onAgentClick = { viewModel.openAgentDetail(it) },
            onDispatchMission = { agent ->
              viewModel.selectTab(NavigationTab.WAR_ROOM)
              val targetChannel = when (agent.id) {
                "AGT-SRE" -> "#sre-war-room"
                "AGT-CFO" -> "#cfo-ledger-erp"
                "AGT-REV" -> "#global-sales-deals"
                "AGT-SEC" -> "#secops-compliance"
                else -> "#agent-swarm-all"
              }
              viewModel.selectChannel(targetChannel)
              viewModel.executeQuickCommand("/swarm-dispatch ${agent.name}: Execute automated enterprise check")
            }
          )
        }
        NavigationTab.AI_ROUTER -> {
          MultiAiRouterScreen(models = aiModels)
        }
        NavigationTab.ERP_LEDGER -> {
          ErpLedgerScreen(
            summary = ledgerSummary,
            journalEntries = journalEntries,
            rfpDeals = rfpDeals,
            onOpenNewTxDialog = { viewModel.toggleNewTxDialog(true) },
            onOpenDealCloserDialog = { viewModel.toggleNewDealDialog(true) }
          )
        }
        NavigationTab.CTO_BENCHMARK -> {
          CtoBenchmarkScreen(
            benchmarkResult = benchmarkResult,
            isBenchmarking = isBenchmarking,
            powReceipts = powReceipts,
            adrs = viewModel.getAdrs(),
            onRunBenchmark = { viewModel.runConcurrencyBenchmark() },
            onPoWClick = { viewModel.openPoWDetail(it) }
          )
        }
      }
    }
  }

  // Dialog Overlays
  if (selectedAgent != null) {
    AgentDetailDialog(
      agent = selectedAgent!!,
      onDismiss = { viewModel.closeAgentDetail() }
    )
  }

  if (selectedPoW != null) {
    PoWDetailDialog(
      pow = selectedPoW!!,
      onDismiss = { viewModel.closePoWDetail() }
    )
  }

  if (showNewTxDialog) {
    NewTransactionDialog(
      accounts = accounts.keys.toList(),
      onDismiss = { viewModel.toggleNewTxDialog(false) },
      onConfirm = { desc, debit, credit, amount ->
        viewModel.recordTransaction(desc, debit, credit, amount)
      }
    )
  }

  if (showNewDealDialog) {
    DealCloserDialog(
      onDismiss = { viewModel.toggleNewDealDialog(false) },
      onConfirm = { prospect, industry, acv ->
        viewModel.triggerDealCloser(prospect, industry, acv)
      }
    )
  }
}
