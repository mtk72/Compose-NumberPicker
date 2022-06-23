package com.chargemap.compose.numberpicker

import androidx.compose.foundation.layout.Row
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kotlin.math.abs

sealed interface Times {
    val hours: Int
    val minutes: Int
    val seconds: Int
}

data class FullTimes(
    override val hours: Int,
    override val minutes: Int,
    override val seconds: Int,
) : Times

data class AMPMTimes(
    override val hours: Int,
    override val minutes: Int,
    override val seconds: Int,
    val dayTime: DayTime
) : Times {
    enum class DayTime {
        AM,
        PM;
    }
}

@Composable
fun TimeNumberPicker(
    modifier: Modifier = Modifier,
    label: (Int) -> String = {
        it.toString()
    },
    value: Times,
    leadingZero: Boolean = true,
    hoursRange: Iterable<Int> = when (value) {
        is FullTimes -> (0..23)
        is AMPMTimes -> (1..12)
    },
    minutesRange: Iterable<Int> = (0..59),
    secondsRange: Iterable<Int> = (0..59),
    hoursDivider: (@Composable () -> Unit)? = null,
    minutesDivider: (@Composable () -> Unit)? = null,
    secondsDivider: (@Composable () -> Unit)? = null,
    onValueChange: (Times) -> Unit,
    dividersColor: Color = MaterialTheme.colors.primary,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    when (value) {
        is FullTimes ->
            TimeHoursNumberPicker(
                modifier = modifier,
                value = value,
                leadingZero = leadingZero,
                hoursRange = hoursRange,
                minutesRange = minutesRange,
                secondsRange = secondsRange,
                hoursDivider = hoursDivider,
                minutesDivider = minutesDivider,
                secondsDivider = secondsDivider,
                onValueChange = onValueChange,
                dividersColor = dividersColor,
                textStyle = textStyle,
            )
        is AMPMTimes ->
            AMPMTimeNumberPicker(
                modifier = modifier,
                value = value,
                leadingZero = leadingZero,
                hoursRange = hoursRange,
                minutesRange = minutesRange,
                secondsRange = secondsRange,
                hoursDivider = hoursDivider,
                minutesDivider = minutesDivider,
                secondsDivider = secondsDivider,
                onValueChange = onValueChange,
                dividersColor = dividersColor,
                textStyle = textStyle,
            )
    }
}

@Composable
fun TimeHoursNumberPicker(
    modifier: Modifier = Modifier,
    value: FullTimes,
    leadingZero: Boolean = true,
    hoursRange: Iterable<Int>,
    minutesRange: Iterable<Int> = (0..59),
    secondsRange: Iterable<Int> = (0..59),
    hoursDivider: (@Composable () -> Unit)? = null,
    minutesDivider: (@Composable () -> Unit)? = null,
    secondsDivider: (@Composable () -> Unit)? = null,
    onValueChange: (Times) -> Unit,
    dividersColor: Color = MaterialTheme.colors.primary,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NumberPicker(
            modifier = Modifier.weight(1f),
            label = {
                "${if (leadingZero && abs(it) < 10) "0" else ""}$it"
            },
            value = value.hours,
            onValueChange = {
                onValueChange(value.copy(hours = it))
            },
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = hoursRange
        )

        hoursDivider?.invoke()

        NumberPicker(
            modifier = Modifier.weight(1f),
            label = {
                "${if (leadingZero && abs(it) < 10) "0" else ""}$it"
            },
            value = value.minutes,
            onValueChange = {
                onValueChange(value.copy(minutes = it))
            },
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = minutesRange
        )

        minutesDivider?.invoke()

        NumberPicker(
            modifier = Modifier.weight(1f),
            label = {
                "${if (leadingZero && abs(it) < 10) "0" else ""}$it"
            },
            value = value.seconds,
            onValueChange = {
                onValueChange(value.copy(seconds = it))
            },
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = secondsRange
        )

        secondsDivider?.invoke()
    }
}

@Composable
fun AMPMTimeNumberPicker(
    modifier: Modifier = Modifier,
    value: AMPMTimes,
    leadingZero: Boolean = true,
    hoursRange: Iterable<Int>,
    minutesRange: Iterable<Int> = (0..59),
    secondsRange: Iterable<Int> = (0..59),
    hoursDivider: (@Composable () -> Unit)? = null,
    minutesDivider: (@Composable () -> Unit)? = null,
    secondsDivider: (@Composable () -> Unit)? = null,
    onValueChange: (Times) -> Unit,
    dividersColor: Color = MaterialTheme.colors.primary,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NumberPicker(
            modifier = Modifier.weight(1f),
            value = value.hours,
            label = {
                "${if (leadingZero && abs(it) < 10) "0" else ""}$it"
            },
            onValueChange = {
                onValueChange(value.copy(hours = it))
            },
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = hoursRange
        )

        hoursDivider?.invoke()

        NumberPicker(
            modifier = Modifier.weight(1f),
            label = {
                "${if (leadingZero && abs(it) < 10) "0" else ""}$it"
            },
            value = value.minutes,
            onValueChange = {
                onValueChange(value.copy(minutes = it))
            },
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = minutesRange
        )

        minutesDivider?.invoke()

        NumberPicker(
            modifier = Modifier.weight(1f),
            label = {
                "${if (leadingZero && abs(it) < 10) "0" else ""}$it"
            },
            value = value.seconds,
            onValueChange = {
                onValueChange(value.copy(seconds = it))
            },
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = secondsRange
        )

        secondsDivider?.invoke()

        NumberPicker(
            value = when (value.dayTime) {
                AMPMTimes.DayTime.AM -> 0
                else -> 1
            },
            label = {
                when (it) {
                    0 -> "AM"
                    else -> "PM"
                }
            },
            onValueChange = {
                onValueChange(
                    value.copy(
                        dayTime = when (it) {
                            0 -> AMPMTimes.DayTime.AM
                            else -> AMPMTimes.DayTime.PM
                        }
                    )
                )
            },
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = (0..1)
        )
    }
}