package com.example.healthtipsapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthtipsapp.R
import com.example.healthtipsapp.data.TipRepository
import com.example.healthtipsapp.model.HealthTip
import com.example.healthtipsapp.model.TipCategory

sealed class AppScreen(val route: String, val title: String) {
    data object Home : AppScreen("home", "Home")
    data object Favorites : AppScreen("favorites", "Favorites")
    data object About : AppScreen("about", "About")
}

@Composable
fun HealthTipsApp() {
    val navController = rememberNavController()
    val favorites = remember { mutableSetOf<Int>() }
    var selectedCategory by remember { mutableStateOf<TipCategory?>(null) }
    var selectedTip by remember { mutableStateOf<HealthTip?>(null) }

    val tips = TipRepository.getTips()
    val screenBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = screenBackStackEntry?.destination?.route ?: AppScreen.Home.route

    Scaffold(
        topBar = {
            TopBar(title = when (currentRoute) {
                AppScreen.Home.route -> "Healthy Tips"
                AppScreen.Favorites.route -> "Favorites"
                AppScreen.About.route -> "About"
                else -> "Healthy Tips"
            })
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppScreen.Home.route) {
                HomeScreen(
                    tips = if (selectedCategory == null) tips else tips.filter { it.category == selectedCategory },
                    favoriteIds = favorites,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it },
                    onToggleFavorite = { tipId ->
                        if (favorites.contains(tipId)) favorites.remove(tipId) else favorites.add(tipId)
                    },
                    onTipClick = { selectedTip = it }
                )
            }

            composable(AppScreen.Favorites.route) {
                FavoritesScreen(
                    tips = tips.filter { favorites.contains(it.id) },
                    favoriteIds = favorites,
                    onToggleFavorite = { tipId ->
                        if (favorites.contains(tipId)) favorites.remove(tipId) else favorites.add(tipId)
                    },
                    onTipClick = { selectedTip = it }
                )
            }

            composable(AppScreen.About.route) {
                AboutScreen()
            }
        }

        selectedTip?.let { tip ->
            TipDetailDialog(
                tip = tip,
                isFavorite = favorites.contains(tip.id),
                onDismiss = { selectedTip = null },
                onToggleFavorite = {
                    if (favorites.contains(tip.id)) favorites.remove(tip.id) else favorites.add(tip.id)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@Composable
private fun AppBottomNavigation(navController: NavController) {
    val items = listOf(
        AppScreen.Home,
        AppScreen.Favorites,
        AppScreen.About
    )

    androidx.compose.material3.NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            androidx.compose.material3.NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                icon = {
                    when (screen) {
                        AppScreen.Home -> Icon(Icons.Filled.Star, contentDescription = null)
                        AppScreen.Favorites -> Icon(Icons.Filled.Favorite, contentDescription = null)
                        AppScreen.About -> Icon(Icons.Outlined.Info, contentDescription = null)
                    }
                },
                label = { Text(screen.title) }
            )
        }
    }
}

@Composable
fun HomeScreen(
    tips: List<HealthTip>,
    favoriteIds: Set<Int>,
    selectedCategory: TipCategory?,
    onCategorySelected: (TipCategory?) -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onTipClick: (HealthTip) -> Unit
) {
    val categories = listOf(null) + TipCategory.entries

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.healthy_lifestyle),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.daily_health_tips),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.foundation.lazy.LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                val label = category?.name ?: stringResource(id = R.string.all)
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    label = { Text(label) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (tips.isEmpty()) {
            Text("No tips available in this category.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(tips) { tip ->
                    TipCard(
                        tip = tip,
                        isFavorite = favoriteIds.contains(tip.id),
                        onToggleFavorite = { onToggleFavorite(tip.id) },
                        onClick = { onTipClick(tip) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoritesScreen(
    tips: List<HealthTip>,
    favoriteIds: Set<Int>,
    onToggleFavorite: (Int) -> Unit,
    onTipClick: (HealthTip) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        if (tips.isEmpty()) {
            Text(
                text = "No favorites yet. Save your preferred tips from home.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(tips) { tip ->
                    TipCard(
                        tip = tip,
                        isFavorite = favoriteIds.contains(tip.id),
                        onToggleFavorite = { onToggleFavorite(tip.id) },
                        onClick = { onTipClick(tip) }
                    )
                }
            }
        }
    }
}

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Why this app?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.about_text),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text("• Small habits matter")
                Text("• Better sleep improves recovery")
                Text("• Movement and hydration support energy")
                Text("• Consistency beats perfection")
            }
        }
    }
}

@Composable
private fun TipCard(
    tip: HealthTip,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tip.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.favorite),
                        tint = if (isFavorite) Color(0xFFEF5350) else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tip.category.name,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tip.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${tip.duration} • ${tip.difficulty}")
                Text("Benefit")
            }
        }
    }
}
