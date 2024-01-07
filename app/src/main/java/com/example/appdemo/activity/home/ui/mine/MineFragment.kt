package com.example.appdemo.activity.home.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.appdemo.activity.FragmentContainer
import com.example.appdemo.flutter.FlutterRootFragment
import com.example.appdemo.flutter.FlutterRuntimeUtil
import io.flutter.embedding.android.FlutterFragment

class MineFragment : Fragment() {

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
                Text(text = "MineTab")
//                FragmentContainer(
//                    modifier = Modifier.height(400.dp),
//                    fragment = flutterFragment!!,
//                    fragmentManager = requireActivity().supportFragmentManager,
//                    commit =  { replace(it, flutterFragment!!) }
//                )
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