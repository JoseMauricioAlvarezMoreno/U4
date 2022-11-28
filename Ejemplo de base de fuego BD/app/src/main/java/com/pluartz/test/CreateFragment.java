package com.pluartz.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CreateFragment extends DialogFragment {

   String id_pet;
   Button btn_add;
   EditText name, age, color, precio_vacuna;
   private FirebaseFirestore mfirestore;
   private FirebaseAuth mAuth;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if (getArguments()!=null){
         nocontrol = getArguments().getString("nocontrol");
      }
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_create, container, false);
      mfirestore = FirebaseFirestore.getInstance();
      mAuth = FirebaseAuth.getInstance();

      name = v.findViewById(R.id.nombre);
      age = v.findViewById(R.id.edad);
      nocontrol = v.findViewById(R.id.nocontrol);
      btn_add = v.findViewById(R.id.btn_add);

      if (id_pet==null || id_pet==""){
         btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String namepet = name.getText().toString().trim();
               String agepet = age.getText().toString().trim();

               Double nocontrol_ = Double.parseDouble(nocontrol.getText().toString().trim());

               if(name.isEmpty() && age.isEmpty() && colorpet.isEmpty()){
                  Toast.makeText(getContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
               }else{
                  postt(name, email,  nocontrol_);
               }
            }
         });
      }else {
         getPet();
         btn_add.setText("update");
         btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name = name.getText().toString().trim();
               String age = age.getText().toString().trim();


               if(namepet.isEmpty() && agepet.isEmpty() && colorpet.isEmpty()){
                  Toast.makeText(getContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
               }else{
                  updatePet(namepet, agepet, colorpet, precio_vacunapet);
               }
            }
         });
      }
      return v;
   }

   private void updatePet(String namepet, String agepet, String colorpet, Double precio_vacunapet) {
      Map<String, Object> map = new HashMap<>();
      map.put("name", namepet);
      map.put("age", agepet);


      mfirestore.collection("pet").document(id_pet).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void unused) {
            Toast.makeText(getContext(), "Actualizado exitosamente", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
            Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
         }
      });
   }

   private void postPet(String namepet, String agepet, String colorpet, Double precio_vacunapet) {
      String idUser = mAuth.getCurrentUser().getUid();
      DocumentReference id = mfirestore.collection("pet").document();

      Map<String, Object> map = new HashMap<>();
      map.put("id_user", idUser);
      map.put("id", id.getId());
      map.put("name", namepet);


      mfirestore.collection("pet").document(id.getId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void unused) {
            Toast.makeText(getContext(), "Creado exitosamente", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
            Toast.makeText(getContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
         }
      });
   }

   private void getPet(){
      mfirestore.collection("pet").document(id_pet).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
         @Override
         public void onSuccess(DocumentSnapshot documentSnapshot) {
            DecimalFormat format = new DecimalFormat("0.00");
//            format.setMaximumFractionDigits(2);
            String namePet = documentSnapshot.getString("name");

            name.setText(namePet);
            age.setText(agePet);
            color.setText(colorPet);
            precio_vacuna.setText(format.format(precio_vacunapet));
         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
            Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
         }
      });
   }
}