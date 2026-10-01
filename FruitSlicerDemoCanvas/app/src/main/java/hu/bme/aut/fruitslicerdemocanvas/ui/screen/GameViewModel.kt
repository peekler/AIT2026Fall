package hu.bme.aut.fruitslicerdemocanvas.ui.screen

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.fruitslicerdemocanvas.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class GameViewModel : ViewModel() {

    companion object {
        const val FRUIT_RADIUS = 60f
    }

    var points by mutableStateOf<List<Offset>>(emptyList())
    var fruitPoints by mutableStateOf<List<Offset>>(emptyList())
    var gameRunning = false



    var mediaSwordPrepared = false
    var mediaSplashPrepared = false

    fun initMedia(context: Context) {
        if (mediaSword == null) {
            mediaSword = MediaPlayer.create(context, R.raw.sword)
            mediaSwordPrepared = mediaSword != null
        }
        if (mediaSplash == null) {
            mediaSplash = MediaPlayer.create(context, R.raw.splash)
            mediaSplashPrepared = mediaSplash != null
        }
    }

    fun playSword() {
        if (mediaSwordPrepared) {
            if (mediaSword?.isPlaying == true) {
                mediaSword?.seekTo(0)
            } else {
                mediaSword?.start()
            }
        }
    }

    fun playSplash() {
        if (mediaSplashPrepared) {
            if (mediaSplash?.isPlaying == true) {
                mediaSplash?.seekTo(0)
            } else {
                mediaSplash?.start()
            }
        }
    }

    fun startGame(xMax: Int, yMax: Int) {
        if (gameRunning) return
        gameRunning = true
        val safeX = xMax.coerceAtLeast(1)
        val safeY = yMax.coerceAtLeast(1)
        viewModelScope.launch {
            val rand = Random(System.currentTimeMillis())
            while (gameRunning) {
                fruitPoints += Offset(
                    40f + rand.nextInt(safeX).toFloat(),
                    40f + rand.nextInt(safeY).toFloat()
                )
                delay(2000)
            }
        }
    }

    fun splashFruits(): Boolean {
        val remainingFruits = mutableListOf<Offset>()
        var wasSplash = false
        fruitPoints.forEach { fruitPoint ->
            var fruitSplash = false
            for (swordPoint in points) {
                val dist = (swordPoint - fruitPoint).getDistance()
                if (dist < FRUIT_RADIUS) {
                    fruitSplash = true
                    wasSplash = true
                    break
                }
            }
            if (!fruitSplash) {
                remainingFruits.add(fruitPoint)
            }
        }
        fruitPoints = remainingFruits
        return wasSplash
    }

    fun stopGame() {
        gameRunning = false
        mediaSword?.stop()
        mediaSplash?.stop()
    }

    override fun onCleared() {
        gameRunning = false
        mediaSword?.release()
        mediaSplash?.release()
        mediaSword = null
        mediaSplash = null
        super.onCleared()
    }
}