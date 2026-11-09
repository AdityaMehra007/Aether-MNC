package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
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
import com.example.model.SlackMessage
import com.example.ui.theme.*

@Composable
fun SlackWarRoomScreen(
  channels: List<String>,
  selectedChannel: String,
  messages: List<SlackMessage>,
  chatInput: String,
  onChannelSelected: (String) -> Unit,
  onChatInputChanged: (String) -> Unit,
  onSendMessage: () -> Unit,
  onExecuteCommand: (String) -> Unit
) {
  val filteredMessages = messages.filter {
    if (selectedChannel == "#agent-swarm-all") true else it.channel == selectedChannel
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(SlateDark950)
  ) {
    // 1. Channel Selector Bar
    Surface(
      color = SlateDark900,
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, SlateBorder)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState())
          .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        channels.forEach { ch ->
          val isSelected = ch == selectedChannel
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(16.dp))
              .background(if (isSelected) CyanNeon else SlateDark800)
              .border(
                1.dp,
                if (isSelected) CyanNeon else SlateBorder,
                RoundedCornerShape(16.dp)
              )
              .clickable { onChannelSelected(ch) }
              .padding(horizontal = 12.dp, vertical = 6.dp)
          ) {
            Text(
              text = ch,
              fontSize = 11.sp,
              fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
              color = if (isSelected) SlateDark950 else TextSecondary
            )
          }
        }
      }
    }

    // 2. Slash Command Quick Actions
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 12.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "COMMANDS:",
        fontSize = 9.sp,
        fontWeight = FontWeight.Black,
        color = TextMuted
      )

      listOf(
        "/incident-heal" to RoseTech,
        "/rfp-close" to EmeraldTech,
        "/erp-audit" to AmberTech,
        "/compliance-scan" to VioletNeon,
        "/code-review" to CyanNeon
      ).forEach { (cmd, tint) ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(tint.copy(alpha = 0.15f))
            .border(1.dp, tint.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .clickable { onExecuteCommand(cmd) }
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = cmd,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = tint
          )
        }
      }
    }

    // 3. Messages Stream
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 12.dp),
      reverseLayout = false,
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(vertical = 8.dp)
    ) {
      if (filteredMessages.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(40.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "No messages in $selectedChannel yet.\nType a slash command or query below to command the swarm.",
              color = TextMuted,
              fontSize = 13.sp,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
          }
        }
      } else {
        items(filteredMessages, key = { it.id }) { msg ->
          SlackMessageCard(msg = msg)
        }
      }
    }

    // 4. Chat Input Box
    Surface(
      color = SlateDark900,
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, SlateBorder)
        .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = chatInput,
          onValueChange = onChatInputChanged,
          placeholder = {
            Text(
              text = "Message $selectedChannel or type /incident-heal, /rfp-close...",
              fontSize = 12.sp,
              color = TextMuted
            )
          },
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SlateDark850,
            unfocusedContainerColor = SlateDark850,
            focusedBorderColor = CyanNeon,
            unfocusedBorderColor = SlateBorder,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = CyanNeon
          ),
          shape = RoundedCornerShape(20.dp),
          singleLine = true,
          modifier = Modifier
            .weight(1f)
            .height(50.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
          onClick = onSendMessage,
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(CyanNeon)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send Command",
            tint = SlateDark950,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }
  }
}

@Composable
fun SlackMessageCard(msg: SlackMessage) {
  val isUser = msg.isUser
  val cardBg = if (isUser) SlateDark800 else SurfaceCard
  val borderStroke = if (isUser) VioletNeon.copy(alpha = 0.5f) else SlateBorder

  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = cardBg),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, borderStroke, RoundedCornerShape(12.dp))
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      // Header: Sender + Role + Channel + Time
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(if (isUser) VioletGlow else CyanGlow)
              .border(1.dp, if (isUser) VioletNeon else CyanNeon, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = if (isUser) "👑" else "🤖",
              fontSize = 14.sp
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Column {
            Text(
              text = msg.sender,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = if (isUser) VioletNeon else CyanNeon
            )
            Text(
              text = msg.roleTitle,
              fontSize = 10.sp,
              color = TextMuted
            )
          }
        }

        Text(
          text = msg.timestamp,
          fontSize = 10.sp,
          color = TextMuted
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Message Body
      Text(
        text = msg.text,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Footer: Stack badge + SHA-256 Hash chip
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // AI Stack Pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SlateDark950)
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = "⚡ " + msg.stackUsed,
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium,
            color = CyanNeon
          )
        }

        // Hash Chip
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SlateDark950)
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            tint = EmeraldTech,
            modifier = Modifier.size(10.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "SHA-256: " + msg.sha256Hash.take(8),
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            color = TextSecondary
          )
        }
      }
    }
  }
}
