package com.example.wumbell.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.wumbell.R


class StartupScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as SplashScreenActivity).supportActionBar!!.hide()
        Handler().postDelayed({
                              view?.findNavController()?.navigate(R.id.action_startupScreen_to_introScreen1)
        }, 1000)

        return inflater.inflate(R.layout.appstartup, container, false)
    }

}