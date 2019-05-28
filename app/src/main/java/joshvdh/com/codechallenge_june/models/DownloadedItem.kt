package joshvdh.com.codechallenge_june.models

import io.realm.RealmObject
import java.util.*

open class DownloadedItem : RealmObject() {
    private var id = ""
    var name = ""; private set
    var filePath = ""; private set
    var fileSizeBytes = 0L; private set
    var completedAt: Date? = null; private set

    //TODO: Create from companion object
}