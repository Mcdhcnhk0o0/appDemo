package com.example.appdemo.activity.home.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appdemo.R
import com.example.appdemo.activity.FragmentContainer
import com.example.appdemo.databinding.FragmentNotificationsBinding
import com.example.appdemo.flutter.FlutterRootFragment
import com.example.appdemo.flutter.FlutterRuntimeUtil
import io.flutter.embedding.android.FlutterFragment

class NotificationsFragment : Fragment() {

    private var flutterFragment: FlutterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        acquireFlutterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        acquireFlutterFragment()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                FragmentContainer(
                    modifier = Modifier.height(400.dp),
                    fragment = flutterFragment!!,
                    fragmentManager = requireActivity().supportFragmentManager,
                    commit =  { replace(it, flutterFragment!!) }
                )
            }
        }
    }

    private fun acquireFlutterFragment() {
        if (flutterFragment != null) {
            return
        }
        flutterFragment = FlutterRootFragment
            .withCachedEngine(FlutterRuntimeUtil.DEFAULT_FLUTTER_ENGINE).build<FlutterFragment>()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}