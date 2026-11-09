package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.model.ArchitectureDecisionRecord
import com.example.model.BenchmarkResult
import com.example.model.ProofOfWorkReceipt
import com.example.ui.theme.*

@Composable
fun CtoBenchmarkScreen(
  benchmarkResult: BenchmarkResult?,
  isBenchmarking: Boolean,
  powReceipts: List<ProofOfWorkReceipt>,
  adrs: List<ArchitectureDecisionRecord>,
  onRunBenchmark: () -> Unit,
  onPoWClick: (ProofOfWorkReceipt) -> Unit
) {
  var selectedSubTab by remember { mutableStateOf(0) } // 0: Benchmark, 1: PoW Verifier, 2: ADRs

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SlateDark950)
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    // Sub-Navigation Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(SlateDark900)
        .padding(4.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      listOf("⚡ STRESS BENCHMARK", "📜 PoW VERIFIER", "🏛️ ADR SPECS").forEachIndexed { index, title ->
        val isSelected = selectedSubTab == index
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) VioletNeon else Color.Transparent)
            .clickable { selectedSubTab = index }
            .padding(vertical = 8.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            color = if (isSelected) Color.White else TextMuted
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    when (selectedSubTab) {
      0 -> BenchmarkTab(
        benchmarkResult = benchmarkResult,
        isBenchmarking = isBenchmarking,
        onRunBenchmark = onRunBenchmark
      )
      1 -> PowVerifierTab(
        powReceipts = powReceipts,
        onPoWClick = onPoWClick
      )
      2 -> AdrSpecsTab(adrs = adrs)
    }
  }
}

@Composable
fun BenchmarkTab(
  benchmarkResult: BenchmarkResult?,
  isBenchmarking: Boolean,
  onRunBenchmark: () -> Unit
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    contentPadding = PaddingValues(bottom = 16.dp)
  ) {
    // Benchmark Action Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SlateDark900),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "PARALLEL CONCURRENCY STRESS ENGINE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = CyanNeon,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Staff/Principal Concurrency Test",
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Dispatches 100 parallel operations across 10 concurrent coroutines with double-entry mutation, SHA-256 block hashing, and mutex synchronization.",
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = TextSecondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
          )

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = onRunBenchmark,
            enabled = !isBenchmarking,
            colors = ButtonDefaults.buttonColors(
              containerColor = CyanNeon,
              disabledContainerColor = SlateDark800
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
          ) {
            if (isBenchmarking) {
              CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = CyanNeon,
                strokeWidth = 2.dp
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "EXECUTING 100 OPS IN PARALLEL...",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary
              )
            } else {
              Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = SlateDark950
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "RUN 100-OP STRESS TEST",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = SlateDark950
              )
            }
          }
        }
      }
    }

    // Benchmark Results Display
    if (benchmarkResult != null) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceCard),
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
                text = "BENCHMARK RESULTS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = EmeraldTech
              )
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(EmeraldGlow)
                  .padding(horizontal = 8.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "ZERO RACE CONDITIONS",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = EmeraldTech
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4-Tile Metric Grid
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              MetricTile(
                label = "THROUGHPUT",
                value = "%,.0f ops/s".format(benchmarkResult.opsPerSecond),
                tint = CyanNeon,
                modifier = Modifier.weight(1f)
              )
              MetricTile(
                label = "TOTAL DURATION",
                value = "${benchmarkResult.totalDurationMs} ms",
                tint = TextPrimary,
                modifier = Modifier.weight(1f)
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              MetricTile(
                label = "p50 / p95 / p99",
                value = "${benchmarkResult.p50LatencyMs} / ${benchmarkResult.p95LatencyMs} / ${benchmarkResult.p99LatencyMs} ms",
                tint = VioletNeon,
                modifier = Modifier.weight(1f)
              )
              MetricTile(
                label = "LEDGER INVARIANT",
                value = if (benchmarkResult.ledgerInvariantVerified) "100% BALANCED" else "DRIFT",
                tint = EmeraldTech,
                modifier = Modifier.weight(1f)
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "FINAL HEAD HASH: ${benchmarkResult.finalHeadHash}",
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

@Composable
fun MetricTile(
  label: String,
  value: String,
  tint: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(SlateDark950)
      .padding(10.dp)
  ) {
    Column {
      Text(text = label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextMuted)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Black, color = tint)
    }
  }
}

@Composable
fun PowVerifierTab(
  powReceipts: List<ProofOfWorkReceipt>,
  onPoWClick: (ProofOfWorkReceipt) -> Unit
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(10.dp),
    contentPadding = PaddingValues(bottom = 16.dp)
  ) {
    item {
      Text(
        text = "CRYPTOGRAPHIC PROOF-OF-WORK RECEIPTS",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = TextMuted
      )
    }

    items(powReceipts, key = { it.receiptId }) { pow ->
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
          .clickable { onPoWClick(pow) }
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = pow.receiptId,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = CyanNeon
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(EmeraldGlow)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "✓ VERIFIED",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldTech
              )
            }
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = pow.taskName,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
          )

          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Agent: ${pow.agent} • ${pow.latencyP95Ms}ms",
              fontSize = 10.sp,
              color = TextSecondary
            )
            Text(
              text = "SIG: ${pow.signatureHash.take(8)}",
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

@Composable
fun AdrSpecsTab(adrs: List<ArchitectureDecisionRecord>) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(10.dp),
    contentPadding = PaddingValues(bottom = 16.dp)
  ) {
    items(adrs, key = { it.id }) { adr ->
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = adr.id,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = CyanNeon
            )
            Text(
              text = adr.status,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldTech
            )
          }

          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = adr.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "CONTEXT: " + adr.context,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = TextSecondary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "DECISION: " + adr.decision,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = CyanNeon
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "CONSEQUENCES: " + adr.consequences,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = EmeraldTech
          )
        }
      }
    }
  }
}
