/*
 * Copyright (C) 2018 Shehab Salah Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shehabsalah.movieappmvpdagger2.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ShehabSalah on 1/8/18.
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    /**
     * This method take the fragment and added to the container View that has id frameId by the
     * fragmentManager.
     * @param fragmentManager responsible on adding the fragment to its container.
     * @param fragment fragment to be added to the fragment manager
     * @param frameId  Container View.
     * */
   public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                            @NonNull Fragment fragment, int frameId){
       FragmentTransaction transaction = fragmentManager.beginTransaction();
       transaction.add(frameId, fragment);
       transaction.commit();
   }
}
