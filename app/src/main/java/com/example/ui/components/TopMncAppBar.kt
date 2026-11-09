package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Security
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
import com.example.model.NavigationTab
import com.example.ui.theme.*

@Composable
fun TopMncAppBar(
  headHash: String,
  isBalanced: Boolean,
  onOpenCtoAudit: () -> Unit
) {
  Surface(
    color = SlateDark900,
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, SlateBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Brand Title
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(CyanGlow)
              .border(1.dp, CyanNeon, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Bolt,
              contentDescription = "Aether Logo",
              tint = CyanNeon,
              modifier = Modifier.size(22.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = "AETHER MNC",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 1.5.sp,
                color = TextPrimary
              )
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(EmeraldTech)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "8/8 AGENTS SWARMING • ACTIVE",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = EmeraldTech
                )
              )
            }
          }
        }

        // Live CTO Audit Button
        FilledTonalButton(
          onClick = onOpenCtoAudit,
          colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = VioletGlow,
            contentColor = VioletNeon
          ),
          shape = RoundedCornerShape(20.dp),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          modifier = Modifier.border(1.dp, VioletNeon.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
        ) {
          Icon(
            imageVector = Icons.Default.Bolt,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "CTO AUDIT",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // System Telemetry Ticker
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(6.dp))
          .background(SlateDark950)
          .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "LEDGER: ",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = TextMuted
          )
          Text(
            text = if (isBalanced) "100% BALANCED" else "UNBALANCED",
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            color = if (isBalanced) EmeraldTech else RoseTech
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "LATENCY: ",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = TextMuted
          )
          Text(
            text = "28ms (p99)",
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            color = CyanNeon
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "HEAD: ",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = TextMuted
          )
          Text(
            text = headHash.take(8) + "...",
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = VioletNeon
          )
        }
      }
    }
  }
}
