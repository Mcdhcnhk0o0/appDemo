package com.example.appdemo.activity.home.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appdemo.R
import com.idlefish.flutterboost.containers.FlutterBoostFragment

class MineFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mView = inflater.inflate(R.layout.fragment_mine, null)
        val flutterFragment = FlutterBoostFragment.CachedEngineFragmentBuilder()
            .url("flutter://demo1")
            .build<FlutterBoostFragment>()
        parentFragmentManager.beginTransaction()
            .add(R.id.mine_flutter_fragment, flutterFragment).commitAllowingStateLoss()
        return mView
    }

}