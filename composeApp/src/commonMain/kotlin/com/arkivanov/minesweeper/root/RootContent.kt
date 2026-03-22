package com.arkivanov.minesweeper.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.minesweeper.game.GameContent
import com.arkivanov.minesweeper.settings.EditSettingsContent
import minesweeper.composeapp.generated.resources.Res
import minesweeper.composeapp.generated.resources.app_name
import minesweeper.composeapp.generated.resources.settings
import minesweeper.composeapp.generated.resources.settings_24px
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootContent(component: RootComponent) {
    val gameComponent by component.gameComponent.subscribeAsState()
    val editSettingsComponentSlot by component.editSettingsComponent.subscribeAsState()

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(stringResource(Res.string.app_name)) },
                actions = {
                    IconButton(onClick = component::onEditSettingsClicked) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.settings_24px),
                            contentDescription = stringResource(Res.string.settings),
                        )
                    }
                },
            )

            key(gameComponent) {
                GameContent(component = gameComponent, modifier = Modifier.fillMaxSize())
            }
        }

        editSettingsComponentSlot.child?.instance?.also { editSettingsComponent ->
            EditSettingsContent(component = editSettingsComponent)
        }
    }
}
