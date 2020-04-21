package com.javabrother.parfumshop.admin_fragment.Wanita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.javabrother.parfumshop.R;
import com.javabrother.parfumshop.admin_fragment.Pria.EditParfumPriaActivity;
import com.javabrother.parfumshop.admin_fragment.Pria.PriaList;

public class EditParfumWanitaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_parfum_wanita);

    }
}
