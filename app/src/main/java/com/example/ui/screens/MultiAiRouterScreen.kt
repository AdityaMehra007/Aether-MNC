package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AiModelInfo
import com.example.ui.theme.*

@Composable
fun MultiAiRouterScreen(
  models: List<AiModelInfo>
) {
  val totalCalls = models.sumOf { it.callsHandled }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SlateDark950)
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    // Top Router Status
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
              text = "MULTI-AI STACK ROUTER",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = CyanNeon,
              letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Dynamic Sovereign Gateway",
              fontSize = 15.sp,
              fontWeight = FontWeight.Black,
              color = TextPrimary
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(EmeraldGlow)
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = "ACTIVE ROUTING • 5 STACKS",
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldTech
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "TOTAL DISPATCHED: %,d".format(totalCalls),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondary
          )
          Text(
            text = "FAILOVER: 0ms LATENCY SPIKE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = VioletNeon
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // List of AI Models in Router
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(bottom = 16.dp)
    ) {
      items(models, key = { it.id }) { model ->
        AiModelCard(model = model)
      }

      item {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = SlateDark900),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = "SOVEREIGN ROUTING LOGIC",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = CyanNeon
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "• Banking/PII workloads are automatically routed to on-prem Llama 3.3 70B (0 data egress).\n• Large-context distributed architectures (2M tokens) route to Gemini 2.5 Pro.\n• Code review & deep security audits route to Claude 4 Opus.\n• Real-time enterprise tool calls route over the MCP Bridge.",
              fontSize = 11.sp,
              lineHeight = 16.sp,
              color = TextSecondary
            )
          }
        }
      }
    }
  }
}

@Composable
fun AiModelCard(model: AiModelInfo) {
  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, SlateBorder, RoundedCornerShape(12.dp))
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = model.id,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = TextPrimary
          )
          Text(
            text = model.provider,
            fontSize = 10.sp,
            color = VioletNeon
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(6.dp)
              .clip(CircleShape)
              .background(EmeraldTech)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = model.status,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldTech
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = model.role,
        fontSize = 11.sp,
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Metric Indicators
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(SlateDark950)
          .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(text = "LATENCY", fontSize = 9.sp, color = TextMuted)
          Text(
            text = "${model.latencyMs} ms",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (model.latencyMs < 100) EmeraldTech else CyanNeon
          )
        }

        Column {
          Text(text = "COST/1K", fontSize = 9.sp, color = TextMuted)
          Text(
            text = if (model.costPer1kTokens == 0.0) "FREE (ON-PREM)" else "$${model.costPer1kTokens}",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (model.costPer1kTokens == 0.0) EmeraldTech else AmberTech
          )
        }

        Column {
          Text(text = "CALLS", fontSize = 9.sp, color = TextMuted)
          Text(
            text = "%,d".format(model.callsHandled),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }

        Column {
          Text(text = "CONTEXT", fontSize = 9.sp, color = TextMuted)
          Text(
            text = model.contextWindow,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = VioletNeon
          )
        }
      }
    }
  }
}
