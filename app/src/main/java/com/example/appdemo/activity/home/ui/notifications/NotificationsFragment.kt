package com.example.appdemo.activity.home.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appdemo.R
import com.example.appdemo.databinding.FragmentNotificationsBinding
import com.example.appdemo.flutter.FlutterRootFragment
import com.example.appdemo.flutter.FlutterRuntimeUtil
import io.flutter.embedding.android.FlutterFragment

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flutterFragment = FlutterRootFragment.withCachedEngine(FlutterRuntimeUtil.DEFAULT_FLUTTER_ENGINE).build<FlutterFragment>()
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_navigation, flutterFragment)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}