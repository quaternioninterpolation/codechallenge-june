package joshvdh.com.codechallenge_june.mvvm

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment<V: BaseViewModel> : Fragment() {
    protected abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //
    }
}