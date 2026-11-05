package hu.ait.hiltdemo

import javax.inject.Inject

class Info @Inject constructor(private val detail: Detail) {
    val text = "Hello Hilt ${detail.detailText}"
}

class Detail @Inject constructor() {
    val detailText = "Hello Detail"
}