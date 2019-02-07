package healthsystemapp.com.example.tcc.healthsystemapp.exams.examHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import healthsystemapp.com.example.tcc.healthsystemapp.R;


public class ExamViewHolder extends RecyclerView.ViewHolder {

    private ImageView hospitalImageView;
    private Button attachmentButton;
    private ImageView doctorImageView;
    private TextView descriptionTextView;
    private TextView dateTextView;
    private TextView hospitalTextView;
    private TextView doctorTextView;

    public ExamViewHolder(@NonNull  View itemView) {
        super(itemView);

        hospitalImageView = (ImageView) itemView.findViewById(R.id.hospitalImageView);
        hospitalTextView = (TextView) itemView.findViewById(R.id.hospitalNameTextView);
        doctorImageView = (ImageView) itemView.findViewById(R.id.doctorImageView);
        doctorTextView = (TextView) itemView.findViewById(R.id.doctorNameTextView);
        attachmentButton = (Button) itemView.findViewById(R.id.attachmentButton);
        descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
        dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
    }

    public ImageView getHospitalImageView() {
        return hospitalImageView;
    }

    public void setHospitalImageView(ImageView hospitalImageView) {
        this.hospitalImageView = hospitalImageView;
    }

    public Button getAttachmentButton() {
        return attachmentButton;
    }

    public void setAttachmentButton(Button attachmentImageView) {
        this.attachmentButton = attachmentImageView;
    }

    public ImageView getDoctorImageView() {
        return doctorImageView;
    }

    public void setDoctorImageView(ImageView doctorImageView) {
        this.doctorImageView = doctorImageView;
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public void setDescriptionTextView(TextView descriptionTextView) {
        this.descriptionTextView = descriptionTextView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public void setDateTextView(TextView dateTextView) {
        this.dateTextView = dateTextView;
    }

    public TextView getHospitalTextView() {
        return hospitalTextView;
    }

    public void setHospitalTextView(TextView hospitalTextView) {
        this.hospitalTextView = hospitalTextView;
    }

    public TextView getDoctorTextView() {
        return doctorTextView;
    }

    public void setDoctorTextView(TextView doctorTextView) {
        this.doctorTextView = doctorTextView;
    }
}
