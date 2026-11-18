package hu.bme.ait.httpdemo.ui.screen.newsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.revenuecat.placeholder.PlaceholderDefaults
import com.revenuecat.placeholder.placeholder

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Text("News screen")

        Button(onClick = {
            //
        }) {
            Text(text = "Refresh")
        }

        ResultScreen()
    }
}

@Composable
fun ResultScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(5) {
            NewsCard()
        }
    }
}



@Composable
fun NewsCard (

) {
    val uriHandler = LocalUriHandler.current

    val annotatedString = buildAnnotatedString {
        // We use withLink to handle the click and styling automatically
        withLink(
            LinkAnnotation.Url(
                url = "https://cs.ait-budapest.com/",
                styles = TextLinkStyles(
                    style = SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                ),
                linkInteractionListener = {
                    val url = (it as LinkAnnotation.Url).url
                    if (url.isNotEmpty()) {
                        uriHandler.openUri(url)
                    }
                }
            )
        ) {
            append("Link")
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Author",
                fontWeight = FontWeight.Bold
            )
            Text(
                text =  "Publish date",
                fontSize = 12.sp
            )
            Text(
                text = "Title"
            )
            //if (ARTICLE URL IS NOT NULL) {
                AsyncImage(
                    model = "https://cs.ait-budapest.com/sites/ait/files/styles/testimonials_front/public/default_images/ait-news-default.jpg",
                    modifier = Modifier
                        .size(200.dp, 100.dp),
                    contentDescription = "selected image"
                )
            //}
            Text(
                text = annotatedString
            )
        }
    }
}