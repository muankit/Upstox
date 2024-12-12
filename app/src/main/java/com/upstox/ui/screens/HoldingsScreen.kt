package com.upstox.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upstox.R
import com.upstox.data.models.UserHolding
import com.upstox.utils.currencyFormat
import com.upstox.utils.format

@Composable
fun HoldingsScreen(uiState: HoldingsUiState) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        if (uiState.isLoading) {
            Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
        } else if (uiState.hasFailure) {
            Text(
                text = "Something went wrong on our end",
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column {
                Toolbar()

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 150.dp)
                ) {
                    items(uiState.holdingsData) { item ->
                        Column {
                            HoldingsItem(
                                item = item
                            )
                            if (item != uiState.holdingsData.last()) {
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .animateContentSize(
                        animationSpec = tween(durationMillis = 400)
                    )
            ) {
                var isCollapsed by remember { mutableStateOf(true) }
                if (isCollapsed) {
                    Row(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { isCollapsed = false },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.profit_loss),
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${uiState.totalPNL.currencyFormat()}(${uiState.percentagePNL.format()}%)",
                            color = if (uiState.totalPNL >= 0.0) Color.Green else Color.Red
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { isCollapsed = true },
                    ) {
                        RowItem(
                            title = stringResource(R.string.current_value),
                            value = uiState.currentValue,
                        )

                        Spacer(Modifier.height(12.dp))

                        RowItem(
                            title = stringResource(R.string.total_investment),
                            value = uiState.totalInvestment
                        )

                        Spacer(Modifier.height(12.dp))

                        RowItem(
                            title = stringResource(R.string.today_s_profit_and_loss),
                            value = uiState.todaysPNL,
                            isColored = true
                        )

                        Spacer(Modifier.height(8.dp))

                        HorizontalDivider()

                        Spacer(Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(R.string.profit_loss),
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "${uiState.totalPNL.currencyFormat()}(${uiState.percentagePNL.format()}%)",
                                color = if (uiState.totalPNL >= 0.0) Color.Green else Color.Red
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun Toolbar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(color = Color.Blue)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_profile_avatar),
            tint = Color.White,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(text = "Portfolio", color = Color.White)

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_import_export),
            tint = Color.White,
            contentDescription = ""
        )

        Spacer(modifier = Modifier.width(8.dp))
        VerticalDivider(modifier = Modifier.fillMaxHeight())
        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(R.drawable.ic_search),
            tint = Color.White,
            contentDescription = ""
        )
    }
}

@Composable
private fun RowItem(title: String, value: Double, isColored: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value.currencyFormat(),
            color = if (isColored) {
                if (value >= 0.0) Color.Green else Color.Red
            } else Color.Unspecified
        )
    }
}

@Composable
private fun HoldingsItem(item: UserHolding) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = item.symbol ?: "", fontWeight = FontWeight.W600, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))

            val ltpText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.Gray
                    )
                ) {
                    append("LTP: ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.Black
                    )
                ) {
                    append(item.ltp.currencyFormat())
                }
            }
            Text(text = ltpText)
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            val netQtyTxt = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.Gray
                    )
                ) {
                    append("NET QTY: ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.Black
                    )
                ) {
                    append(item.quantity.toString())
                }
            }
            Text(text = netQtyTxt)
            Spacer(modifier = Modifier.weight(1f))
            val pnlTxt = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.Gray
                    )
                ) {
                    append("P&L: ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = if (item.totalPNL >= 0) Color.Green else Color.Red
                    )
                ) {
                    append(item.totalPNL.currencyFormat())
                }
            }
            Text(text = pnlTxt)
        }
    }
}

@Composable
@Preview
private fun ScreenPreview() {
    HoldingsScreen(uiState = HoldingsUiState())
}