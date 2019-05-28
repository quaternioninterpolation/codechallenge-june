package joshvdh.com.codechallenge_june.mvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment<out V: BaseViewModel>(clazz: Class<V>) : Fragment() {
    protected val viewModel by lazy { ViewModelProviders.of(this).get<V>(clazz) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}