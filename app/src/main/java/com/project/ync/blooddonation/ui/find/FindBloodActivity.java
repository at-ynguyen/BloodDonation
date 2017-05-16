package com.project.ync.blooddonation.ui.find;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.body.FindBloodBody;
import com.project.ync.blooddonation.api.core.ApiCallback;
import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiError;
import com.project.ync.blooddonation.shareds.SharedPreferences_;
import com.project.ync.blooddonation.ui.BaseActivity;
import com.project.ync.blooddonation.ui.dialog.StandardPickerDialog;
import com.project.ync.blooddonation.ui.dialog.StandardPickerDialog_;
import com.project.ync.blooddonation.ui.home.HomeActivity_;
import com.project.ync.blooddonation.ui.wellcome.LoginActivity_;
import com.project.ync.blooddonation.util.DialogUtil;
import com.project.ync.blooddonation.util.KeyboardUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author YNC
 */
@EActivity(R.layout.activity_find_blood)
public class FindBloodActivity extends BaseActivity {
    private static final int IMAGE_PICKER_SELECT = 100;

    @ViewById(R.id.tvSelectBlood)
    TextView mTvSelectBlood;
    @ViewById(R.id.edtNamePost)
    EditText mEdtNamePost;
    @ViewById(R.id.edtContent)
    EditText mEdtContent;
    @ViewById(R.id.edtAddress)
    EditText mEdtAddress;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;
    @ViewById(R.id.rlContent)
    RelativeLayout rlContent;
    @ViewById(R.id.imgView)
    ImageView mImgView;

    @Pref
    SharedPreferences_ mPref;

    private Uri mUri;

    @Override
    protected void init() {
        KeyboardUtil.touchHideKeyboard(FindBloodActivity.this, rlContent);
    }

    @Click(R.id.tvSelectBlood)
    void selectBlood() {
        StandardPickerDialog_.builder().mResourceArray(R.array.blood_type).build().setListenerStandardPicker(new StandardPickerDialog.IListenerStandardPicker() {
            @Override
            public void onClickOk(String item, int index) {
                mTvSelectBlood.setText(item);
            }

            @Override
            public void onDismiss() {

            }
        }).show(getFragmentManager(), null);
    }

    @Click(R.id.btnSubmit)
    void onClickSubmit() {
        mProgressBar.setVisibility(View.VISIBLE);
        RequestBody requestBodyImage = null;
        FindBloodBody findBloodBody = new FindBloodBody(getStringEdittext(mEdtNamePost), getStringEdittext(mEdtContent), mTvSelectBlood.getText().toString(), getStringEdittext(mEdtAddress));
        String json = new Gson().toJson(findBloodBody);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), json);
        if (mUri != null) {
            File file = new File(getRealPathFromURI(mUri));
            if (file.exists()) {
                requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestBodyImage);
                postFindBlood(image, body);
            } else {
                postFindNoImage(findBloodBody);
            }
        } else {
            postFindNoImage(findBloodBody);
        }

    }

    @Click(R.id.tvSelectImage)
    void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICKER_SELECT);
    }

    @OnActivityResult(IMAGE_PICKER_SELECT)
    void selectFromGalleryResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mUri = data.getData();
            mImgView.setVisibility(View.VISIBLE);
            Glide.with(this).load(mUri).centerCrop().into(mImgView);
        }
    }

    private String getStringEdittext(EditText edittext) {
        return edittext.getText().toString();
    }

    public String getRealPathFromURI(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String str = cursor.getString(column_index);
        cursor.close();
        return str;
    }

    private void postFindBlood(MultipartBody.Part image, RequestBody body) {
        Call<FindBloodBody> call = ApiClient.call().createFindBlood(mPref.accessToken().get(), image, body);
        call.enqueue(new ApiCallback<FindBloodBody>() {
            @Override
            public void success(FindBloodBody findBlood) {
                mProgressBar.setVisibility(View.GONE);
                HomeActivity_.intent(FindBloodActivity.this).flags(Intent.FLAG_ACTIVITY_CLEAR_TOP).start();
            }

            @Override
            public void failure(ApiError apiError) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void postFindNoImage(FindBloodBody findBloodBody) {
        Call<FindBloodBody> call = ApiClient.call().createFindBloodNoImage(mPref.accessToken().get(), findBloodBody);
        call.enqueue(new ApiCallback<FindBloodBody>() {
            @Override
            public void success(FindBloodBody findBlood) {
                mProgressBar.setVisibility(View.GONE);
                HomeActivity_.intent(FindBloodActivity.this).flags(Intent.FLAG_ACTIVITY_CLEAR_TOP).start();
            }

            @Override
            public void failure(ApiError apiError) {
                mProgressBar.setVisibility(View.GONE);
                DialogUtil.createErrorDialog(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mPref.accessToken().put("");
                        LoginActivity_.intent(FindBloodActivity.this).start();
                    }
                }, FindBloodActivity.this, "Lỗi", "Thất bại vui lòng kiểm tra lại");
            }
        });
    }

    @Click(R.id.imgBack)
    void onBackClick() {
        onBackPressed();
    }
}
