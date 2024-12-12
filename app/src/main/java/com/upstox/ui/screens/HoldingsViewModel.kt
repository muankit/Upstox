package com.upstox.ui.screens

import android.app.Application
import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upstox.data.models.UserHolding
import com.upstox.data.repository.HoldingsRepo
import com.upstox.data.util.Status
import com.upstox.data.util.resourceFlow
import com.upstox.utils.NetworkUtils
import com.upstox.utils.collectLatest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val holdingsRepo: HoldingsRepo,
    private val context: Application
) : ViewModel() {

    private val _holdingsUiState = MutableStateFlow(HoldingsUiState())
    val holdingsUiState = _holdingsUiState

    init {
        resourceFlow {
            holdingsRepo.getHoldings(fetchFromRemote = NetworkUtils.isNetworkAvailable(context))
        }.collectLatest(viewModelScope) {
            when (it.status) {
                Status.LOADING -> {
                    _holdingsUiState.update { uiState ->
                        uiState.copy(
                            isLoading = true,
                            hasFailure = false
                        )
                    }
                }

                Status.SUCCESS -> {
                    val holdings = it.data?.toList() ?: listOf()

                    val currentValue = holdings.sumOf {
                        (it.ltp ?: 0.0) * (it.quantity ?: 0)
                    }

                    val totalInvestment = holdings.sumOf {
                        (it.avgPrice ?: 0.0) * (it.quantity ?: 0)
                    }

                    val totalPNL = (currentValue - totalInvestment)

                    val todaysPNL = holdings.sumOf {
                        ((it.ltp ?: 0.0) - (it.close ?: 0.0)) * (it.quantity ?: 0)
                    }

                    val percentagePNL = if (totalInvestment != 0.0) {
                        ((totalPNL / totalInvestment) * 100)
                    } else 0.0

                    _holdingsUiState.update { uiState ->
                        uiState.copy(
                            holdingsData = holdings,
                            currentValue = currentValue,
                            totalInvestment = totalInvestment,
                            totalPNL = totalPNL,
                            todaysPNL = todaysPNL,
                            percentagePNL = percentagePNL,
                            isLoading = false,
                            hasFailure = false
                        )
                    }
                }

                Status.ERROR -> {
                    println(it.message)
                    _holdingsUiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            hasFailure = true
                        )
                    }
                }
            }
        }
    }

}

@Keep
data class HoldingsUiState(
    val holdingsData: List<UserHolding> = listOf(),
    val isLoading: Boolean = false,
    val hasFailure: Boolean = false,
    val currentValue: Double = 0.0,
    val totalInvestment: Double = 0.0,
    val totalPNL: Double = 0.0,
    val todaysPNL: Double = 0.0,
    val percentagePNL: Double = 0.0
)