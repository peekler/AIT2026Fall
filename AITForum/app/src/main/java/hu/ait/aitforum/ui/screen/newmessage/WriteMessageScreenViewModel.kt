package hu.ait.aitforum.ui.screen.newmessage

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.aitforum.data.Post
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.Date
import java.util.UUID

// fz5StHqm7LILipNv

sealed interface WritePostUiState {
    object Init : WritePostUiState
    object LoadingPostUpload : WritePostUiState
    object PostUploadSuccess : WritePostUiState
    data class ErrorDuringPostUpload(val error: String?) : WritePostUiState

    object LoadingImageUpload : WritePostUiState
    data class ErrorDuringImageUpload(val error: String?) : WritePostUiState
    object ImageUploadSuccess : WritePostUiState
}

class WriteMessageScreenViewModel : ViewModel() {

    companion object { // static items are inside this
        const val COLLECTION_POSTS = "posts"
    }

    var writePostUiState: WritePostUiState by mutableStateOf(
        WritePostUiState.Init)
    private lateinit var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }

    fun uploadPost(
        title: String,
        postBody: String,
        imageUrl: String = ""
    ) {
        writePostUiState = WritePostUiState.LoadingPostUpload

        val myPost = Post(
            uid = auth.currentUser!!.uid,
            author = auth.currentUser!!.email!!,
            postDate = Date(System.currentTimeMillis()).toString(),
            postTitle = title,
            postBody = postBody,
            imgUrl = imageUrl
        )

        val postCollection = FirebaseFirestore.getInstance().collection(
            COLLECTION_POSTS
        )

        postCollection.add(myPost)
            .addOnSuccessListener {
                writePostUiState = WritePostUiState.PostUploadSuccess
            }
            .addOnFailureListener {
                writePostUiState = WritePostUiState.ErrorDuringPostUpload(
                    it.message)
            }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    public fun uploadPostImage(
        contentResolver: ContentResolver,
        imageUri: Uri,
        title: String,
        postBody: String
    ) {
        viewModelScope.launch {
            writePostUiState = WritePostUiState.LoadingImageUpload

            val source = ImageDecoder.createSource(contentResolver,
                imageUri)
            val bitmap = ImageDecoder.decodeBitmap(source)

            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG,
                100, baos)
            val imageInBytes = baos.toByteArray()

            val supabase = createSupabaseClient(
                supabaseUrl = "https://kluinxnebbxvjvyyganf.supabase.co",
                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtsdWlueG5lYmJ4dmp2eXlnYW5mIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTUwOTIzODAsImV4cCI6MjExMDY2ODM4MH0.DYLsSk6q14F2qsVLm2DeiI4C47EHEeajpUKTOU47iJU",
            ) {
                install(Postgrest)
                install(Storage)
            }

            val bucket = supabase.storage.from("forumimages")

            val fileName = "uploads/${UUID.randomUUID().toString()}.jpg"
            bucket.upload(fileName, imageInBytes)
            val imgUrl = bucket.publicUrl(fileName)

            uploadPost(title, postBody, imgUrl)
        }
    }


}