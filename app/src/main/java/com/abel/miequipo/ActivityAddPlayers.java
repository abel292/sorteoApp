package com.abel.miequipo;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.rankinJugadores.JugadorRankin;
import com.abel.miequipo.objetos.ImagePicker;
import com.abel.miequipo.objetos.Imagen;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelNuevoJugador;
import com.abel.miequipo.viewmodel.ViewModelRankinJugadores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityAddPlayers extends AppCompatActivity {

    private List<EditText> listaEditText = new ArrayList<>();
    private EditText editTextNombre;
    private JugadorEntitie jugadorEntitie;

    private ViewModelNuevoJugador viewModel;
    private ViewModelRankinJugadores viewModelRankin;
    private CircleImageView imageViewPerfil;
    private ImageView imageViewGallery,imageViewCamera;
    private int current = 0;
    private String mCurrentPhotoPath;
    private Button buttonSave;
    private static final int REQUEST_TAKE_PHOTO = 4;
    private String foto = "sin foto";

    private static final int PROFILE_PICTURE = 1;
    private static final int WALL_PICTURE = 2;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        getSupportActionBar().hide();

        buttonSave = findViewById(R.id.buttonSave);
        imageViewPerfil = findViewById(R.id.imageViewPerfil);
        editTextNombre = findViewById(R.id.editTextNombre);

        imageViewCamera= findViewById(R.id.imageViewCamera);
        imageViewGallery = findViewById(R.id.imageViewGallery);

        viewModel = new ViewModelNuevoJugador(getApplication());
        viewModelRankin = new ViewModelRankinJugadores(getApplication());

        imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dispatchTakePictureIntent();
                openGallery();
            }
        });

        imageViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });

        imageViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JugadorEntitie jugador = new JugadorEntitie(editTextNombre.getText().toString(), foto);
                viewModel.insert(jugador);

                JugadorRankin jugadorRankin = new JugadorRankin(editTextNombre.getText().toString(), foto, "0", 0, 0);
                viewModelRankin.insert(jugadorRankin);


                Toast.makeText(getBaseContext(), "Guardado: " + foto, Toast.LENGTH_LONG).show();
                restartCreateNewJugador();
            }
        });
    }

    public void takeGroupPicture() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent chooseImageProfileIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageProfileIntent, 1);
    }

    public void takeGroupProfilePicture() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent choseImageBoatIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(choseImageBoatIntent, 2);
    }

    public void openGallery(){

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, WALL_PICTURE);

    }
    public String dispatchTakePictureIntent() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
            Uri photoURI = FileProvider.getUriForFile(this, "com.abel.miequipo.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            //setPic(imageView,mCurrentPhotoPath);
        } catch (IOException ex) {
            Log.e("ERROR", "No se cargo la imagen");
            // Error occurred while creating the File
        }

        foto = mCurrentPhotoPath;
        return mCurrentPhotoPath;
    }

    private void restartCreateNewJugador() {
        editTextNombre.setText("");
        imageViewPerfil.setImageResource(R.drawable.jugador);
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        }
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Toast.makeText(this, mCurrentPhotoPath, Toast.LENGTH_LONG).show();
        return image;
    }

    public void setPic(ImageView imageView, String Path) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(Path, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK || requestCode == 0) {
            switch (requestCode) {

                case PROFILE_PICTURE:
                    Toast.makeText(this, "picture", Toast.LENGTH_SHORT).show();
                    break;

                case REQUEST_TAKE_PHOTO:
                    Toast.makeText(this, "pase3", Toast.LENGTH_SHORT).show();
                    Imagen.setPic(imageViewPerfil, mCurrentPhotoPath);
                    break;

                case WALL_PICTURE:
                    try {


                        ////////
                        Uri selectedImage = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        final Bitmap selectedImageBitmap = BitmapFactory.decodeStream(imageStream);
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        File f = new File(picturePath);
                        String imageName = f.getName();

                        ////
                        foto= picturePath;
                        Toast.makeText(this, String.valueOf(picturePath ), Toast.LENGTH_LONG).show();

                        imageViewPerfil.setImageBitmap(selectedImageBitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        //Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                    //Toast.makeText(this, "ERROR picture walsss", Toast.LENGTH_SHORT).show();

                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }


    }
}
