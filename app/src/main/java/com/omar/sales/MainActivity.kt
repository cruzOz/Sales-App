package com.omar.sales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.omar.sales.presentation.AppNavigation
import com.omar.sales.ui.theme.SalesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalesTheme {
                AppNavigation()
            }
        }
    }
}