package joshvdh.com.codechallenge_june.downloads

import android.os.Bundle
import joshvdh.com.codechallenge_june.mvvm.BaseFragment

class DownloadsFragment : BaseFragment<DownloadsViewModel>() {

    override val viewModel = DownloadsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}