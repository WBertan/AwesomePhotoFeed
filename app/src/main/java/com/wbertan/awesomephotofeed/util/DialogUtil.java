package com.wbertan.awesomephotofeed.util;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.wbertan.awesomephotofeed.R;
import com.wbertan.awesomephotofeed.props.PropsDialogMode;

import static com.wbertan.awesomephotofeed.R.style.AppTheme;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class DialogUtil extends Dialog {

    private Activity mContext;
    private String mTitle;
    private String mMessage;
    private boolean mCancelable;
    private View.OnClickListener mOkListener;
    private String mOkTextButton;
    private View.OnClickListener mYesListener;
    private String mYesTextButton;
    private View.OnClickListener mNoListener;
    private String mNoTextButton;
    private View.OnClickListener mCancelListener;
    private String mCancelTextButton;
    private int mDialogMode;
    private OnDismissListener mOnDismissListener;
    private OnCancelListener mOnCancelListener;

    private DialogUtil(Activity aContext) {
        this(aContext, AppTheme);
    }

    private DialogUtil(Activity aContext, @StyleRes int aStyleRes) {
        super(aContext, aStyleRes);
        if(getWindow() != null) {
            getWindow().setBackgroundDrawableResource(R.color.apptheme_dialogOutsideBackground);
        }
        mContext = aContext;
        mTitle = mContext.getString(R.string.dialog_default_title_fail);
        mMessage = null;
        mOkTextButton = mContext.getString(R.string.dialog_default_button_text_ok);
        mYesTextButton = mContext.getString(R.string.dialog_default_button_text_yes);
        mNoTextButton = mContext.getString(R.string.dialog_default_button_text_no);
        mCancelTextButton = mContext.getString(R.string.dialog_default_button_text_cancel);
        mCancelable = true;
        mDialogMode = PropsDialogMode.MODE_OK;
    }

    public DialogUtil withDialogMode(@PropsDialogMode int aDialogMode) {
        mDialogMode = aDialogMode;
        return this;
    }

    public DialogUtil withTitle(String aTitle) {
        mTitle = aTitle;
        return this;
    }

    public DialogUtil withMessage(String aMessage) {
        mMessage = aMessage;
        return this;
    }

    public DialogUtil withCancelable(boolean aCancelable) {
        mCancelable = aCancelable;
        return this;
    }

    public DialogUtil withOkButtonListener(View.OnClickListener aOkListener) {
        mOkListener = aOkListener;
        return this;
    }

    public DialogUtil withOkTextButton(String aOkTextButton) {
        mOkTextButton = aOkTextButton;
        return this;
    }

    public DialogUtil withYesButtonListener(View.OnClickListener aYesListener) {
        mYesListener = aYesListener;
        return this;
    }

    public DialogUtil withYesTextButton(String aYesTextButton) {
        mYesTextButton = aYesTextButton.toUpperCase();
        return this;
    }

    public DialogUtil withNoButtonListener(View.OnClickListener aNoListener) {
        mNoListener = aNoListener;
        return this;
    }

    public DialogUtil withNoTextButton(String aNoTextButton) {
        mNoTextButton = aNoTextButton.toUpperCase();
        return this;
    }

    public DialogUtil withCancelButtonListener(View.OnClickListener aCancelListener){
        mCancelListener = aCancelListener;
        return this;
    }

    public DialogUtil withCancelTextButton(String aCancelTextButton) {
        mCancelTextButton = aCancelTextButton;
        return this;
    }

    public DialogUtil withOnDismissListener(OnDismissListener onDismissListener){
        mOnDismissListener = onDismissListener;
        return this;
    }

    public DialogUtil withOnCancelListener(OnCancelListener onCancelListener){
        mOnCancelListener = onCancelListener;
        return this;
    }

    public static DialogUtil instantiate(Activity context) {
        return new DialogUtil(context);
    }

    public static DialogUtil instantiate(Activity context, @StyleRes int aStyleRes) {
        return new DialogUtil(context, aStyleRes);
    }

    @Override
    public void show() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mDialogMode);
        setCancelable(mCancelable);
        if(getWindow() == null) {
            return;
        }
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        final TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        if(textViewTitle != null) {
            textViewTitle.setText(mTitle);
        }

        TextView textViewMessage = (TextView) findViewById(R.id.textViewMessage);
        if(textViewMessage != null) {
            textViewMessage.setText(mMessage);
        }

        if(mOnDismissListener != null){
            setOnDismissListener(mOnDismissListener);
        }

        if(mOnCancelListener != null){
            setOnCancelListener(mOnCancelListener);
        }

        if(mDialogMode == PropsDialogMode.MODE_OK) {
            setDialogButtonValues(R.id.buttonOk, mOkTextButton, mOkListener);
        } else if(mDialogMode == PropsDialogMode.MODE_YES_NO) {
            setDialogButtonValues(R.id.buttonYes, mYesTextButton, mYesListener);
            setDialogButtonValues(R.id.buttonNo, mNoTextButton, mNoListener);
            setDialogButtonValues(R.id.buttonCancel, mCancelTextButton, mCancelListener);
        }
        if(!mContext.isFinishing()) {
            super.show();
        }
    }

    private void setDialogButtonValues(int buttonId, String buttonText, final View.OnClickListener buttonListener) {
        Button button = (Button) findViewById(buttonId);
        button.setText(buttonText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewButton) {
                if(buttonListener != null) {
                    buttonListener.onClick(viewButton.getRootView());
                }
                dismiss();
            }
        });
    }
}
