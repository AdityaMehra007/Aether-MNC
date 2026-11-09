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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MncAgent
import com.example.ui.theme.*

@Composable
fun AgentFleetScreen(
  agents: List<MncAgent>,
  onAgentClick: (MncAgent) -> Unit,
  onDispatchMission: (MncAgent) -> Unit
) {
  val totalTasks = agents.sumOf { it.tasksCompleted }
  val totalRoi = agents.sumOf { it.roiSavingsUsd }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SlateDark950)
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    // 1. Fleet Top KPI Overview
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SlateDark900),
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, SlateBorder, RoundedCornerShape(16.dp))
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "ENTERPRISE AGENT SWARM",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = CyanNeon,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "8 Autonomous MNC Agents",
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = TextPrimary
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "TASKS DONE",
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = TextMuted
            )
            Text(
              text = "%,d".format(totalTasks),
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "CUMULATIVE ROI",
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = TextMuted
            )
            Text(
              text = "$%,d".format(totalRoi),
              fontSize = 13.sp,
              fontWeight = FontWeight.Black,
              color = EmeraldTech
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // 2. Agents List
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(bottom = 16.dp)
    ) {
      items(agents, key = { it.id }) { agent ->
        AgentCard(
          agent = agent,
          onClick = { onAgentClick(agent) },
          onDispatch = { onDispatchMission(agent) }
        )
      }
    }
  }
}

@Composable
fun AgentCard(
  agent: MncAgent,
  onClick: () -> Unit,
  onDispatch: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, SlateBorder, RoundedCornerShape(14.dp))
      .clickable { onClick() }
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(SlateDark800)
              .border(1.dp, CyanNeon.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
          ) {
            Text(text = agent.iconEmoji, fontSize = 20.sp)
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = agent.name,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = agent.department,
              fontSize = 11.sp,
              color = CyanNeon
            )
          }
        }

        // Status Badge
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(EmeraldGlow)
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Box(
            modifier = Modifier
              .size(6.dp)
              .clip(CircleShape)
              .background(EmeraldTech)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "SWARMING",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldTech
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = agent.roleDescription,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Bottom Row: Stack, ROI and Dispatch Action
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "AI STACK: " + agent.aiStack,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = VioletNeon
          )
          Text(
            text = "ROI SAVED: $%,d • %,d tasks".format(agent.roiSavingsUsd, agent.tasksCompleted),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldTech
          )
        }

        FilledTonalButton(
          onClick = onDispatch,
          colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = CyanNeon,
            contentColor = SlateDark950
          ),
          shape = RoundedCornerShape(12.dp),
          contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "DISPATCH",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
          )
        }
      }
    }
  }
}
