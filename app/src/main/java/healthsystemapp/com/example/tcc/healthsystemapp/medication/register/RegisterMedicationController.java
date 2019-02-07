package healthsystemapp.com.example.tcc.healthsystemapp.medication.register;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.login.LoginSharedPreferences;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationController;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.dao.MedicationDAO;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationHourModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationSuggestionModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationUri;
import util.CountryUtil;
import util.Message;


public class RegisterMedicationController extends Fragment {

    @BindView(R.id.medicineNameEditText)
    AutoCompleteTextView medicineNameEditText;
    @BindView(R.id.rememberCheckBox)
    CheckBox rememberCheckBox;
    @BindView(R.id.frequencySpinner)
    Spinner frequencySpinner;
    @BindView(R.id.startTimeTimePicker)
    TimePicker startTimeTimePicker;
    @BindView(R.id.sundayCheckBox)
    CheckBox sundayCheckBox;
    @BindView(R.id.mondayCheckBox)
    CheckBox mondayCheckBox;
    @BindView(R.id.tuesdayCheckBox)
    CheckBox tuesdayCheckBox;
    @BindView(R.id.wednesdayCheckBox)
    CheckBox wednesdayCheckBox;
    @BindView(R.id.thursdayCheckBox)
    CheckBox thursdayCheckBox;
    @BindView(R.id.fridayCheckBox)
    CheckBox fridayCheckBox;
    @BindView(R.id.saturdayCheckBox)
    CheckBox saturdayCheckBox;

    public RegisterMedicationController() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeResources(inflater, container);
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.fragment_add_medication));
        setHasOptionsMenu(true);


        initializeListeners();
        return view;
    }

    private CallHandler callHandler = new CallHandler();

    private void initializeListeners() {
        medicineNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                System.out.println(s.toString());
                callHandler.medicationSuggestion(s.toString());

            }
        });


        medicineNameEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idMedicine = adapter.getItem(position).getIdMedicine();
                System.out.println(adapter.getItem(position).getIdMedicine());
            }
        });


    }



    private ArrayAdapter<MedicationSuggestionModel> adapter;

    private class CallHandler {

        public void medicationSuggestion(final String searchTerm) {

            final List<MedicationSuggestionModel> medicationList = new ArrayList<>();

            String uri = null;
            try {
                uri = MedicationUri.medicationList() + "?search=" + URLEncoder.encode(searchTerm, "utf-8") + "&language=" + Locale.getDefault().getLanguage().toLowerCase() + "&country=" + CountryUtil.getCountry();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            StringRequest request = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        response = new String(response.getBytes(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    System.out.println(response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);


                        JSONArray list = jsonObject.getJSONArray("list");

                        for (int i = 0; i < list.length(); i++) {

                            JSONObject item = (JSONObject) list.get(i);
                            MedicationSuggestionModel medicationModel = new MedicationSuggestionModel();
                            medicationModel.setCountry(item.getString("country"));
                            medicationModel.setLanguage(item.getString("language"));
                            medicationModel.setName(item.getString("name").substring(0, 1).toUpperCase() + item.getString("name").substring(1));
                            medicationModel.setIdMedicine(item.getString("idMedicine"));

                            medicationList.add(medicationModel);
                        }

                        adapter = new ArrayAdapter<MedicationSuggestionModel>
                                (getContext(),
                                        android.R.layout.simple_dropdown_item_1line, medicationList);
                        medicineNameEditText.setAdapter(adapter);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    System.out.println("errooooo");
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getActivity().getApplicationContext());
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Accept", "application/json; charset=iso-8859-1");
                    params.put("Authorization", loginSharedPreferences.getToken());
                    return params;
                }


            };

            requestQueue.add(request);

        }

    }

    private void initializeResources(LayoutInflater inflater, ViewGroup container) {
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        view = inflater.inflate(R.layout.fragment_register_medication_controller, container, false);
        medicationDAO = new MedicationDAO(getContext());
        ButterKnife.bind(this, view);
        startTimeTimePicker.setIs24HourView(true);

        List<String> frequencyList = new ArrayList<>();
        frequencyList.add(getString(R.string.oneTime));
        frequencyList.add(getString(R.string.twoTimes));
        frequencyList.add(getString(R.string.threeTimes));
        frequencyList.add(getString(R.string.fourTimes));
        frequencyList.add(getString(R.string.fiveTimes));
        frequencyList.add(getString(R.string.sixTimes));
        frequencyList.add(getString(R.string.sevenTimes));
        frequencyList.add(getString(R.string.eightTimes));
        frequencyList.add(getString(R.string.nineTimes));
        frequencyList.add(getString(R.string.tenTimes));
        frequencyList.add(getString(R.string.elevenTimes));
        frequencyList.add(getString(R.string.twelveTimes));


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, frequencyList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(adp1);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.check).setVisible(true);
        menu.findItem(R.id.delete).setVisible(false);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.check:
                MedicationModel medicationModel = new MedicationModel();

                String id = UUID.randomUUID().toString();
                medicationModel.setName(medicineNameEditText.getText().toString());
                medicationModel.setRemember(rememberCheckBox.isChecked() ? 1 : 0);
                medicationModel.setIdPatientUsesMedicine(id);
                medicationModel.setIdMedicine(idMedicine);
                medicationModel.setSaturday(saturdayCheckBox.isChecked() ? 1 : 0);
                medicationModel.setWednesday(wednesdayCheckBox.isChecked() ? 1 : 0);
                medicationModel.setThursday(thursdayCheckBox.isChecked() ? 1 : 0);
                medicationModel.setTuesday(tuesdayCheckBox.isChecked() ? 1 : 0);
                medicationModel.setMonday(mondayCheckBox.isChecked() ? 1 : 0);
                medicationModel.setFriday(fridayCheckBox.isChecked() ? 1 : 0);
                medicationModel.setSunday(sundayCheckBox.isChecked() ? 1 : 0);

                List<MedicationHourModel> medicationHourModelList = new ArrayList<>();
                int selectedFrequency = frequencySpinner.getSelectedItemPosition() + 1;
                long addTime = DAY / selectedFrequency;
                Integer currentHour = startTimeTimePicker.getCurrentHour();
                Integer currentMinute = startTimeTimePicker.getCurrentMinute();

                long selectedMillis = TimeUnit.HOURS.toMillis(currentHour) + TimeUnit.MINUTES.toMillis(currentMinute);

                long start = selectedMillis;
                for (int i = 1; i <= selectedFrequency; i++) {
                    MedicationHourModel medicationHourModel = new MedicationHourModel();
                    medicationHourModel.setIdPatientUsesMedicineInHour(UUID.randomUUID().toString());
                    medicationHourModel.setIdPatientUsesMedicine(id);
                    medicationHourModel.setHour(start);
                    medicationHourModelList.add(medicationHourModel);

                    int seconds = (int) (start / 1000) % 60 ;
                    int minutes = (int) ((start / (1000*60)) % 60);
                    int hours   = (int) ((start / (1000*60*60)) % 24);



                    start += addTime;

                    System.out.println(hours + " --- " + minutes + " ---- " + seconds);


                }


                medicationModel.setMedicationHourList(medicationHourModelList);

                boolean register = medicationDAO.register(medicationModel);


                if (register) {
                    MedicationController medicationController = new MedicationController();
                    Message.showDialogChangeScreen(medicationController, getActivity(), getString(R.string.success), getString(R.string.successAddingMedication));

                } else {
                    MedicationController medicationController = new MedicationController();
                    Message.showDialogChangeScreen(medicationController, getActivity(), getString(R.string.error), getString(R.string.errorAddingMedication));

                }



                return true;
            default:
                break;
        }

        return false;
    }

    private AlertDialog dialogSuggestionAlertDialog;
    private View view;
    private RequestQueue requestQueue;
    private MedicationDAO medicationDAO;
    private final long DAY = ((60 * 1000) * 60) * 24;
    private String idMedicine;


}
