package com.shagalalab.sozlik.shared.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryType
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun FlagFromTo(type: DictionaryType, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Image(
            painter = painterResource(if (type == DictionaryType.RU_QQ) CommonRes.images.flag_russia else CommonRes.images.flag_karakalpakstan),
            contentDescription = null,
            modifier = Modifier.width(40.dp).aspectRatio(1.6f),
            contentScale = ContentScale.FillBounds
        )
        Icon(Icons.Default.ArrowForward, null, modifier = Modifier.padding(horizontal = 4.dp))
        Image(
            painter = painterResource(if (type == DictionaryType.RU_QQ) CommonRes.images.flag_karakalpakstan else CommonRes.images.flag_uk),
            contentDescription = null,
            modifier = Modifier.width(40.dp).aspectRatio(1.6f),
            contentScale = ContentScale.FillBounds
        )
    }
}
