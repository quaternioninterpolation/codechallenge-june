package joshvdh.com.codechallenge_june.downloads

import android.app.Application
import io.realm.Realm
import io.realm.kotlin.where
import joshvdh.com.codechallenge_june.models.DownloadedItem
import joshvdh.com.codechallenge_june.models.asLiveData
import joshvdh.com.codechallenge_june.mvvm.BaseViewModel

class DownloadsViewModel(application: Application) : BaseViewModel(application) {

    val downloads = Realm.getDefaultInstance().where<DownloadedItem>().sort("id").findAll().asLiveData()

    init {
    }
}