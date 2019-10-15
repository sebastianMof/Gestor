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
import okhttp3.MultipartBody;
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
    private TareaStatus mTareaStatus = null;
    private Tareas mTareas = null;
    private TareasTareaId mTareasTareaId = null;
    private FinalizarTarea mFinalizarTarea = null;
    private ComentariosTareas mComentariosTareas = null;
    private Interrupciones mInterrupciones = null;
    private CausaInterrupciones mCausaInterrupciones = null;
    private SolucionCausaInterrupcionCausaInterrupcionId mSolucionCausaInterrupcionCausaInterrupcionId = null;
    private StatusInterrupciones mStatusInterrupciones = null;
    private AccionesSoluciones mAccionesSoluciones = null;


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
    private String user;
    private String token;
    private String userId;
    private String selectedContractId;
    private String lastContractId;
    private String fullNameComputed;
    private String contractCode;
    private String apiCode;

    //CONSTRUCTOR
    public Sesion(String username, String password) {
        this.mail = username;
        this.password = password;

    //cambiar luego de terminar
    }

    // PARCELING
    public Sesion(Parcel in) {
        this.user = in.readString();
        this.token = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user);
        dest.writeString(this.token);
    }

    @Override
    public String toString() {
        return "Sesion{" +
                " user='" + user + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    //SETTERS & GETTERS
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

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

    //TASK CLASSES
    public class Token extends AsyncTask<Void, Void, Boolean> {

        Token() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            String postdata = "username="+user+"&password="+password+"&grant_type=password";

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

                        setUser(obj.getString("userName"));
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


            RequestBody formBody = new FormBody.Builder()
                    .add("email", getMail())
                    .build();

            //https://ezprogpdar-apiproductividad.azurewebsites.net/api/Contratistas/?email=diego.murua@koffguerrero.com
            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Contratistas/")
                    .post(formBody)
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

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
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
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Contracts")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {

                        JSONArray array = new JSONArray(jsonResponse);
                        JSONObject auxObj;

                        for (int i =0;i<array.length();i++){
                            auxObj = array.getJSONObject(i);

                            auxObj.get("Id");
                            auxObj.get("Name");
                            //guardar Id y nombre de cada contrato
                        }

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
            mContracts = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
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
            mCambioContrato = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
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

                    try {

                        JSONArray array = new JSONArray(jsonResponse);
                        JSONObject auxObj;

                        for (int i =0;i<array.length();i++){
                            auxObj = array.getJSONObject(i);

                            auxObj.get("Id");
                            auxObj.get("Name");
                            //guardar Id de categoria y nombre de cada categoria
                        }

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
            mWorkerCategorias = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mWorkerCategorias = null;
            //showProgress(false);
        }
    }

    public class Workers extends AsyncTask<Void, Void, Boolean> {

        Workers() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("filter", "FILTER")//RUT / NOMBRE
                    .add("categoriaId", "cateriaId") // categoria id
                    .add("contractId",getLastContractId()) //tambien es opcional pero por la logica pedida para la aplicacion es el cntrato seleccionado en la sesion
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Workers")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {

                        JSONArray array = new JSONArray(jsonResponse);
                        JSONObject auxObj;

                        for (int i =0;i<array.length();i++){
                            auxObj = array.getJSONObject(i);

                            auxObj.get("Id");
                            auxObj.get("Name");
                            auxObj.get("Categoria");
                            auxObj.get("IsActivo");
                            //son los datos de los workers para la lista
                        }

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
            mWorkers = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
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

                    try {
                        JSONObject obj = new JSONObject(jsonResponse);


                        /*
                        {
                            "Id": 16,
                                "Name": "Diego Riquelme",
                                "Code": "1",
                                "CiudadOrigen": "Arica",
                                "CiudadOrigenCode": "151",
                                "Region": "De Arica y Parinacota",
                                "RegionCode": "15",
                                "CodigoContrato": "TBD",
                                "NombreContrato": "Avance de Puesta en Marcha",
                                "IsCapataz": true,
                                "IsDirecto": false,
                                "IsSupervisor": false,
                                "TurnoContratoCode": "1",
                                "TurnoContratoName": "Turno semanal día",
                                "TurnoContratoIsNoche": false,
                                "TurnoContratoFecha": "2019-10-01T19:16:42",
                                "Especialidad": "excavación",
                                "EspecialidadCode": "4",
                                "Categoria": "mano de obra",
                                "CategoriaCode": "1",
                                "ExperienciaYears": 1,
                                "FInicio": "2019-02-01T19:19:42",
                                "FTermino": "2019-12-01T19:19:48",
                                "Genero": 1,
                                "IsActivo": true
                        }*/

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
            mWorkersWorkerId = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
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
                        JSONObject auxObj;

                        for (int i = 0; i < array.length(); i++) {
                            auxObj=array.getJSONObject(i);

                            auxObj.getString("Id");
                            auxObj.getString("Name");
                            //id del tipo de equipo y el tipo de equipo, guardarlos
                        }


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

        Equipos() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("filter", "FILTER")//RUT / NOMBRE
                    .add("tipoId", "cateriaId") // categoria id
                    .add("contractId",getLastContractId()) //tambien es opcional pero por la logica pedida para la aplicacion es el cntrato seleccionado en la sesion
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Equipos")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {

                        JSONArray array = new JSONArray(jsonResponse);
                        JSONObject auxObj;

                        for (int i =0;i<array.length();i++){
                            auxObj = array.getJSONObject(i);

                            auxObj.get("Id");
                            auxObj.get("Code");
                            auxObj.get("Marca");
                            auxObj.get("Modelo");
                            auxObj.get("Patente");
                            auxObj.get("IsActivo");
                            auxObj.get("Combustible");
                            //Datos del equipo, guardar

                        }

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

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
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
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Workers/"+equipoId)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONObject obj = new JSONObject(jsonResponse);

                        /*
                        {
                        "Id": 1,
                        "Code": "EQ001",
                        "Marca": "Subaru",
                        "Modelo": "serie 500",
                        "Patente": "0101ab",
                        "IsActivo": true,
                        "Combustible": 750.0
                        }*/

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

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
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

                    try {

                        JSONArray array = new JSONArray(jsonResponse);
                        JSONObject auxObj;

                        for (int i =0;i<array.length();i++){
                            auxObj = array.getJSONObject(i);

                            auxObj.get("Id");
                            auxObj.get("Name");
                            //lista de especialidades, guardar

                        }

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
            mEspecialidades = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mEspecialidades = null;
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
            RequestBody body = new FormBody.Builder()
                    .add("isFinalizado", "")// Se puede mandar cualquier argumento, el hecho de de agregar el campo lo hace true
                    .add("isInterrupcion", "")// Se puede mandar cualquier argumento, el hecho de de agregar el campo lo hace true
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/TareaStatus")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {

                        JSONArray array = new JSONArray(jsonResponse);
                        JSONObject auxObj;

                        for (int i =0;i<array.length();i++){
                            auxObj = array.getJSONObject(i);

                            auxObj.get("Id");
                            auxObj.get("Name");
                            //lista de status de tareas y ids de cada status de tarea, guardar

                        }

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
            mTareaStatus = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mTareaStatus = null;
            //showProgress(false);
        }
    }

    public class Tareas extends AsyncTask<Void, Void, Boolean> {

        Tareas() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("nombreCapataz", "")
                    .add("especialidadId", "")
                    .add("areaId", "")
                    .add("statusTareaId", "")
                    .add("contractId", "")
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Tareas")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {

                        JSONArray array = new JSONArray(jsonResponse);
                        JSONObject auxObj;

                        for (int i =0;i<array.length();i++){
                            auxObj = array.getJSONObject(i);

                            auxObj.getString("Id");
                            auxObj.getString("ItoName");
                            auxObj.getString("TareaStatusName");
                            auxObj.getBoolean("IsFinalizado");
                            auxObj.getBoolean("IsInterrupción");
                            auxObj.getString("EspecialidadName");
                            auxObj.getString("InicioPrograma");
                            auxObj.getString("TerminoProgramada");
                            auxObj.getString("InicioReal");
                            auxObj.getString("TerminoReal");

                        //Lista de todas las tareas filtradas, guardar o usar en la listview

                        }

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

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
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

        TareasTareaId(String workerId) {
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

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
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

        FinalizarTarea(String tareaId) {
            this.tareaId = tareaId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("Id", tareaId)// id de la tarea a finalizar
                    .add("CantidadCompletada", "2")//cantidad completada
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/FinalizarTarea")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONObject obj = new JSONObject(jsonResponse);
                        // mensaje de avance o algo? guardar?

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

    public class ComentariosTareas extends AsyncTask<Void, Void, Boolean> {

        private String tareaId;
        private String comentario;

        ComentariosTareas(String tareaId, String comentario) {
            this.tareaId = tareaId;
            this.comentario = comentario;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("TareaId", tareaId)// id de la tarea a finalizar
                    .add("UserId", getUserId())
                    .add("StatusId", "") //id del status correspondiente
                    .add("Comentario", comentario)
                    .add("Hora", "00:00")
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/ComentariosTareas")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONObject obj = new JSONObject(jsonResponse);
                        // mensaje de avance o algo? guardar?

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
            mComentariosTareas = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mComentariosTareas = null;
            //showProgress(false);
        }
    }

    public class Interrupciones extends AsyncTask<Void, Void, Boolean> {

        private String tareaId;
        private String descripcion;

        Interrupciones(String tareaId, String descripcion) {
            this.tareaId = tareaId;
            this.descripcion = descripcion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("TareaId", tareaId)// id de la tarea a finalizar
                    .add("UserId", getUserId())
                    .add("Fecha", "") // "AAAA-MM-DD HH:mm"
                    .add("Descripcion", descripcion)
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/Interrupciones")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONObject obj = new JSONObject(jsonResponse);
                        // mensaje de avance o algo? guardar?

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
            mInterrupciones = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mInterrupciones = null;
            //showProgress(false);
        }
    }

    public class CausaInterrupciones extends AsyncTask<Void, Void, Boolean> {

        private String interrupcionesId;
        private String descripcion;

        CausaInterrupciones(String interrupcionesId, String descripcion) {
            this.interrupcionesId = interrupcionesId;
            this.descripcion = descripcion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("InterrupcionesId", interrupcionesId) //InterrupcionesId de metodo interrupciones
                    .add("CreadorId", getUserId())
                    .add("Fecha", "") // AAAA-MM-DD HH:mm
                    .add("Descripcion", descripcion)
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/CausaInterrupciones")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONObject obj = new JSONObject(jsonResponse);
                        // mensaje de avance o algo? guardar?

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
            mCausaInterrupciones = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mCausaInterrupciones = null;
            //showProgress(false);
        }
    }

    public class SolucionCausaInterrupcionCausaInterrupcionId extends AsyncTask<Void, Void, Boolean> {

        private String interrrupcionesId;
        private String descripcion;

        SolucionCausaInterrupcionCausaInterrupcionId(String interrrupcionesId, String descripcion) {
            this.interrrupcionesId = interrrupcionesId;
            this.descripcion = descripcion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("InterrupcionesId", interrrupcionesId)
                    .add("CreadorId", getUserId())
                    .add("Fecha", "") // AAAA-MM-DD HH:mm
                    .add("Descripcion", descripcion)
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/SolucionCausaInterrupcion/"+interrrupcionesId)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONObject obj = new JSONObject(jsonResponse);
                        // mensaje de avance o algo? guardar?

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
            mSolucionCausaInterrupcionCausaInterrupcionId = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mSolucionCausaInterrupcionCausaInterrupcionId = null;
            //showProgress(false);
        }
    }

    public class StatusInterrupciones extends AsyncTask<Void, Void, Boolean> {

        StatusInterrupciones() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("isFinalizado", "")
                    .add("isActivo", "")
                    .add("isPendiente", "") //se puede mandar cualquier value, con tal de mandar el campo lo toma true
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/StatusInterrupciones")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+getToken())
                    .build();

            try (Response response = client.newCall(request).execute()) {

                if (response.body() != null) {

                    String jsonResponse = response.body().string();

                    try {
                        JSONArray array = new JSONArray(jsonResponse);
                        JSONObject auxObj = new JSONObject();
                        for (int i = 0; i < array.length(); i++) {
                            auxObj = array.getJSONObject(i);

                            auxObj.getString("Id"); //Id del status
                            auxObj.getString("Name"); //Nombre del status
                        }

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
            mStatusInterrupciones = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mStatusInterrupciones = null;
            //showProgress(false);
        }
    }

    public class AccionesSoluciones extends AsyncTask<Void, Void, Boolean> {

        private String interrrupcionesId;
        private String descripcion;
        private String statusInterrupcionId;

        AccionesSoluciones(String interrrupcionesId, String descripcion, String statusInterrupcionId) {
            this.interrrupcionesId = interrrupcionesId;
            this.descripcion = descripcion;
            this.statusInterrupcionId = statusInterrupcionId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("InterrupcionesId", interrrupcionesId)
                    .addFormDataPart("StatusInterrupcionId", statusInterrupcionId)
                    .addFormDataPart("UserId",  getUserId())
                    .addFormDataPart("Fecha", "") // AAAA-MM-DD HH:mm
                    .addFormDataPart("Descripcion", descripcion)
                    .build();

            final Request request = new Request.Builder()
                    .url("https://ezprogpdar-apiproductividad.azurewebsites.net/api/AccionesSoluciones")
                    .post(requestBody)
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
            mAccionesSoluciones = null;
            //showProgress(false);

            if (success) {

                Log.e("TEST","AUTH OK");

            } else {

                Log.e("TEST","AUTH NOT OK");
            }
        }

        @Override
        protected void onCancelled() {
            mAccionesSoluciones = null;
            //showProgress(false);
        }
    }


    /*
    private boolean attemptContract() {
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
    */


}