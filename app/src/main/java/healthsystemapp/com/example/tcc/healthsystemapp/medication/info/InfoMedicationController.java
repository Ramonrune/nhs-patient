package healthsystemapp.com.example.tcc.healthsystemapp.medication.info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.MainActivity;
import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationController;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.dao.MedicationDAO;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationHourModel;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.model.MedicationModel;
import util.Message;


public class InfoMedicationController extends Fragment {

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


    public InfoMedicationController() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeResources(inflater, container);

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.infoMedication));
        setHasOptionsMenu(true);



        medicineNameEditText.setText(medicationModel.getName());
        rememberCheckBox.setChecked(medicationModel.getRemember() == 1 ? true : false);
        sundayCheckBox.setChecked(medicationModel.getSunday() == 1 ? true : false);
        mondayCheckBox.setChecked(medicationModel.getMonday() == 1 ? true : false);
        tuesdayCheckBox.setChecked(medicationModel.getTuesday() == 1 ? true : false);
        wednesdayCheckBox.setChecked(medicationModel.getWednesday() == 1 ? true : false);
        thursdayCheckBox.setChecked(medicationModel.getThursday() == 1 ? true : false);
        fridayCheckBox.setChecked(medicationModel.getFriday() == 1 ? true : false);
        saturdayCheckBox.setChecked(medicationModel.getSaturday() == 1 ? true : false);
        frequencySpinner.setSelection(medicationModel.getMedicationHourList().size() - 1);

        int seconds = (int) (medicationModel.getMedicationHourList().get(0).getHour() / 1000) % 60;
        int minutes = (int) ((medicationModel.getMedicationHourList().get(0).getHour() / (1000 * 60)) % 60);
        int hours = (int) ((medicationModel.getMedicationHourList().get(0).getHour() / (1000 * 60 * 60)) % 24);

        startTimeTimePicker.setCurrentHour(hours);
        startTimeTimePicker.setCurrentMinute(minutes);

        return view;
    }


    private void initializeResources(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_medication_info_controller, container, false);
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

                MedicationModel newMedicationMedicineModel = new MedicationModel();

                String id = medicationModel.getIdPatientUsesMedicine();

                newMedicationMedicineModel.setName(medicationModel.getName());
                newMedicationMedicineModel.setRemember(rememberCheckBox.isChecked() ? 1 : 0);
                newMedicationMedicineModel.setIdPatientUsesMedicine(id);
                newMedicationMedicineModel.setIdMedicine(medicationModel.getIdMedicine());
                newMedicationMedicineModel.setSaturday(saturdayCheckBox.isChecked() ? 1 : 0);
                newMedicationMedicineModel.setWednesday(wednesdayCheckBox.isChecked() ? 1 : 0);
                newMedicationMedicineModel.setThursday(thursdayCheckBox.isChecked() ? 1 : 0);
                newMedicationMedicineModel.setTuesday(tuesdayCheckBox.isChecked() ? 1 : 0);
                newMedicationMedicineModel.setMonday(mondayCheckBox.isChecked() ? 1 : 0);
                newMedicationMedicineModel.setFriday(fridayCheckBox.isChecked() ? 1 : 0);
                newMedicationMedicineModel.setSunday(sundayCheckBox.isChecked() ? 1 : 0);

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
/*
                    int seconds = (int) (start / 1000) % 60;
                    int minutes = (int) ((start / (1000 * 60)) % 60);
                    int hours = (int) ((start / (1000 * 60 * 60)) % 24);


                    System.out.println(hours + " --- " + minutes + " ---- " + seconds);
*/
                    start += addTime;

                }


                newMedicationMedicineModel.setMedicationHourList(medicationHourModelList);

                boolean register = medicationDAO.update(newMedicationMedicineModel);


                if (register) {
                    MedicationController medicationController = new MedicationController();
                    Message.showDialogChangeScreen(medicationController, getActivity(), getString(R.string.success), getString(R.string.successUpdatingMedication));

                } else {
                    MedicationController medicationController = new MedicationController();
                    Message.showDialogChangeScreen(medicationController, getActivity(), getString(R.string.error), getString(R.string.errorUpdatingMedication));

                }

                return true;

            case R.id.delete:


                return true;
            default:
                break;
        }

        return false;
    }


    public void setMedicationModel(MedicationModel medicationModel) {
        this.medicationModel = medicationModel;

    }


    private final long DAY = ((60 * 1000) * 60) * 24;

    private MedicationModel medicationModel;
    private MedicationDAO medicationDAO;
    private View view;

}
