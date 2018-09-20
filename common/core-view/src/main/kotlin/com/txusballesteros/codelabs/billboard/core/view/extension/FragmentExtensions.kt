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
package com.txusballesteros.codelabs.billboard.core.view.extension

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.util.TypedValue
import org.funktionale.option.Option
import org.jetbrains.anko.bundleOf

fun Fragment.attach(@IdRes placeHolder: Int, fragment: Fragment) =
    childFragmentManager.attach(placeHolder, fragment)

fun Fragment.remove(fragment: Fragment) =
    childFragmentManager.remove(fragment)

inline fun <reified T : Fragment> Fragment.findById(@IdRes id: Int, block: (T) -> Unit) =
    childFragmentManager.findFragmentById(id)?.let { block(it as T) }

inline fun <reified T : Fragment> Fragment.findByTag(block: (T) -> Unit) =
    childFragmentManager.findByTag<T> { block(it) }

inline fun <reified T : Fragment> Fragment.findByTag(): Option<T> =
    childFragmentManager.findByTag()

inline fun <reified T : Fragment> Fragment.findByTagOrNull(): T? =
    childFragmentManager.findByTagOrNull()

fun Fragment.add(@IdRes placeHolder: Int, fragment: Fragment) =
    childFragmentManager.add(placeHolder, fragment)

fun <T : Fragment> T.withArguments(vararg arguments: Pair<String, Any?>): T {
    this.arguments = bundleOf(*arguments)
    return this
}

fun Fragment.dp2px(value: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)