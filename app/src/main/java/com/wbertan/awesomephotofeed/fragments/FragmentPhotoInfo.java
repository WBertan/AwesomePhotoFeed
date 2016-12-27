package com.wbertan.awesomephotofeed.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wbertan.awesomephotofeed.BR;
import com.wbertan.awesomephotofeed.R;
import com.wbertan.awesomephotofeed.adapter.AdapterGeneric;
import com.wbertan.awesomephotofeed.controller.flickr.ControllerFlickr_GetPerson;
import com.wbertan.awesomephotofeed.controller.flickr.ControllerFlickr_GetPhotoInfo;
import com.wbertan.awesomephotofeed.interactor.DefaultObserver;
import com.wbertan.awesomephotofeed.interactor.Params;
import com.wbertan.awesomephotofeed.model.flickr.Person;
import com.wbertan.awesomephotofeed.model.flickr.PersonResponse;
import com.wbertan.awesomephotofeed.model.flickr.Photo;
import com.wbertan.awesomephotofeed.model.flickr.PhotoInfo;
import com.wbertan.awesomephotofeed.model.flickr.PhotoResponse;
import com.wbertan.awesomephotofeed.util.DialogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class FragmentPhotoInfo extends FragmentGeneric implements View.OnClickListener {
    public static final int REQUEST_CODE_PERMISSION_WRITE_ON_EXTERNAL_SAVE = 10;
    public static final int REQUEST_CODE_PERMISSION_WRITE_ON_EXTERNAL_SHARE = 20;

    @Override
    public String getFragmentTitle() {
        return getString(R.string.fragment_photo_info_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_photo_info, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getView() == null) {
            return;
        }

        Bundle bundleArgs = getArguments();
        if(bundleArgs == null || !bundleArgs.containsKey("photo_id") || !bundleArgs.containsKey("photo_link")) {
            DialogUtil.instantiate(getActivity()).withMessage(getString(R.string.fragment_photo_info_dialog_message_problem_loading_photo)).show();
            return;
        }
        String photoId = bundleArgs.getString("photo_id");
        String authorId = bundleArgs.getString("author_id");
        String photoLink = bundleArgs.getString("photo_link");
        String photoTitle = bundleArgs.getString("photo_title");
        String photoDescription = bundleArgs.getString("photo_description");

        Button buttonShare = (Button) getView().findViewById(R.id.buttonShare);
        Button buttonSave = (Button) getView().findViewById(R.id.buttonSave);
        Button buttonOpen = (Button) getView().findViewById(R.id.buttonOpen);
        buttonShare.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonOpen.setOnClickListener(this);

        ImageView imageView = (ImageView) getView().findViewById(R.id.imageViewPhoto);
        Glide.with(imageView.getContext())
                .load(photoLink)
                .placeholder(R.mipmap.logo_awesome_photo_feed_icon)
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        ImageView imageView = (ImageView) getView().findViewById(R.id.imageViewPhoto);
                        PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
                        attacher.update();
                        return false;
                    }
                })
                .into(imageView);

        if(getView() == null) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewPhotoInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterGeneric<PhotoInfo> adapter = new AdapterGeneric<>(R.layout.adapter_photo_info, BR.photoInfo);

        adapter.add(new PhotoInfo("author_id", authorId));
        adapter.add(new PhotoInfo("photo_id", photoId));

        recyclerView.setAdapter(adapter);
        showProgress();
        try {
            Params params = Params.getInstance();
            params.put("author_id", authorId);
            new ControllerFlickr_GetPerson().execute(new PersonObserver(), params);
        } catch (Exception e) {
            e.printStackTrace();
            dismissProgress();
        }
        try {
            Params params = Params.getInstance();
            params.put("photo_id", photoId);
            new ControllerFlickr_GetPhotoInfo().execute(new PhotoInfoObserver(), params);
        } catch (Exception e) {
            e.printStackTrace();
            dismissProgress();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
                || newConfig.orientation == Configuration.ORIENTATION_UNDEFINED){
            if(getView() == null) {
                return;
            }
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.remove(this);
            transaction.commitAllowingStateLoss();
            getActivity().getSupportFragmentManager().executePendingTransactions();
            transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.add(this, getClass().getSimpleName());
            transaction.commitAllowingStateLoss();
            getActivity().getSupportFragmentManager().executePendingTransactions();
        }
    }

    private final class PersonObserver extends DefaultObserver<PersonResponse> {
        @Override
        public void onComplete() {
            dismissProgress();
        }

        @Override
        public void onError(Throwable e) {
            dismissProgress();
            e.printStackTrace();
        }

        @Override
        public void onNext(PersonResponse personResponse) {
            if(getView() == null) {
                return;
            }
            Person person = personResponse.getPerson();
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewPhotoInfo);
            AdapterGeneric<PhotoInfo> adapter = (AdapterGeneric<PhotoInfo>) recyclerView.getAdapter();
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_author_id), person.getId()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_author_username), person.getUsername()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_author_realname), person.getRealname()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_author_photos_url), person.getPhotosurl()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_author_profile_url), person.getProfileurl()));
        }
    }

    private final class PhotoInfoObserver extends DefaultObserver<PhotoResponse> {
        @Override
        public void onComplete() {
            dismissProgress();
        }

        @Override
        public void onError(Throwable e) {
            dismissProgress();
            e.printStackTrace();
        }

        @Override
        public void onNext(PhotoResponse photoResponse) {
            if(getView() == null) {
                return;
            }
            Photo photoData = photoResponse.getPhoto();
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewPhotoInfo);
            AdapterGeneric<PhotoInfo> adapter = (AdapterGeneric<PhotoInfo>) recyclerView.getAdapter();
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_photo_id), photoData.getId()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_photo_secret), photoData.getSecret()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_photo_server), photoData.getServer()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_photo_date_uploaded), SimpleDateFormat.getDateTimeInstance().format(new Date(photoData.getDateuploaded()))));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_photo_original_secret), photoData.getOriginalsecret()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_photo_original_format), photoData.getOriginalformat()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_photo_title), photoData.getTitle()));
            adapter.add(new PhotoInfo(getString(R.string.fragment_photo_info_label_photo_description), photoData.getDescription()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(getView() == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_WRITE_ON_EXTERNAL_SAVE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImageToGallery(requestCode);
                } else {
                    Button buttonSave = (Button) getView().findViewById(R.id.buttonSave);
                    buttonSave.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case REQUEST_CODE_PERMISSION_WRITE_ON_EXTERNAL_SHARE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareImage();
                } else {
                    Button buttonShare = (Button) getView().findViewById(R.id.buttonShare);
                    buttonShare.setVisibility(View.INVISIBLE);
                }
                break;
            }
        }
    }

    String saveImageToGallery(int aRequestCode) {
        if(getView() == null) {
            return null;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, aRequestCode);
            return null;
        }
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageViewPhoto);
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmapImageView = imageView.getDrawingCache();
        String returnInsertImage = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmapImageView, String.valueOf(System.currentTimeMillis()), null);
        if(aRequestCode == REQUEST_CODE_PERMISSION_WRITE_ON_EXTERNAL_SAVE) {
            DialogUtil.instantiate(getActivity()).withTitle(getString(R.string.dialog_default_title_success)).withMessage(getString(R.string.fragment_photo_info_save_success)).show();
        }
        return returnInsertImage;
    }

    void shareImage() {
        if(getView() == null) {
            return;
        }
        String returnInsertImage = saveImageToGallery(REQUEST_CODE_PERMISSION_WRITE_ON_EXTERNAL_SHARE);
        if(returnInsertImage == null) {
            return;
        }
        Uri imageUri = Uri.parse(returnInsertImage);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.fragment_photo_info_share_text));
        Intent chooser=Intent.createChooser(shareIntent,getString(R.string.fragment_photo_info_share_chooser_text));
        try {
            startActivity(chooser);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
            DialogUtil.instantiate(getActivity()).withMessage(getString(R.string.fragment_photo_info_dialog_message_no_app_action)).show();
            Button buttonShare = (Button) getView().findViewById(R.id.buttonShare);
            buttonShare.setVisibility(View.INVISIBLE);
        }
    }

    void openImageInBrowser() {
        if(getView() == null) {
            return;
        }
        Bundle bundleArgs = getArguments();
        String photoLink = bundleArgs.getString("photo_link");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(photoLink));
        try {
            startActivity(launchBrowser);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
            DialogUtil.instantiate(getActivity()).withMessage(getString(R.string.fragment_photo_info_dialog_message_no_app_action)).show();
            Button buttonOpen = (Button) getView().findViewById(R.id.buttonOpen);
            buttonOpen.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSave) {
            saveImageToGallery(REQUEST_CODE_PERMISSION_WRITE_ON_EXTERNAL_SAVE);
        } else if(view.getId() == R.id.buttonShare) {
            shareImage();
        } else if(view.getId() == R.id.buttonOpen) {
            openImageInBrowser();
        }
    }
}
