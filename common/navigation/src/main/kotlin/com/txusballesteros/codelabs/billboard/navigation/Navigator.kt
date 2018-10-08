/*
 * Copyright Txus Ballesteros 2018 (@txusballesteros)
 *
 * This file is part of some open source application.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Contact: Txus Ballesteros <txus.ballesteros@gmail.com>
 */
package com.txusballesteros.codelabs.billboard.navigation

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.view.View
import org.funktionale.tries.Try

typealias Navigator = (fragment: Fragment, navigationCommand: NavigationCommand, sharedView: View?) -> Try<Unit>
typealias NavigationCommand = (schema: String) -> Uri

private const val DEFAULT_SCHEMA = "billboard"

internal val navigationImpl: Navigator = { fragment, navigationCommand, sharedView ->
    fragment.activity?.let { activity ->
        val intent = navigationCommand(DEFAULT_SCHEMA).intent()
        whenSupportIntent(activity, intent) {
            if (sharedView != null) {
                val intentOptions = prepareIntentOptions(activity, sharedView)
                activity.startActivity(intent, intentOptions.toBundle())
            } else {
                activity.startActivity(intent)
            }
            Try.Success(Unit)
        }
    }
    Try.Failure(IllegalStateException())
}

private fun prepareIntentOptions(activity: Activity, sharedView: View) =
    ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedView, "poster")

private fun Uri.intent(): Intent = Intent(Intent.ACTION_VIEW, this)

private fun whenSupportIntent(context: Context, intent: Intent, block: () -> Unit) {
    if (intent.resolveActivity(context.packageManager) != null) {
        block()
    } else {
        showNavigationNotFoundDialog(context)
    }
}

private fun showNavigationNotFoundDialog(context: Context) = Dialog(context).apply {
    setContentView(R.layout.dialog_navigation_not_available)
}.show()
