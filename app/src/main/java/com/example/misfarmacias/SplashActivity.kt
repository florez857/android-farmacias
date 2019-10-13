package com.example.misfarmacias

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {



    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splash)

        initDataBindings()

        //  initActions()
    }

    private fun initDataBindings() {
        //  Utils.setImageToImageView(this, s2bgImageView, R.drawable.star_bg)
    }

//    private fun initActions() {
//      //  exploreButton.setOnClickListener {
//
//            this.finish() }
//    }

    override fun onBackPressed() {

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //Here you can get the size!

        if (!isRunning) {
            isRunning = true

            iconImageView.animate().scaleX(3f).scaleY(3f).alpha(1f).setDuration(0).setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {

                }

                override fun onAnimationEnd(animator: Animator) {

                    iconImageView.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(1000).setListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animator: Animator) {

                        }

                        override fun onAnimationEnd(animator: Animator) {
                            nameTextView.animate().alpha(1f).setDuration(800).setListener(object : Animator.AnimatorListener {
                                override fun onAnimationStart(animator: Animator) {

                                }

                                override fun onAnimationEnd(animator: Animator) {

                                    Handler().postDelayed({
                                        // This method will be executed once the timer is over
                                        // Start your app main activity
                                        val intent: Intent = Intent(this@SplashActivity,HostActivity::class.java)
                                        startActivity(intent)

                                        // close this activity
                                        finish()
                                    },500)





                                    // exploreButton.animate().alpha(1f).setDuration(400).start()
                                }

                                override fun onAnimationCancel(animator: Animator) {

                                }

                                override fun onAnimationRepeat(animator: Animator) {

                                }
                            }).start()

                        }

                        override fun onAnimationCancel(animator: Animator) {

                        }

                        override fun onAnimationRepeat(animator: Animator) {

                        }
                    }).start()

                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            }).start()

        }
    }


}
