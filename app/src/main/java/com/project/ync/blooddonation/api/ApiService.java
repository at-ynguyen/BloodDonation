package jp.welby.pah.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import jp.welby.pah.api.body.AuthenticationBody;
import jp.welby.pah.api.body.CreateMedicalBody;
import jp.welby.pah.api.body.InhalationBody;
import jp.welby.pah.api.body.LoginBody;
import jp.welby.pah.api.body.LoginNonAccountBody;
import jp.welby.pah.api.body.MailReportBody;
import jp.welby.pah.api.body.RegistrationBody;
import jp.welby.pah.api.body.ResendAuthCodeBody;
import jp.welby.pah.api.body.ResetPasswordBody;
import jp.welby.pah.api.body.TransferBody;
import jp.welby.pah.api.body.UpdateInfoDetailUserBody;
import jp.welby.pah.api.body.UpdateMedicineListBody;
import jp.welby.pah.api.body.UpdateMedicineSettingBody;
import jp.welby.pah.api.body.UpdateNotificationBody;
import jp.welby.pah.api.body.UpdateStatusMedicineBody;
import jp.welby.pah.api.body.UpdateSymptomListBody;
import jp.welby.pah.api.response.AuthenticationResponse;
import jp.welby.pah.api.response.BaseInfoResponse;
import jp.welby.pah.api.response.CalendarListResponse;
import jp.welby.pah.api.response.ComingSoonMedicalResponse;
import jp.welby.pah.api.response.CreateMedicalResponse;
import jp.welby.pah.api.response.DataUserResponse;
import jp.welby.pah.api.response.DetailAddressResponse;
import jp.welby.pah.api.response.InhalationResponse;
import jp.welby.pah.api.response.LoginNonAccountResponse;
import jp.welby.pah.api.response.LoginResponse;
import jp.welby.pah.api.response.MailReportResponse;
import jp.welby.pah.api.response.MedicalListResponse;
import jp.welby.pah.api.response.MedicineListResponse;
import jp.welby.pah.api.response.MedicineRemindResponse;
import jp.welby.pah.api.response.MedicineSettingResponse;
import jp.welby.pah.api.response.MedicineTableResponse;
import jp.welby.pah.api.response.NotificationHomeResponse;
import jp.welby.pah.api.response.NotificationResponse;
import jp.welby.pah.api.response.RegistrationResponse;
import jp.welby.pah.api.response.ResendAuthCodeResponse;
import jp.welby.pah.api.response.ResetPasswordResponse;
import jp.welby.pah.api.response.StartNoteResponse;
import jp.welby.pah.api.response.SymptomGraphResponse;
import jp.welby.pah.api.response.SymptomResponse;
import jp.welby.pah.api.response.TrackingDeviceResponse;
import jp.welby.pah.api.response.TransferResponse;
import jp.welby.pah.api.response.UpdateMedicineListResponse;
import jp.welby.pah.api.response.UpdateStatusMedicineResponse;
import jp.welby.pah.api.response.UpdateSymptomListResponse;
import jp.welby.pah.api.response.VersionResponse;
import jp.welby.pah.model.user.User;

/**
 * Api Service
 *
 * @author BiNC
 */
public interface ApiService {
    @GET("/medicine-records")
    Call<MedicineListResponse> fetchMedicineRecordList(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @POST("/medicine-records/update")
    Call<UpdateMedicineListResponse> updateMedicineList(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                        @Body UpdateMedicineListBody updateMedicineListBody);

    @GET("/medicine-reminds")
    Call<MedicineRemindResponse> fetchMedicineRemindList(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                         @Query(Parameter.TAKEN_DATE) String takenDate);

    @GET("/medicine-reminds/view")
    Call<MedicineSettingResponse> fetchMedicineRemindSettingList(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                                 @Query(Parameter.MEDICINE_RECORD_ID) int medicineRecordId);

    @POST("/medicine-reminds/update")
    Call<MedicineSettingResponse> updateMedicineSetting(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                        @Body UpdateMedicineSettingBody updateMedicineSettingBody,
                                                        @Query(Parameter.DATE) String date);

    @POST("/medicine-reminds/update-status")
    Call<UpdateStatusMedicineResponse> updateStatusMedicine(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                            @Body UpdateStatusMedicineBody updateStatusMedicineBody);

    @GET("/symptom-logs")
    Call<SymptomResponse> fetchSymptomList(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                           @Query(Parameter.LOG_DATE) String dateLog);

    @POST("/symptom-logs/update")
    Call<UpdateSymptomListResponse> updateSymptomList(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                      @Body UpdateSymptomListBody updateSymptomListBody);

    @GET("/medical")
    Call<MedicalListResponse> fetchMedicalList(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                               @Query(Parameter.PAGE) int page);

    @POST("/medical/add")
    Call<CreateMedicalResponse> createMedical(@Query(Parameter.ACCESS_TOKEN) String accessToken, @Body CreateMedicalBody createMedicalBody);

    @PUT("/medical/edit/{register_medical_id}")
    Call<CreateMedicalResponse> updateMedical(@Path("register_medical_id") int id, @Query(Parameter.ACCESS_TOKEN) String accessToken, @Body CreateMedicalBody createMedicalBody);

    @POST("/user/add")
    Call<RegistrationResponse> register(@Body RegistrationBody registrationBody);

    @POST("/user/pin")
    Call<AuthenticationResponse> authenticate(@Body AuthenticationBody authenticationBody);

    @PUT("/user/edit")
    Call<User> updateUserInfo(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                              @Body UpdateInfoDetailUserBody updateInfoDetailUserBody);

    @POST("/user/login")
    Call<LoginResponse> login(@Body LoginBody loginBody);

    @POST("user/logout")
    Call<ResponseBody> logout(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @GET("/calendar/data")
    Call<CalendarListResponse> fetchDateDataList(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                 @Query(Parameter.DATE) String date);

    @POST("/user/pincode")
    Call<ResendAuthCodeResponse> resendAuthCode(@Body ResendAuthCodeBody resendAuthCodeBody);

    @POST("/user/reminder")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordBody resetPasswordBody);

    @GET("/medicine-records/table")
    Call<MedicineTableResponse> fetchMedicineTable(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                   @Query(Parameter.MEDICINE_TABLE_DATE) String date);

    @GET("/medical/coming-soon")
    Call<ComingSoonMedicalResponse> fetchMedicalComingSoon(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @GET("/symptom/point-graph")
    Call<BaseInfoResponse> fetchBaseInfoGraph(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @GET("/symptoms/graph")
    Call<SymptomGraphResponse> fetchSymptomGraph(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                 @Query(Parameter.KIND_SYMPTOM_GRAPH) String kind,
                                                 @Query(Parameter.PAGE) int page);

    @POST("/user/create-temp-user")
    Call<LoginNonAccountResponse> fetchUserNonAccount(@Body LoginNonAccountBody loginNonAccountBody);

    @GET("/calendar/start-note")
    Call<StartNoteResponse> fetchStartNote(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @GET("/notifications")
    Call<NotificationResponse> fetchNotification(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @GET("/prefectures")
    Call<DetailAddressResponse> fetchPrefectures();

    @GET("/cities/{prefCode}")
    Call<DetailAddressResponse> fetchCities(@Path(Parameter.PREF_CODE) String prefCode);

    @GET("/facilities/{prefCode}/{cityCode}")
    Call<DetailAddressResponse> fetchFacilities(@Path(Parameter.PREF_CODE) String prefCode,
                                                @Path(Parameter.CITY_CODE) String cityCode);

    @POST("/user/transfer-data")
    Call<TransferResponse> transferData(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                        @Body TransferBody transferBody);

    @POST("device/track")
    Call<TrackingDeviceResponse> trackingDevice(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @GET("/user")
    Call<User> fetchInfoDetailUser(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @GET("/report-email/view")
    Call<MailReportResponse> fetchInfoMailReport(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @POST("/report-email/update")
    Call<MailReportResponse> updateInfoMailReport(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                  @Body MailReportBody mailReportBody);

    @GET("/notifications/customize")
    Call<NotificationHomeResponse> getNotificationHome(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                       @Query(Parameter.DATE) String date,
                                                       @Query(Parameter.TIME) String time);

    @POST("/notifications/log-date")
    Call<NotificationHomeResponse> updateNotification(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                      @Body UpdateNotificationBody updateNotificationBody);

    @GET("/user/check-store-data")
    Call<DataUserResponse> checkUserHasData(@Query(Parameter.ACCESS_TOKEN) String accessToken);

    @GET("/notifications/remind-symptom")
    Call<NotificationHomeResponse> getNotificationSymptom(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                                          @Query(Parameter.DATE) String date);

    @POST("/medicine-reminds/add-inhalation")
    Call<InhalationResponse> updateInhalation(@Query(Parameter.ACCESS_TOKEN) String accessToken,
                                              @Body InhalationBody inhalationBody,
                                              @Query(Parameter.DATE) String date);

    @GET("versions/")
    Call<VersionResponse> getVersionUpdate();
}
