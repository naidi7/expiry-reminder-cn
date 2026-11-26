package com.lorenzovainigli.foodexpirationdates.feature.foodlist.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lorenzovainigli.foodexpirationdates.R
import com.lorenzovainigli.foodexpirationdates.feature.foodlist.presentation.component.FoodItemsList
import com.lorenzovainigli.foodexpirationdates.feature.foodlist.presentation.component.FoodListEmptyState
import com.lorenzovainigli.foodexpirationdates.feature.foodlist.presentation.model.FoodCardUiModel
import com.lorenzovainigli.foodexpirationdates.feature.foodlist.presentation.preview.getItemsForPreview
import com.lorenzovainigli.foodexpirationdates.ui.theme.FoodExpirationDatesTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun FoodListScreen(
    items: ImmutableList<FoodCardUiModel>,
    showSnackbar: MutableState<Boolean>? ,
    isSearchActive: Boolean,
    onSearchBarClose: () -> Unit,
    onClickDelete: (Int) -> Unit,
    onClickEdit: (Int) -> Unit,
    onFloatingActionButtonClick: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("") }
    val filteredItems = remember(items, selectedCategory) {
        items
            .filter { selectedCategory.isEmpty() || it.category == selectedCategory }
            .toImmutableList()
    }
    Box(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = filteredItems.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            FoodItemsList(
                items = filteredItems,
                showSnackbar = showSnackbar,
                isSearchActive = isSearchActive,
                onSearchBarClose = onSearchBarClose,
                onClickDelete = onClickDelete,
                onClickEdit = onClickEdit,
                selectedCategory = selectedCategory,
                onSelectCategory = { selectedCategory = it }
            )
        }
        AnimatedVisibility(
            visible = filteredItems.isEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            FoodListEmptyState()
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            onClick = onFloatingActionButtonClick,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(id = R.string.insert)
            )
        }
    }
}

@Preview
@Composable
fun FoodListScreenPreview() {
    FoodExpirationDatesTheme {
        Surface {
            FoodListScreen(
                items = getItemsForPreview(LocalContext.current),
                isSearchActive = true,
                onSearchBarClose = {},
                onClickDelete = {},
                onClickEdit = {},
                onFloatingActionButtonClick = {},
                showSnackbar = null
            )
        }
    }
}