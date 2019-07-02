package joshvdh.com.codechallenge_june

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import joshvdh.com.codechallenge_june.downloads.DownloadsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHost, DownloadsFragment())
            .commit()
    }
}
