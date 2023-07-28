package com.example.ui;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.example.online_shop.R;
import com.example.pojo.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    ImageView img;
   EditText et_name;
   EditText et_id,et_price,et_description;
   Spinner coin ;
   Button btn_save ;
    Uri img_uri;
    StorageReference storageReference;
    DatabaseReference reference;



    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_add, container, false);

       img = view.findViewById(R.id.img);
       et_name = view.findViewById(R.id.et_name);
        et_price = view.findViewById(R.id.et_price);
       coin = view.findViewById(R.id.sp_coinr);
        et_id = view.findViewById(R.id.et_id);
        et_description = view.findViewById(R.id.et_description);
        btn_save = view.findViewById(R.id.btn_save);




        // add product to firebse
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String name = et_name.getText().toString();
                String price = et_price.getText().toString();
                String coine=coin.getSelectedItem().toString();
                String id = et_id.getText().toString();
                String info = et_description.getText().toString();
                if (TextUtils.isEmpty(name)&&TextUtils.isEmpty(price)
                        &&TextUtils.isEmpty(id)&&TextUtils.isEmpty(info)&&coine.equals("select coin"))
                {
                    et_name.setError("required");
                    et_price.setError("required");
                    et_id.setError("required");
                    et_description.setError("required");


                }else
                {  reference = FirebaseDatabase.getInstance().getReference().child("products");



                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            if (snapshot.hasChild(et_id.getText().toString()))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("code of this product is already exist, Are you wont to replace it? ");
                                builder.setNegativeButton("replace", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        add_product(img_uri.toString(), name, id, info, price, coine);
                                        et_name.setText("");
                                        et_id.setText("");
                                        et_description.setText("");
                                        et_price.setText("");

                                    }
                                });
                                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Toast.makeText(getActivity(), " cancelled ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.show();
                            }else

                            {
                                add_product(img_uri.toString(), name, id, info, price, coine);
                                et_name.setText("");
                                et_id.setText("");
                                et_description.setText("");
                                et_price.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        // picture
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectImg();

            }
        });




        return view;
    }

    public  void add_product(String imgUrl ,String name ,String code, String description,String price ,String coin)
    {
        Product product = new Product( imgUrl,name,code,price,description,coin);
        reference.child(et_id.getText().toString()).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {

                if (task.isSuccessful())
                {
                    Toast.makeText(getActivity(), "successfully add product", Toast.LENGTH_SHORT).show();
                }

            }
        });

        uploadImage(img_uri);

    }

//    public void getPicture()
//    {
//        Intent intent = new Intent();
//        ActivityResultLauncher launcher = registerForActivityResult
//                (new ActivityResultContracts.StartActivityForResult(),
//                        new ActivityResultCallback<ActivityResult>() {
//                            @Override
//                            public void onActivityResult(ActivityResult result) {
//                                if (result.getResultCode()== getActivity().RESULT_OK
//                                      && result.getData() != null
//                                       && result.getData().getData() != null) {
//                                    Uri uri = result.getData().getData();
//
//                                    // إنشاء إشارة لمجموعة Firebase Storage المراد رفع الصورة إليها
//
//                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images");
//
//                                    // إنشاء مرجع للصورة المراد رفعها
//                                    StorageReference imageRef = storageRef.child("image" + uri.getLastPathSegment());
//
//                                    // رفع الصورة إلى Firebase Storage
//                                    UploadTask uploadTask = imageRef.putFile(uri);
//                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//                                        {
//                                            Toast.makeText(getActivity(), "successfully", Toast.LENGTH_SHORT).show();
//
////                                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
////                                            Picasso.get().load(downloadUrl).into(imageView);
//
//
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//
//                                        }
//                                    });
//                                }
//
//                            }
//                        });
//    }

    public void  uploadImage(Uri uri)
    {


        reference.child(et_id.getText().toString()).child("img").setValue(uri.toString());

        storageReference = FirebaseStorage.getInstance().getReference("images/"+et_id.getText().toString());

        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Toast.makeText(getActivity(), "successfully uploaded", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getActivity(), "an error occurred ,try again", Toast.LENGTH_SHORT).show();

                    }
                });





    }

    private void selectImg()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==1 && data != null)
        {
            img_uri = data.getData();
            img.setImageURI(img_uri);


        }
    }

    public String getExt(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton ();
        return  mimeTypeMap.getExtensionFromMimeType (  MimeTypeMap.getFileExtensionFromUrl ( contentResolver.getType ( uri ) ));

    }

    public  void  getImage(ImageView img,Product product){

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("images/"+product.getCode());
        try {
            File localFile = File.createTempFile("tempFile",".jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    img.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getActivity(), "faild ", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
