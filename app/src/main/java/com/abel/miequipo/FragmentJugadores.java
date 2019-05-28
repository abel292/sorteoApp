package com.abel.miequipo;


import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.abel.miequipo.adaptersRecycler.AdapterListAllPlayers;
import com.abel.miequipo.adaptersRecycler.AdapterPlayersSelected;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.rankinJugadores.JugadorRankin;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.popups.Popup_agregar_jugador;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelNuevoJugador;
import com.abel.miequipo.viewmodel.ViewModelRankinJugadores;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJugadores extends Fragment {

    ViewModelNuevoJugador viewModel;
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;
    ListView recyclerView, recyclerViewSeleccionados;

    private String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 4;

    private static String TAG = "TAGG";
    private ImageView avatar;
    private AutoCompleteTextView autoCompleteNombre;
    private Button buttonMenos, buttonMas, buttonSiguiente;
    private FloatingActionButton buttonCrearJug;
    private EditText editTextCuentaEquipos;
    private ImageButton imageButtonLimpiarLista,imageButtonLimpiarLista1;
    private LinearLayout linearLayoutContenedorJugadores;
    private Popup_agregar_jugador popupAgregarJugador;
    private LiveData<List<JugadorSeleccionado>> jugadoresSeleccionados;
    private AdapterListAllPlayers adapter;
    final List<JugadorEntitie> lista = new ArrayList<>();

    private int textlength = 0;
    private List<JugadorEntitie> array_sort = new ArrayList<>();


    public FragmentJugadores() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_jugadores, container, false);


        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerViewSeleccionados = v.findViewById(R.id.recyclerViewSeleccionados);

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelNuevoJugador.class);
        viewModelJugadorSeleccionado = ViewModelProviders.of(getActivity()).get(ViewModelJugadorSeleccionado.class);

        viewModel.getAllJugadores().observe(getActivity(), new Observer<List<JugadorEntitie>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorEntitie> jugadores) {
                // adapter.setWords(jugadores);
                lista.clear();
                adapter = new AdapterListAllPlayers(getContext(), jugadores, recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setClickable(true);
                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        viewModel.deleteJugador(jugadores.get(position));
                        viewModelJugadorSeleccionado.insert(new JugadorSeleccionado(jugadores.get(position).getNombre(), jugadores.get(position).getImagen()));
                        array_sort.clear();
                        lista.clear();
                    }
                });

                for (JugadorEntitie jugador : viewModel.getAllJugadores().getValue()) {
                    lista.add(jugador);
                }

            }
        });

        viewModelJugadorSeleccionado.getAllJugadores().observe(getActivity(), new Observer<List<JugadorSeleccionado>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorSeleccionado> jugadores) {
                //  adapterSeleccionados.setWords(jugadores);

                final AdapterPlayersSelected adapter = new AdapterPlayersSelected(getContext(), jugadores, recyclerView);
                recyclerViewSeleccionados.setAdapter(adapter);
                recyclerViewSeleccionados.setClickable(true);
                recyclerViewSeleccionados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        viewModel.insert(new JugadorEntitie(jugadores.get(position).getNombre(), jugadores.get(position).getImagen()));
                        viewModelJugadorSeleccionado.deleteJugador(jugadores.get(position));
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });


        configurarViews(v);


        return v;
    }

    private void configurarViews(View v) {

        imageButtonLimpiarLista1= v.findViewById(R.id.imageButtonLimpiarLista1);
        autoCompleteNombre = v.findViewById(R.id.autoCompleteNombre);
        buttonCrearJug = v.findViewById(R.id.buttonCrearJug);
        buttonMas = v.findViewById(R.id.buttonMas);
        buttonMenos = v.findViewById(R.id.buttonMenos);
        buttonSiguiente = v.findViewById(R.id.buttonSiguiente);
        editTextCuentaEquipos = v.findViewById(R.id.editTextCuentaEquipos);
        linearLayoutContenedorJugadores = v.findViewById(R.id.linearLayoutContenedorJugadores);
        imageButtonLimpiarLista = v.findViewById(R.id.imageButtonLimpiarLista);
        avatar = v.findViewById(R.id.avatar);


        buttonCrearJug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNuevoJugador();
            }
        });

        imageButtonLimpiarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarListaSeleccionados("seleccionados");
            }
        });

        imageButtonLimpiarLista1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarListaSeleccionados("all");
            }
        });

        buttonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoToActivitySorteo = new Intent(getContext(), ActivitySorteo.class);
                startActivity(intentGoToActivitySorteo);
                //sortearEquipos(jugadoresSeleccionados);
            }
        });
        configAutocomplete(autoCompleteNombre);
        popupAgregarJugador = new Popup_agregar_jugador(getActivity(), getContext());
    }

    private void limpiarListaSeleccionados(String lista) {
        Toast.makeText(getContext(), "Vaciando lista...", Toast.LENGTH_SHORT).show();
        if (lista.equalsIgnoreCase("all")){

            LiveData<List<JugadorEntitie>> listaAllJugadores= viewModel.getAllJugadores();
            for (JugadorEntitie jugador : listaAllJugadores.getValue()) {
                viewModel.deleteJugador(jugador);
            }
        }else {
            viewModel.moveAllToPlayerList(viewModelJugadorSeleccionado.getAllJugadores());
        }
    }


    private void configAutocomplete(final AutoCompleteTextView autoCompleteTextView) {

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // Abstract Method of TextWatcher Interface.
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Abstract Method of TextWatcher Interface.
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlength = autoCompleteTextView.getText().length();
                array_sort.clear();

                for (int i = 0; i < lista.size(); i++) {
                    if (textlength <= lista.get(i).getNombre().length()) {
                        if (autoCompleteTextView.getText().toString().equalsIgnoreCase((String) lista.get(i).getNombre().subSequence(0, textlength))) {
                            array_sort.add(lista.get(i));
                        }
                    }
                }
                recyclerView.setAdapter(new AdapterListAllPlayers(getContext(), array_sort, recyclerView));
                recyclerView.setClickable(true);
                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String nombreClicked = String.valueOf(array_sort.get(position).getNombre());
                        Toast.makeText(getContext(), String.valueOf(array_sort.get(position).getNombre()), Toast.LENGTH_SHORT).show();
                        viewModel.deleteJugador(array_sort.get(position));
                        viewModelJugadorSeleccionado.insert(new JugadorSeleccionado(array_sort.get(position).getNombre(), ""));
                        array_sort.clear();
                        lista.clear();
                        //recyclerView.removeViewAt(position);
                        //adapter.notifyDataSetChanged();

                    }
                });

            }
        });
    }

    private void agregarNuevoJugador() {
        Intent intent= new Intent(getContext(),ActivityAddPlayers.class);
        startActivity(intent);
    }


    public String dispatchTakePictureIntent() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
            Uri photoURI = FileProvider.getUriForFile(getContext(), "com.abel.miequipo.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            //setPic(imageView,mCurrentPhotoPath);
        } catch (IOException ex) {
            Log.e("ERROR", "No se cargo la imagen");
            // Error occurred while creating the File
        }

        return mCurrentPhotoPath;
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        }
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Toast.makeText(getContext(), mCurrentPhotoPath, Toast.LENGTH_LONG).show();
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

        if (requestCode == REQUEST_TAKE_PHOTO) {
            Toast.makeText(getContext(), "pase3", Toast.LENGTH_SHORT).show();
            setPic(avatar, mCurrentPhotoPath);
        }
    }

}
