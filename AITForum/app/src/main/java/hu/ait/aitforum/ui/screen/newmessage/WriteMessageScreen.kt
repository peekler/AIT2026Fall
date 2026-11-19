package hu.ait.aitforum.ui.screen.newmessage

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.content.FileProvider.getUriForFile
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WriteMessageScreen(
    viewModel: WriteMessageScreenViewModel = viewModel()
) {
    var postTitle by rememberSaveable { mutableStateOf("") }
    var postBody by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )



    Column(modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
        Text("Write Post Message",
            fontSize = 28.sp,
            fontFamily = FontFamily.Monospace,
            fontStyle = FontStyle.Italic)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {Text("Enter title here")},
            value = postTitle,
            onValueChange = {
                postTitle = it
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {Text("Enter post body here")},
            value = postBody,
            onValueChange = {
                postBody = it
            }
        )

        // - camera and permission
        if (cameraPermissionState.status.isGranted) {
            Button(onClick = {
                val uri = ComposeFileProvider.getImageUri(context)
                imageUri = uri
                cameraLauncher.launch(uri) // opens the built in camera
            }) {
                Text(text = "Take photo")
            }
        } else {
            Column() {
                val permissionText = if (cameraPermissionState.status.shouldShowRationale) {
                    "Please reconsider giving the camera persmission it is needed if you want to take photo for the message"
                } else {
                    "Give permission for using photos with items"
                }
                Text(text = permissionText)
                Button(onClick = {
                    // This opens the permission request dialog
                    cameraPermissionState.launchPermissionRequest()
                }) {
                    Text(text = "Request permission")
                }
            }
        }

        if (hasImage && imageUri != null) {
            AsyncImage( // AsyncImage is from the coilCompose library
                model = imageUri,
                modifier = Modifier.size(200.dp, 200.dp),
                contentDescription = "selected photo"
            )
        }


        Button(
            onClick = {
                if (imageUri == null ) {
                    viewModel.uploadPost(
                        postTitle, postBody, ""
                    )
                } else {
                    viewModel.uploadPostImage(
                        context.contentResolver,
                        imageUri!!,
                        postTitle,
                        postBody
                    )
                }
            }
        ) {
            Text("Save")
        }

        when(viewModel.writePostUiState) {
            is WritePostUiState.Init -> {}
            is WritePostUiState.LoadingPostUpload -> CircularProgressIndicator()
            is WritePostUiState.PostUploadSuccess -> Text("Post uploaded")
            is WritePostUiState.ErrorDuringPostUpload -> {
                val errorText =
                    (viewModel.writePostUiState as
                            WritePostUiState.ErrorDuringPostUpload)
                        .error
                Text(errorText!!)
            }
            is WritePostUiState.LoadingImageUpload -> CircularProgressIndicator()
            is WritePostUiState.ImageUploadSuccess -> Text("Image uploaded")
            is WritePostUiState.ErrorDuringImageUpload -> {
                val errorText =
                    (viewModel.writePostUiState as
                            WritePostUiState.ErrorDuringImageUpload)
                        .error
                Text(errorText!!)
            }
        }

    }
}


class ComposeFileProvider : FileProvider(
    hu.ait.aitforum.R.xml.filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir,
                "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory,
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}

