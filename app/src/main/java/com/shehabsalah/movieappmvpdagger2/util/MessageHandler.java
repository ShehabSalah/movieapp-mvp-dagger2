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

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ShehabSalah on 1/8/18.
 * This provides methods to help activities to show dialogs
 */

public class MessageHandler {
    private static AlertDialog.Builder dialogBuilder;
    private static AlertDialog dialog;

    /**
     * This method take the application context, message and dialog onClickListener and build, display
     * dialog.
     *
     * @param mContext        the activity context.
     * @param message         message that will appear in the dialog.
     * @param onClickListener passed listener to notify back the user action (OK or CANCEL).
     */
    public static void alertDialog(Context mContext, String message, DialogInterface.OnClickListener onClickListener) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setMessage(message);
        if (onClickListener != null) {
            dialogBuilder.setPositiveButton("OK", onClickListener);
        } else {
            dialogBuilder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    dialogBuilder = null;
                }
            });
        }

        dialog = dialogBuilder.create();
        if (!((Activity) mContext).isFinishing())
            dialog.show();
    }

}
