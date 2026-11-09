package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.MncAgent
import com.example.model.ProofOfWorkReceipt
import com.example.ui.theme.*

@Composable
fun NewTransactionDialog(
  accounts: List<String>,
  onDismiss: () -> Unit,
  onConfirm: (desc: String, debit: String, credit: String, amount: Double) -> Unit
) {
  var desc by remember { mutableStateOf("Enterprise AI Platform Extension License") }
  var debitAcc by remember { mutableStateOf(accounts.firstOrNull() ?: "ACC-TREASURY") }
  var creditAcc by remember { mutableStateOf(accounts.getOrNull(2) ?: "ACC-REVENUE") }
  var amountStr by remember { mutableStateOf("75000") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SlateDark900),
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "RECORD DOUBLE-ENTRY TX",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = CyanNeon
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = desc,
          onValueChange = { desc = it },
          label = { Text("Description") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedTextField(
            value = debitAcc,
            onValueChange = { debitAcc = it },
            label = { Text("Debit Account (Dr)") },
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = creditAcc,
            onValueChange = { creditAcc = it },
            label = { Text("Credit Account (Cr)") },
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = amountStr,
          onValueChange = { amountStr = it },
          label = { Text("Amount (USD $)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = {
            val amt = amountStr.toDoubleOrNull() ?: 1000.0
            onConfirm(desc, debitAcc, creditAcc, amt)
          },
          colors = ButtonDefaults.buttonColors(containerColor = CyanNeon),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "POST TO BALANCED LEDGER",
            fontWeight = FontWeight.Black,
            color = SlateDark950
          )
        }
      }
    }
  }
}

@Composable
fun DealCloserDialog(
  onDismiss: () -> Unit,
  onConfirm: (prospect: String, industry: String, acv: Double) -> Unit
) {
  var prospect by remember { mutableStateOf("Goldman Sachs Global Tech") }
  var industry by remember { mutableStateOf("Investment Banking & Wealth") }
  var acvStr by remember { mutableStateOf("450000") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SlateDark900),
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, VioletNeon.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "1-CLICK RFP DEAL CLOSER",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = VioletNeon
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Instantly generates customized RFP architecture, SOC2 compliance pack, and books 25% upfront retainer into Double-Entry Ledger.",
          fontSize = 11.sp,
          color = TextSecondary
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = prospect,
          onValueChange = { prospect = it },
          label = { Text("Prospect MNC Name") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = industry,
          onValueChange = { industry = it },
          label = { Text("Industry Sector") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = acvStr,
          onValueChange = { acvStr = it },
          label = { Text("Annual Contract Value (USD $)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        Button(
          onClick = {
            val acv = acvStr.toDoubleOrNull() ?: 350000.0
            onConfirm(prospect, industry, acv)
          },
          colors = ButtonDefaults.buttonColors(containerColor = VioletNeon),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "CLOSE DEAL & BOOK TO LEDGER",
            fontWeight = FontWeight.Black,
            color = Color.White
          )
        }
      }
    }
  }
}

@Composable
fun AgentDetailDialog(
  agent: MncAgent,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SlateDark900),
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = agent.iconEmoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = agent.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary
              )
              Text(
                text = agent.department,
                fontSize = 11.sp,
                color = CyanNeon
              )
            }
          }
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "MANDATE & CAPABILITIES",
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = TextMuted
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = agent.roleDescription,
          fontSize = 12.sp,
          lineHeight = 17.sp,
          color = TextSecondary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SlateDark950)
            .padding(10.dp)
        ) {
          Column {
            Text(
              text = "SKILL HARNESS: .agent/skills/${agent.skillKey}/SKILL.md",
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace,
              color = CyanNeon
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "AI STACK: ${agent.aiStack}",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = VioletNeon
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "ROI GENERATED: $%,d USD".format(agent.roiSavingsUsd),
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldTech
            )
          }
        }
      }
    }
  }
}

@Composable
fun PoWDetailDialog(
  pow: ProofOfWorkReceipt,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SlateDark900),
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, EmeraldTech.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "PROOF-OF-WORK CERTIFICATE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = EmeraldTech
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = pow.receiptId,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = CyanNeon
        )
        Text(
          text = "Task: ${pow.taskName}",
          fontSize = 12.sp,
          color = TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SlateDark950)
            .padding(10.dp)
        ) {
          Column {
            Text(text = "EXECUTED BY: ${pow.agent}", fontSize = 10.sp, color = TextSecondary)
            Text(text = "LATENCY: ${pow.latencyP95Ms}ms (p95)", fontSize = 10.sp, color = TextSecondary)
            Text(text = "TEST PASS: ${pow.testPassRate}", fontSize = 10.sp, color = EmeraldTech)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "PREV HASH: ${pow.prevHash.take(16)}...",
              fontSize = 9.sp,
              fontFamily = FontFamily.Monospace,
              color = TextMuted
            )
            Text(
              text = "SIG HASH:  ${pow.signatureHash}",
              fontSize = 9.sp,
              fontFamily = FontFamily.Monospace,
              color = VioletNeon
            )
          }
        }
      }
    }
  }
}
