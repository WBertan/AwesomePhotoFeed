package com.wbertan.awesomephotofeed.props;

import android.support.annotation.IntDef;

import com.wbertan.awesomephotofeed.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.awesomephotofeed.props.PropsDialogMode.MODE_OK;
import static com.wbertan.awesomephotofeed.props.PropsDialogMode.MODE_YES_NO;

/**
 * Created by william.bertan on 25/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {MODE_OK, MODE_YES_NO})
public @interface PropsDialogMode {
    int MODE_OK = R.layout.dialog_ok;
    int MODE_YES_NO = R.layout.dialog_yes_no;
}
