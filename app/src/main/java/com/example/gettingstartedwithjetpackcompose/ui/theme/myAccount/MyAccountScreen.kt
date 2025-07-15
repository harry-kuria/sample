package com.example.gettingstartedwithjetpackcompose.ui.theme.myAccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import com.example.gettingstartedwithjetpackcompose.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

sealed class IconType {
    data class Vector(val imageVector: ImageVector) : IconType()
    data class PainterRes(val painter: Painter) : IconType()
}


@Composable
fun ProfileMenuButtons(icon: IconType, title: String, onClick: () -> Unit,
                       iconTint: Color = Color.Black, textColor: Color = Color.Black) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically){
        when (icon) {
            is IconType.Vector -> Icon(
                imageVector = icon.imageVector,
                contentDescription = "Icon",
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            is IconType.PainterRes -> Icon(
                painter = icon.painter,
                contentDescription = "Icon",
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = title, style = MaterialTheme.typography.headlineSmall,
            color = textColor
        )
    }

}

@Composable
fun MyAccountScreen(username: String, email: String,
                    onSettingsClick: () -> Unit,
                    onNotificationsClick : () -> Unit,
                    onFAQClick : () -> Unit,
                    onAboutClick : () -> Unit,
                    onLogoutClick : () -> Unit,
                    onBackClick : () -> Unit) {
    Column {
        Row(){
            IconButton( onClick = {onBackClick()}) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back button",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(text = "My Account", style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Row(){
            Image(painter = painterResource(R.drawable.user_icon),
                contentDescription = "Profile picture",
                modifier = Modifier.size(100.dp) . clip(shape = CircleShape),
            )
            Spacer(modifier = Modifier.size(20.dp))
            Column {
                Text(text = username, style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(text = email, style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        ProfileMenuButtons(icon = IconType.Vector(Icons.Filled.Settings), title = "Settings", onClick = {/*takeUserToSettingsScreen*/})
        ProfileMenuButtons(icon = IconType.Vector(Icons.Filled.Notifications), title = "Notifications", onClick = {/*takeUserToNotificationsScreen*/})
        ProfileMenuButtons(icon = IconType.Vector(Icons.Filled.Info) , title = "About", onClick = {/*takeUserToAboutScreen*/})
        ProfileMenuButtons(icon = IconType.PainterRes(painterResource(id = R.drawable.ic_faq)), title = "FAQ", onClick = {/*takeUserToFAQScreen*/} )

        Spacer(modifier = Modifier.size(20.dp))

        ProfileMenuButtons(icon = IconType.PainterRes(painterResource(id = R.drawable.ic_logout)), title = "Logout", onClick = {onLogoutClick()})

    }
}