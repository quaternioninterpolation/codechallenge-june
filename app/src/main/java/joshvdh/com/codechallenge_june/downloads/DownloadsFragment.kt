package joshvdh.com.codechallenge_june.downloads

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import joshvdh.com.codechallenge_june.R
import joshvdh.com.codechallenge_june.mvvm.BaseFragment
import kotlinx.android.synthetic.main.fragment_downloads.view.*
import kotlin.random.Random

class DownloadsFragment : BaseFragment<DownloadsViewModel>(DownloadsViewModel::class.java) {

    private val handler = Handler()
    private var progressDrawable: SampledProgressDrawable? = null

    private fun scheduleRandomSample() {
        if (!isVisible) return

        handler.postDelayed(Runnable {
            scheduleRandomSample()
        }, 100)
    }

    private fun sampleRandom() {
        progressDrawable?.addSample(Random.nextFloat())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_downloads, container, false).apply {

            progressDrawable = SampledProgressDrawable(
                5000,
                100,
                Color.GREEN,
                Color.GRAY
            )
            progressDrawable?.setPlaying(true)
            progressView.background = progressDrawable
        }

    override fun onResume() {
        super.onResume()
        scheduleRandomSample()
    }
}