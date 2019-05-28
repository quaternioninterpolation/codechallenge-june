package joshvdh.com.codechallenge_june.models

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmResults

class RealmLiveData<T>(private val realmResults: RealmResults<T>) : LiveData<RealmResults<T>>(),
    RealmChangeListener<RealmResults<T>> {

    override fun onInactive() {
        realmResults.removeChangeListener(this)
    }

    override fun onActive() {
        realmResults.addChangeListener(this)
    }

    override fun onChange(t: RealmResults<T>) {
        value = t
    }
}

fun <T> RealmResults<T>.asLiveData() = RealmLiveData(this)