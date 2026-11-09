package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun ErpLedgerScreen(
  summary: LedgerSummary,
  journalEntries: List<JournalEntry>,
  rfpDeals: List<EnterpriseRfp>,
  onOpenNewTxDialog: () -> Unit,
  onOpenDealCloserDialog: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SlateDark950)
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    // 1. Double-Entry Balance Invariant Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SlateDark900),
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "ACID DOUBLE-ENTRY ERP LEDGER",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = CyanNeon,
              letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Mathematical Invariant Verification",
              fontSize = 14.sp,
              fontWeight = FontWeight.Black,
              color = TextPrimary
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(if (summary.isBalanced) EmeraldGlow else RoseTech.copy(alpha = 0.2f))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = if (summary.isBalanced) "✓ 100% BALANCED" else "⚠ UNBALANCED",
              fontSize = 10.sp,
              fontWeight = FontWeight.Black,
              color = if (summary.isBalanced) EmeraldTech else RoseTech
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SlateDark950)
            .padding(10.dp),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "TOTAL DEBITS", fontSize = 10.sp, color = TextMuted)
            Text(
              text = "$%,.2f".format(summary.totalDebits),
              fontSize = 14.sp,
              fontWeight = FontWeight.Black,
              color = TextPrimary
            )
          }

          Box(
            modifier = Modifier
              .width(1.dp)
              .height(30.dp)
              .background(SlateBorder)
          )

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "TOTAL CREDITS", fontSize = 10.sp, color = TextMuted)
            Text(
              text = "$%,.2f".format(summary.totalCredits),
              fontSize = 14.sp,
              fontWeight = FontWeight.Black,
              color = TextPrimary
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // 2. Action Buttons (Record Transaction & 1-Click RFP Closer)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Button(
        onClick = onOpenNewTxDialog,
        colors = ButtonDefaults.buttonColors(containerColor = SlateDark800),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .weight(1f)
          .border(1.dp, CyanNeon.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = null,
          tint = CyanNeon,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "+ RECORD TX",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = CyanNeon
        )
      }

      Button(
        onClick = onOpenDealCloserDialog,
        colors = ButtonDefaults.buttonColors(containerColor = VioletNeon),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.weight(1f)
      ) {
        Icon(
          imageVector = Icons.Default.Handshake,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "RFP DEAL CLOSER",
          fontSize = 11.sp,
          fontWeight = FontWeight.Black,
          color = Color.White
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // 3. Accounts & Journal List
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(bottom = 16.dp)
    ) {
      // Accounts Summary Row
      item {
        Text(
          text = "CHART OF ACCOUNTS",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = TextMuted
        )
      }

      summary.accounts.values.chunked(2).forEach { chunk ->
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            chunk.forEach { acc ->
              Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                modifier = Modifier
                  .weight(1f)
                  .border(1.dp, SlateBorder, RoundedCornerShape(10.dp))
              ) {
                Column(modifier = Modifier.padding(10.dp)) {
                  Text(
                    text = acc.code,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = VioletNeon
                  )
                  Text(
                    text = acc.name,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    maxLines = 1
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "$%,.0f".format(acc.balance),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = if (acc.balance >= 0) EmeraldTech else AmberTech
                  )
                }
              }
            }
          }
        }
      }

      // Recent Journal Entries
      item {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "RECENT DOUBLE-ENTRY JOURNAL ENTRIES",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = TextMuted
        )
      }

      items(journalEntries, key = { it.entryId }) { entry ->
        Card(
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceCard),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SlateBorder, RoundedCornerShape(10.dp))
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = entry.entryId,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = CyanNeon
              )
              Text(
                text = "$%,.2f".format(entry.amountUsd),
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = EmeraldTech
              )
            }

            Text(
              text = entry.description,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "Dr: ${entry.debitAccount}  ->  Cr: ${entry.creditAccount}",
                fontSize = 10.sp,
                color = TextSecondary
              )
              Text(
                text = "HASH: ${entry.currHash.take(8)}",
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace,
                color = TextMuted
              )
            }
          }
        }
      }
    }
  }
}
