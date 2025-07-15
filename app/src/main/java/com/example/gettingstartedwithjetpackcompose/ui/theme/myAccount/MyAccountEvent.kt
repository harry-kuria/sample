package com.example.gettingstartedwithjetpackcompose.ui.theme.myAccount

sealed class MyAccountEvent {
    object SettingsClicked      : MyAccountEvent()
    object NotificationsClicked : MyAccountEvent()
    object FAQClicked           : MyAccountEvent()
    object AboutClicked         : MyAccountEvent()
    object LogoutClicked        : MyAccountEvent()
    object BackClicked          : MyAccountEvent()
}