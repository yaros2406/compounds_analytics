package com.example.swiftyproteins.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment

class ChooserShareScreen {
    companion object {
        private const val SHARE_MESSAGE = "Hey look what a cool protein"
        private const val SHARE_INTENT_TYPE = "image/*"
        private const val SHARE_CHOOSER_TITLE = "Share To:"

        fun start(fragment: Fragment, uri: Uri) {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = SHARE_INTENT_TYPE
            intent.putExtra(Intent.EXTRA_TEXT, SHARE_MESSAGE)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            try {
                val chooser = Intent.createChooser(intent, SHARE_CHOOSER_TITLE)
                val resInfoList: List<ResolveInfo> = fragment.requireActivity().packageManager
                    .queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY)

                for (resolveInfo in resInfoList) {
                    val packageName = resolveInfo.activityInfo.packageName
                    fragment.requireActivity().grantUriPermission(
                        packageName,
                        uri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                }
                fragment.startActivity(chooser)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(fragment.context, "No App Available", Toast.LENGTH_SHORT).show()
            }
        }
    }
}