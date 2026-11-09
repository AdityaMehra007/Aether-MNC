package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavigationTab
import com.example.ui.theme.*

@Composable
fun MncBottomNavigationBar(
  currentTab: NavigationTab,
  onTabSelected: (NavigationTab) -> Unit
) {
  Surface(
    color = SlateDark900,
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, SlateBorder)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = 4.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      NavigationTab.values().forEach { tab ->
        val isSelected = tab == currentTab
        val (icon, label) = when (tab) {
          NavigationTab.WAR_ROOM -> Icons.Default.Forum to "War Room"
          NavigationTab.AGENT_FLEET -> Icons.Default.SmartToy to "Agents"
          NavigationTab.AI_ROUTER -> Icons.Default.Memory to "AI Stacks"
          NavigationTab.ERP_LEDGER -> Icons.Default.AccountBalance to "ERP Ledger"
          NavigationTab.CTO_BENCHMARK -> Icons.Default.Speed to "CTO Audit"
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onTabSelected(tab) }
            .then(
              if (isSelected) {
                Modifier
                  .background(SlateDark800)
                  .border(1.dp, CyanNeon.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
              } else Modifier
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            BadgedBox(
              badge = {
                if (tab.badge.isNotEmpty()) {
                  Badge(
                    containerColor = if (isSelected) CyanNeon else VioletNeon,
                    contentColor = SlateDark950
                  ) {
                    Text(
                      text = tab.badge,
                      fontSize = 8.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }
              }
            ) {
              Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) CyanNeon else TextMuted,
                modifier = Modifier.size(22.dp)
              )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
              text = label,
              fontSize = 10.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              color = if (isSelected) TextPrimary else TextMuted
            )
          }
        }
      }
    }
  }
}
