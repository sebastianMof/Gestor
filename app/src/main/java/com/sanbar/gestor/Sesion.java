package com.sanbar.gestor;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Sesion implements Parcelable {

    //FOR HTTP
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    //TASKS DECLARATIONS
    private Token mToken = null;
    private Contratistas mContratistas = null;
    private Contracts mContracts = null;
    private CambioContrato mCambioContrato = null;
    private WorkerCategorias mWorkerCategorias = null;
    private Workers mWorkers = null;
    private WorkersWorkerId mWorkersWorkerId = null;
    private TipoEquipos mTipoEquipos = null;
    private Equipos mEquipos = null;
    private EquiposEquipoId mEquiposEquipoId = null;
    private Especialidades mEspecialidades = null;
    private AreaContratos mAreaContratos = null;
    private TareaStatus mTareaStatus = null;
    private Tareas mTareas = null;
    private TareasTareaId mTareasTareaId = null;
    private FinalizarTarea mFinalizarTarea = null;
    private IniciarTarea mIniciarTarea = null;
    private Interrupciones mInterrupciones = null;
    private CausasInmediatas mCausasInmediatas = null;
    private CausasInmediatasCausaId mCausasInmediatasCausaId = null;
    private TerminarInterrupcion mTerminarInterrupcion = null;
    private Responsables mResponsables = null;
    private Layouts mLayouts = null;
    private PodSummary mPodSummary = null;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Sesion createFromParcel(Parcel in) {
            return new Sesion(in);
        }

        public Sesion[] newArray(int size) {
            return new Sesion[size];
        }
    };

    private String mail;
    private String password;
    private String token;
    private String userId;
    private String selectedContractId;
    private String selectedContractName;
    private String lastContractId;
    private String fullNameComputed;
    private String contractCode;
    private String apiCode;
    private String contracts;
    private String workerCategorias;
    private String workers;
    private String workersWorkerId;
    private String especialidades;
    private String tareaStatus;
    private String areaContratos;
    private String tareas;
    private String tipoEquipos;
    private String equipos;
    private String equiposEquipoId;
    private String causasInmediatas;
    private String causasInmediatasCausaId;
    private String tareasTareaId;
    private String responsables;
    private String layouts;
    private String podSummary;

    //CONSTRUCTOR
    public Sesion(String mail, String password) {
        this.mail = mail;
        this.password = password;

    //cambiar luego de terminar
    }

    // PARCELING
    public Sesion(Parcel in) {
        this.mail = in.readString();
        this.password = in.readString();
        this.token = in.readString();
        this.userId = in.readString();
        this.selectedContractId = in.readString();
        this.selectedContractName = in.readString();
        this.lastContractId = in.readString();
        this.fullNameComputed = in.readString();
        this.contractCode = in.readString();
        this.apiCode = in.readString();
        this.contracts = in.readString();
        this.workerCategorias = in.readString();
        this.workers = in.readString();
        this.workersWorkerId = in.readString();
        this.especialidades = in.readString();
        this.tareaStatus = in.readString();
        this.areaContratos = in.readString();
        this.tareas = in.readString();
        this.tipoEquipos = in.readString();
        this.equipos = in.readString();
        this.equiposEquipoId = in.readString();
        this.causasInmediatas = in.readString();
        this.causasInmediatasCausaId = in.readString();
        this.tareasTareaId = in.readString();
        this.layouts = in.readString();
        this.podSummary = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.mail);
        dest.writeString(this.password);
        dest.writeString(this.token);
        dest.writeString(this.userId);
        dest.writeString(this.selectedContractId);
        dest.writeString(this.selectedContractName);
        dest.writeString(this.lastContractId);
        dest.writeString(this.fullNameComputed);
        dest.writeString(this.contractCode);
        dest.writeString(this.apiCode);
        dest.writeString(this.contracts);
        dest.writeString(this.workerCategorias);
        dest.writeString(this.workers);
        dest.writeString(this.workersWorkerId);
        dest.writeString(this.especialidades);
        dest.writeString(this.tareaStatus);
        dest.writeString(this.areaContratos);
        dest.writeString(this.tareas);
        dest.writeString(this.tipoEquipos);
        dest.writeString(this.equipos);
        dest.writeString(this.equiposEquipoId);
        dest.writeString(this.causasInmediatas);
        dest.writeString(this.causasInmediatasCausaId);
        dest.writeString(this.tareasTareaId);
        dest.writeString(this.layouts);
        dest.writeString(this.podSummary);

    }

    @Override
    public String toString() {
        return "Sesion{" +
                " mail='" + mail + '\'' +
                " password='" + password + '\'' +
                " token='" + token + '\'' +
                " userId='" + userId + '\'' +
                " selectedContractId='" + selectedContractId + '\'' +
                " lastContractId='" + lastContractId + '\'' +
                " fullNameComputed='" + fullNameComputed + '\'' +
                " contractCode='" + contractCode + '\'' +
                " apiCode='" + apiCode + '\'' +
                '}';
    }

    //SETTERS & GETTERS
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSelectedContractId() {
        return selectedContractId;
    }

    public void setSelectedContractId(String selectedContractId) {
        this.selectedContractId = selectedContractId;
    }

    public String getSelectedContractName() {
        return selectedContractName;
    }

    public void setSelectedContractName(String selectedContractName) {
        this.selectedContractName = selectedContractName;
    }

    public String getLastContractId() {
        return lastContractId;
    }

    public void setLastContractId(String lastContractId) {
        this.lastContractId = lastContractId;
    }

    public String getFullNameComputed() {
        return fullNameComputed;
    }

    public void setFullNameComputed(String fullNameComputed) {
        this.fullNameComputed = fullNameComputed;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getContracts() {
        return contracts;
    }

    public void setContracts(String contracts) {
        this.contracts = contracts;
    }

    public String getWorkerCategorias() {
        return workerCategorias;
    }

    public void setWorkerCategorias(String workerCategorias) {
        this.workerCategorias = workerCategorias;
    }

    public String getWorkers() {
        return workers;
    }

    public void setWorkers(String workers) {
        this.workers = workers;
    }

    public String getWorkersWorkerId() {
        return workersWorkerId;
    }

    public void setWorkersWorkerId(String workersWorkerId) {
        this.workersWorkerId = workersWorkerId;
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public String getTareaStatus() {
        return tareaStatus;
    }

    public void setTareaStatus(String tareaStatus) {
        this.tareaStatus = tareaStatus;
    }

    public String getAreaContratos() {
        return areaContratos;
    }

    public void setAreaContratos(String areaContratos) {
        this.areaContratos = areaContratos;
    }

    public String getTareas() {
        return tareas;
    }

    public void setTareas(String tareas) {
        this.tareas = tareas;
    }

    public String getTipoEquipos() {
        return tipoEquipos;
    }

    public void setTipoEquipos(String tipoEquipos) {
        this.tipoEquipos = tipoEquipos;
    }

    public String getEquipos() {
        return equipos;
    }

    public void setEquipos(String equipos) {
        this.equipos = equipos;
    }

    public String getEquiposEquipoId() {
        return equiposEquipoId;
    }

    public void setEquiposEquipoId(String equiposEquipoId) {
        this.equiposEquipoId = equiposEquipoId;
    }

    public String getCausasInmediatas() {
        return causasInmediatas;
    }

    public void setCausasInmediatas(String causasInmediatas) {
        this.causasInmediatas = causasInmediatas;
    }

    public String getCausasInmediatasCausaId() {
        return causasInmediatasCausaId;
    }

    public void setCausasInmediatasCausaId(String causasInmediatasCausaId) {
        this.causasInmediatasCausaId = causasInmediatasCausaId;
    }

    public String getTareasTareaId() {
        Log.e("TEST",tareasTareaId);
        return tareasTareaId;
    }

    public void setTareasTareaId(String tareasTareaId) {
        this.tareasTareaId = tareasTareaId;
    }

    public String getResponsables() {
        return responsables;
    }

    public void setResponsables(String responsables) {
        this.responsables = responsables;
    }

    public String getLayouts() {
        return layouts;
    }

    public void setLayouts(String layouts) {
        this.layouts = layouts;
    }

    public String getPodSummary() {
        return podSummary;
    }

    public void setPodSummary(String podSummary) {
        this.podSummary = podSummary;
    }

    //TASK CLASSES
    public class Token extends AsyncTask<Void, Void, Boolean> {

        Token() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            String postdata = "username="+mail+"&password="+password+"&grant_type=password";

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/Token")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {

                        JSONObject obj = new JSONObject(jsonResponse);

                        setMail(obj.getString("userName"));
                        setToken(obj.getString("access_token"));

                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mToken = null;
            //showProgress(false);

            if (success) {
                //DoSomethingWithSuccess

            } else {
                //DoSomethingWithFailure
            }
        }

        @Override
        protected void onCancelled() {
            mToken = null;
            //showProgress(false);
        }
    }

    public class Contratistas extends AsyncTask<Void, Void, Boolean> {

        Contratistas() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Contratistas/"+"?email="+getMail())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {

                        JSONObject obj = new JSONObject(jsonResponse);

                        userId = (
                                obj.has("Id")?
                                obj.getString("Id"):null
                        );
                        fullNameComputed = (
                                obj.has("FullNameComputed")?
                                obj.getString("FullNameComputed"):null
                        );
                        contractCode = (
                                obj.has("ContractCode")?
                                obj.getString("ContractCode"):null
                        );
                        lastContractId = (
                                obj.has("LastContractId")?
                                obj.getString("LastContractId"):null
                        );
                        apiCode = (
                                obj.has("ApiCode")?
                                obj.getString("ApiCode"):null
                        );
                        selectedContractName = (
                                obj.has("ContractName")?
                                        obj.getString("ContractName"):null
                        );


                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mContratistas = null;
            //showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mContratistas = null;
            //showProgress(false);
        }
    }

    public class Contracts extends AsyncTask<Void, Void, Boolean> {

        Contracts() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/apiv2/Contractors/"+getUserId()+"/Contracts")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    setContracts(jsonResponse);

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mContracts = null;
            //showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mContracts = null;
            //showProgress(false);
        }
    }

    public class CambioContrato extends AsyncTask<Void, Void, Boolean> {

        CambioContrato() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
                postdata.put("Id",getUserId());
                postdata.put("LastContractId",getSelectedContractId());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/CambioContrato")
                    .put(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mCambioContrato = null;
            //showProgress(false);

            if (success) {
                Log.e("TEST","SUCCESS cambio contrato");
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mCambioContrato = null;
            //showProgress(false);
        }
    }

    public class WorkerCategorias extends AsyncTask<Void, Void, Boolean> {

        WorkerCategorias() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/WorkerCategorias")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();
                    setWorkerCategorias(jsonResponse);

                }


                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mWorkerCategorias = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","OK");

            } else {

                Log.e("TEST","NOOK");
            }
        }

        @Override
        protected void onCancelled() {
            mWorkerCategorias = null;
            //showProgress(false);
        }
    }

    public class Workers extends AsyncTask<Void, Void, Boolean> {

        String filter;
        String categoriaId;

        Workers(String filter, String categoriaId) {
            this.filter=filter;
            this.categoriaId=categoriaId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            String url ="https://ezprogpdar-apiproductividad.azurewebsites.net/api/Workers";


            if(filter != null || categoriaId != null ){
                url+=("?");
            }

            if(filter != null ){
                url+=("filter="+filter);
            }

            if (filter!=null && categoriaId != null ){
                url+="&";
            }

            if (categoriaId!=null){
                url+=("categoriaId="+categoriaId);
            }

            if (filter!=null || categoriaId != null){
                url+="&";
            }

            if (filter==null){
                if (categoriaId==null){
                    url+="?";
                }
            }

            url+=("contractId="+getLastContractId());

            final Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();


            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {
                    String jsonResponse = response.body().string();
                    setWorkers(jsonResponse);
                    Log.e("TEST",jsonResponse);
                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mWorkers = null;
            //showProgress(false);

            if (success) {
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            mWorkers = null;
            //showProgress(false);
        }
    }

    public class WorkersWorkerId extends AsyncTask<Void, Void, Boolean> {

        private String workerId;

        WorkersWorkerId(String workerId) {
            this.workerId = workerId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Workers/"+workerId)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    setWorkersWorkerId(jsonResponse);

                }


                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mWorkersWorkerId = null;
            //showProgress(false);

            if (success) {


            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mWorkersWorkerId = null;
            //showProgress(false);
        }
    }

    public class TipoEquipos extends AsyncTask<Void, Void, Boolean> {

        TipoEquipos() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/TipoEquipos")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();
                    try {

                        JSONArray array = new JSONArray(jsonResponse);
                        setTipoEquipos(jsonResponse);


                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }


                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mTipoEquipos = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mTipoEquipos = null;
            //showProgress(false);
        }
    }

    public class Equipos extends AsyncTask<Void, Void, Boolean> {

        String filter;
        String tipoId;

        Equipos(String filter, String tipoId) {
            this.filter = filter;
            this.tipoId = tipoId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            String url ="https://ezprogpdar-apiproductividad.azurewebsites.net/apiv2/Equipment";


            if(filter != null || tipoId != null ){
                url+=("?");
            }

            if(filter != null ){
                url+=("filter="+filter);
            }

            if (filter!=null && tipoId != null ){
                url+="&";
            }

            if (tipoId!=null){
                url+=("tipoId="+tipoId);
            }

            if (filter!=null || tipoId != null){
                url+="&";
            }

            if (filter==null){
                if (tipoId==null){
                    url+="?";
                }
            }

            url+=("ContractId="+getLastContractId());

            Log.e("TEST",url);

            final Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {


                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    Log.e("TEST", jsonResponse);

                        try {
                        JSONArray array = new JSONArray(jsonResponse);
                        setEquipos(jsonResponse);

                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }



                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mEquipos = null;
            //showProgress(false);

            if (success) {
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            mEquipos = null;
            //showProgress(false);
        }
    }

    public class EquiposEquipoId extends AsyncTask<Void, Void, Boolean> {

        private String equipoId;

        EquiposEquipoId(String equipoId) {
            this.equipoId = equipoId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Equipos/"+equipoId)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONObject obj = new JSONObject(jsonResponse);
                        setEquiposEquipoId(jsonResponse);

                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }


                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mEquiposEquipoId = null;
            //showProgress(false);

            if (success) {
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            mEquiposEquipoId = null;
            //showProgress(false);
        }
    }

    public class Especialidades extends AsyncTask<Void, Void, Boolean> {

        Especialidades() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Especialidades")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    setEspecialidades(jsonResponse);

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mEspecialidades = null;
            //showProgress(false);

            if (success) {

            } else {
            }
        }

        @Override
        protected void onCancelled() {
            mEspecialidades = null;
            //showProgress(false);
        }
    }

    public class AreaContratos extends AsyncTask<Void, Void, Boolean> {

        AreaContratos() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            //params opcionales
            RequestBody body = new FormBody.Builder()
                    .add("isFinalizado", "")// Se puede mandar cualquier argumento, el hecho de de agregar el campo lo hace true
                    .add("isInterrupcion", "")// Se puede mandar cualquier argumento, el hecho de de agregar el campo lo hace true
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/AreaContratos")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    setAreaContratos(jsonResponse);

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAreaContratos = null;
            //showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mAreaContratos = null;
            //showProgress(false);
        }
    }

    public class TareaStatus extends AsyncTask<Void, Void, Boolean> {

        TareaStatus() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            //params opcionales
            RequestBody body = new FormBody.Builder()
                    .add("isFinalizado", "")// Se puede mandar cualquier argumento, el hecho de de agregar el campo lo hace true
                    .add("isInterrupcion", "")// Se puede mandar cualquier argumento, el hecho de de agregar el campo lo hace true
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/TareaStatus")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();
                    setTareaStatus(jsonResponse);

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mTareaStatus = null;
            //showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mTareaStatus = null;
            //showProgress(false);
        }
    }

    public class Tareas extends AsyncTask<Void, Void, Boolean> {

        String nombreCapataz;
        String especialidadId;
        String areaId;
        String statusTareaId;

        Tareas(String nombreCapataz, String especialidadId, String areaId, String statusTareaId) {
            this.nombreCapataz=nombreCapataz;
            this.especialidadId=especialidadId;
            this.areaId=areaId;
            this.statusTareaId=statusTareaId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            if (nombreCapataz!= null && nombreCapataz.equals(""))
                nombreCapataz=null;

            String url = "https://ezprogpdar-apiproductividad.azurewebsites.net/api/Tareas?";

            if (nombreCapataz!=null){
                url+="nombreCapataz="+nombreCapataz;
            }

            if (especialidadId!=null){
                if (url.substring(url.length() - 1).equals("?")){
                    url+="especialidadId="+especialidadId;
                } else {
                    url+="&especialidadId="+especialidadId;
                }
            }

            if (areaId!=null){
                if (url.substring(url.length() - 1).equals("?")){
                    url+="areaId="+areaId;
                } else {
                    url+="&areaId="+areaId;
                }
            }

            if (statusTareaId!=null){
                if (url.substring(url.length() - 1).equals("?")){
                    url+="statusTareaId="+statusTareaId;
                } else {
                    url+="&statusTareaId="+statusTareaId;
                }
            }

            if (url.substring(url.length() - 1).equals("?")){
                url+="contractId="+getLastContractId();
            } else {
                url+="&contractId="+getLastContractId();
            }



            final Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONArray array = new JSONArray(jsonResponse);
                        setTareas(jsonResponse);

                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mTareas = null;
            //showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mTareas = null;
            //showProgress(false);
        }
    }

    public class TareasTareaId extends AsyncTask<Void, Void, Boolean> {

        private String tareaId;

        TareasTareaId(String tareaId) {
            this.tareaId = tareaId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Tareas/"+tareaId)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONObject obj = new JSONObject(jsonResponse);
                        setTareasTareaId(jsonResponse);

                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }


                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mTareasTareaId = null;
            //showProgress(false);

            if (success) {


            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mTareasTareaId = null;
            //showProgress(false);
        }
    }

    public class FinalizarTarea extends AsyncTask<Void, Void, Boolean> {

        private String tareaId;
        private String terminoReal;
        private String cantidad;

        FinalizarTarea(String tareaId, String terminoReal, String cantidad) {
            this.tareaId = tareaId;
            this.terminoReal = terminoReal;
            this.cantidad = cantidad;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("Id", tareaId)// id de la tarea a finalizar
                    .add("TerminoReal", terminoReal)
                    .add("CantidadCompletada", cantidad)//cantidad completada
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/FinalizarTarea")
                    .put(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();
                    Log.e("TEST",jsonResponse);

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mFinalizarTarea = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mFinalizarTarea = null;
            //showProgress(false);
        }
    }

    public class IniciarTarea extends AsyncTask<Void, Void, Boolean> {

        private String tareaId;
        private String inicioReal;

        IniciarTarea(String tareaId, String inicioReal) {
            this.tareaId = tareaId;
            this.inicioReal = inicioReal;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("Id", tareaId)// id de la tarea a finalizar
                    .add("InicioReal", inicioReal)
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/IniciarTarea")
                    .put(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();
                    Log.e("TEST",jsonResponse);

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mIniciarTarea = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mIniciarTarea = null;
            //showProgress(false);
        }
    }

    public class Interrupciones extends AsyncTask<Void, Void, Boolean> {

        private String tareaId;
        private String encargadoId;
        private String causaInmediataId;
        private String horaInicio;
        private String horaTerminoEstimado;

        Interrupciones(String tareaId, String encargadoId, String causaInmediataId, String horaInicio, String horaTerminoEstimado) {
            this.tareaId = tareaId;
            this.encargadoId = encargadoId;
            this.causaInmediataId = causaInmediataId;
            this.horaInicio = horaInicio;
            this.horaTerminoEstimado = horaTerminoEstimado;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject auxBody = new JSONObject();
            try {
                auxBody.put("TaskId", tareaId);
                auxBody.put("ContractorId", encargadoId);
                auxBody.put("ImmediateCauseId", causaInmediataId);
                auxBody.put("HoraInicio", horaInicio);
                auxBody.put("HoraTérminoProgramado", horaTerminoEstimado);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, String.valueOf(auxBody));

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Interrupciones")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mInterrupciones = null;
            //showProgress(false);

            if (success) {

            } else {
            }
        }

        @Override
        protected void onCancelled() {
            mInterrupciones = null;
            //showProgress(false);
        }
    }

    public class CausasInmediatas extends AsyncTask<Void, Void, Boolean> {

        CausasInmediatas() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();


            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/CausasInmediatas?causasPadre=TRUE")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();
                    Log.e("TEST",jsonResponse);
                    try {

                        JSONArray array = new JSONArray(jsonResponse);
                        setCausasInmediatas(jsonResponse);


                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }


                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mCausasInmediatas = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","CAUSAS INMEDIATAS OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mCausasInmediatas = null;
            //showProgress(false);
        }
    }

    public class CausasInmediatasCausaId extends AsyncTask<Void, Void, Boolean> {

        String causaId;

        CausasInmediatasCausaId(String causaId) {
            this.causaId=causaId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();


            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/CausasInmediatas/"+causaId)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();
                    Log.e("TEST",jsonResponse);
                    try {
                        setCausasInmediatasCausaId(jsonResponse);

                    } catch (Throwable tx) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                    }

                }


                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mCausasInmediatasCausaId = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","CAUSAS INMEDIATAS OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mCausasInmediatasCausaId = null;
            //showProgress(false);
        }
    }

    public class TerminarInterrupcion extends AsyncTask<Void, Void, Boolean> {

        private String interrupcionId;
        private String horaTerminoReal;

        TerminarInterrupcion(String interrupcionId, String horaTerminoReal) {
            this.interrupcionId = interrupcionId;
            this.horaTerminoReal = horaTerminoReal;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("Id", interrupcionId)// id de la tarea a terminar su interripcion
                    .add("HoraTerminoReal", horaTerminoReal)
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/TerminarInterrupcion")
                    .put(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {


                Log.e("TEST",response.toString());



                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mTerminarInterrupcion = null;
            //showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mTerminarInterrupcion = null;
            //showProgress(false);
        }
    }

    public class Responsables extends AsyncTask<Void, Void, Boolean> {

        Responsables() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Contratistas/?ContractId="+getLastContractId())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    setResponsables(jsonResponse);

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mResponsables = null;
            //showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mResponsables = null;
            //showProgress(false);
        }
    }

    public class Layouts extends AsyncTask<Void, Void, Boolean> {

        String date;
        Layouts(String date) {
            this.date=date;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/apiv2/Layouts?ContractId="+getLastContractId()+"&Date="+date)//2019-11-18
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .method("GET", null)
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();
                    setLayouts(jsonResponse);

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mLayouts = null;
            //showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mLayouts = null;
            //showProgress(false);
        }
    }

    public class PodSummary extends AsyncTask<Void, Void, Boolean> {

        String date;
        String filter;

        PodSummary(String date,String filter) {
            this.date=date; //2019-11-18
            this.filter=filter;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/apiv2/PodSummary?filter="+filter+"&Date="+date+"&ContractId="+getLastContractId())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .method("GET", null)
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();
                    setPodSummary(jsonResponse);

                }

                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mPodSummary = null;
            //showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mPodSummary = null;
            //showProgress(false);
        }
    }

    //METHODS EXECUT TASKS
    public boolean attemptToken() {
        if (mToken != null) {
            return false;
        }

        mToken = new Token();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mToken.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptContratistas() {
        if (mContratistas != null) {
            return false;
        }

        mContratistas = new Contratistas();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mContratistas.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptContracts() {
        if (mContracts != null) {
            return false;
        }

        mContracts = new Contracts();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mContracts.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptCambioContrato() {
        if (mCambioContrato != null) {
            return false;
        }

        mCambioContrato = new CambioContrato();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mCambioContrato.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptWorkerCategorias() {
        if (mWorkerCategorias != null) {
            return false;
        }

        mWorkerCategorias = new WorkerCategorias();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mWorkerCategorias.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptWorkers(String filter,String categoriaId) {
        if (mWorkers != null) {
            return false;
        }

        mWorkers = new Workers(filter,categoriaId);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mWorkers.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptWorkersWorkerId(String workerId) {
        if (mWorkersWorkerId != null) {
            return false;
        }

        mWorkersWorkerId = new WorkersWorkerId(workerId);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mWorkersWorkerId.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptTipoEquipos() {
        if (mTipoEquipos != null) {
            return false;
        }

        mTipoEquipos = new TipoEquipos();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mTipoEquipos.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptEquipos(String filter,String tipoId) {
        if (mEquipos != null) {
            return false;
        }

        mEquipos = new Equipos(filter,tipoId);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mEquipos.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptEquiposEquipoId(String id) {
        if (mEquiposEquipoId != null) {
            return false;
        }

        mEquiposEquipoId = new EquiposEquipoId(id);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mEquiposEquipoId.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptEspecialidades() {
        if (mEspecialidades != null) {
            return false;
        }

        mEspecialidades = new Especialidades();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mEspecialidades.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptAreaContratos() {
        if (mAreaContratos != null) {
            return false;
        }

        mAreaContratos = new AreaContratos();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mAreaContratos.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptTareas(String nombreCapataz, String especialidadId, String areaId, String statusTareaId) {
        if (mTareas != null) {
            return false;
        }

        mTareas = new Tareas(nombreCapataz,especialidadId,areaId,statusTareaId);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mTareas.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptTareaStatus() {
        if (mTareaStatus != null) {
            return false;
        }

        mTareaStatus = new TareaStatus();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mTareaStatus.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptFinalizarTarea(String id, String terminoReal, String cantidad) {
        if (mFinalizarTarea != null) {
            return false;
        }

        mFinalizarTarea = new FinalizarTarea(id, terminoReal, cantidad);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mFinalizarTarea.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptIniciarTarea(String id, String inicioReal) {
        if (mIniciarTarea != null) {
            return false;
        }

        mIniciarTarea = new IniciarTarea(id, inicioReal);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mIniciarTarea.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptInterrupciones(String tareaId, String encargadoId, String causaInmediataId, String horaInicio, String horaTerminoEstimado) {

        if (mInterrupciones != null) {
            return false;
        }

        mInterrupciones = new Interrupciones(tareaId, encargadoId, causaInmediataId, horaInicio, horaTerminoEstimado);

        //mAuthTask.execute((Void) null);
        boolean str_result = false;

        try {
            str_result= mInterrupciones.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptCausasInmediatas() {

        if (mCausasInmediatas != null) {
            return false;
        }

        mCausasInmediatas = new CausasInmediatas();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mCausasInmediatas.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptCausasInmediatasCausaId(String causaId) {

        if (mCausasInmediatasCausaId != null) {
            return false;
        }

        mCausasInmediatasCausaId = new CausasInmediatasCausaId(causaId);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mCausasInmediatasCausaId.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptTareasTareaId(String tareaId) {

        if (mTareasTareaId != null) {
            return false;
        }

        mTareasTareaId = new TareasTareaId(tareaId);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mTareasTareaId.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptTerminarInterrupcion(String interrupcionId, String horaTerminoReal) {
        if (mTerminarInterrupcion != null) {
            return false;
        }

        mTerminarInterrupcion = new TerminarInterrupcion(interrupcionId, horaTerminoReal);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mTerminarInterrupcion.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptResponsables() {
        if (mResponsables != null) {
            return false;
        }

        mResponsables = new Responsables();
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mResponsables.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptLayouts(String date) {
        if (mLayouts != null) {
            return false;
        }

        mLayouts = new Layouts(date);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mLayouts.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }

    public boolean attemptPodSummary(String date, String filter) {
        if (mPodSummary != null) {
            return false;
        }

        mPodSummary = new PodSummary(date,filter);
        //mAuthTask.execute((Void) null);

        boolean str_result = false;

        try {
            str_result= mPodSummary.execute((Void) null).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_result;
    }


}