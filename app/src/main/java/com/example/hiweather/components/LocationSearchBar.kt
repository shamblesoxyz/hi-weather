package com.example.jetweatherapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetweatherapp.data.DataOrException
import com.example.jetweatherapp.model.LocationDataItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LocationSearchBar(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {},
    onClear: () -> Unit = {},
    searchResult: DataOrException<ArrayList<LocationDataItem>, Boolean, Exception> = DataOrException(),
    onLocationClick: (Int) -> Unit = {}
) {
    SearchBar(
        modifier = if (active) {
            modifier
                .fillMaxWidth()
        } else {
            modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)
        },
        shadowElevation = 0.dp,
        shape = RoundedCornerShape(15.dp),
        placeholder = {
            Text(text = "Enter Search Location")
        },
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = modifier.clickable { onClear() },
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear search icon"
                )
            }
        }
    ) {
        if (searchResult.loading == true) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator()
            }
        } else {
            if (searchResult.exception != null) {
                Snackbar {
                    Column {
                        Text(text = searchResult.exception!!.message.toString())
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!searchResult.data.isNullOrEmpty()) {
                        LazyColumn(
                            modifier = modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 5.dp),
                            verticalArrangement = Arrangement.Top
                        ) {
                            itemsIndexed(searchResult.data!!) { index, item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .clickable {
                                            onLocationClick(index)
                                        },
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${item.name}, ${item.state} ${item.country}",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "Location Not Found!",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }
    }
}