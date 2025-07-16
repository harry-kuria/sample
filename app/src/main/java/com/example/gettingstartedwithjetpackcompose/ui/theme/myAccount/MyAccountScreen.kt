package com.example.gettingstartedwithjetpackcompose.ui.theme.myAccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gettingstartedwithjetpackcompose.ui.theme.Roboto

sealed class IconType {
    data class Vector(val imageVector: ImageVector) : IconType()
    data class PainterRes(val painter: Painter) : IconType()
}

@Composable
fun ProfileMenuButtons(icon: IconType,
                       title: String,
                       onClick: () -> Unit,
                       iconTint: Color = Color.Black,
                       titleColor: Color = Color.Black) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(vertical = 20.dp),
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
        Text(text = title, style = MaterialTheme.typography.bodyMedium, fontFamily = Roboto,
            color = titleColor
        )
    }

}


@Composable
fun MyAccountScreen(
    username: String,
    email: String,
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "My Account",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                //textAlign = TextAlign.Center,
                fontFamily = Roboto
            )

            Spacer(modifier = Modifier.size(48.dp)) // Fills space to match back button width
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {


            // User information
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.user_icon),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = username,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))


            // Profile button list
            val menuItems = listOf(
                Triple("Edit Profile", IconType.Vector(Icons.Filled.Edit), {}),
                Triple("Settings", IconType.Vector(Icons.Filled.Settings), {}),
                Triple("Notifications", IconType.Vector(Icons.Filled.Notifications), {}),
                Triple("About", IconType.Vector(Icons.Filled.Info), {}),
                //Triple("FAQ", IconType.PainterRes(painterResource(R.drawable.ic_faq)), {})
            )

            menuItems.forEachIndexed { index, item ->
                ProfileMenuButtons(
                    icon = item.second,
                    title = item.first,
                    onClick = item.third
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            //Logout button only
            ProfileMenuButtons(
                icon = IconType.PainterRes(painterResource(R.drawable.ic_logout)),
                title = "Logout",
                titleColor = Color(0xFFE53935),
                iconTint = Color(0xFFE53935),
                onClick = onLogoutClick
            )

        }
    }
}

