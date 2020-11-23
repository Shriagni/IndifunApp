package com.deificindia.indifun1.Fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun1.Adapter.AristoPrivillageAdapter;
import com.deificindia.indifun1.Model.agoramodel.GenerateToken;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.bindingmodals.aristocracycenterprivilage.AristoResult;
import com.deificindia.indifun1.bindingmodals.aristocracycenterprivilage.Aristocracy;
import com.deificindia.indifun1.rest.AppConfig;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AristocracyCenterFragment extends Fragment {

    private int id;
    private String title;
    private String image1;
    private CircleImageView image;
    private TextView titile, buytv;
    RecyclerView recyclerView;


    public AristocracyCenterFragment() {
        // Required empty public constructor
    }

    // newInstance constructor for creating fragment with arguments
    public static AristocracyCenterFragment newInstance(int id, String title, String image) {
        AristocracyCenterFragment fragmentFirst = new AristocracyCenterFragment();
        Bundle args = new Bundle();
        //   args.putInt("someInt", page);
        args.putInt("id", id);
        args.putString("title", title);
        args.putString("image", image);

        //   args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id", 0);
        title = getArguments().getString("title", "");
        image1 = getArguments().getString("image", "");

        // tabpageno = getArguments().getInt("someInt", 0);
        //  title = getArguments().getString("someTitle");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aristocracy_center, container, false);
        titile = v.findViewById(R.id.titile);
        image = v.findViewById(R.id.image);
        buytv = v.findViewById(R.id.buytv);
        recyclerView = v.findViewById(R.id.recycler);

        titile.setText(title);

        if (image1 != null && !image1.isEmpty()) {
            Picasso.get().load(URLs.AristocracyImageBaseUrl + image1).error(R.drawable.no_image).into(image);
        } else {
            image.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
        }

        buytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyaristocracycenter(id);
            }
        });

        aristoResult(id);
        return v;
    }


    /*aristocracy center plan*/

    private void buyaristocracycenter(final int id1) {

        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_BUYARISTOCARCYCENTER,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(getString(R.string.aristocracycenter))
                                        .setMessage(getString(R.string.aristocracycenterpay))
                                        .setIcon(R.drawable.ic_check_circle_black_24dp)
                                        .setCancelable(false)
                                        .setPositiveButton(getString(R.string.ok),
                                                new DialogInterface.OnClickListener() {
                                                    @TargetApi(11)
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                        getChildFragmentManager().popBackStackImmediate();
                                                    }
                                                }).show();
                            } else {
                                myDialog.dismiss();
                                Toast.makeText(getActivity(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Toast.makeText(getActivity(), "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(getActivity(), "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("aristocracy_center_id", String.valueOf(id1));

                return params;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void aristoResult(int id){
        Call<Aristocracy> call = AppConfig.loadInterface()
                .aristocracy_center_privileges(id);
        call.enqueue(new Callback<Aristocracy>() {
            @Override
            public void onResponse(Call<Aristocracy> call, Response<Aristocracy> response) {
                Aristocracy resp = response.body();
                if(resp!=null && resp.getStatus()==1 && resp.getResult()!=null){
                    aristoPrivillageAdapterInit(resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<Aristocracy> call, Throwable t) {

            }
        });

    }

    private void aristoPrivillageAdapterInit(List<AristoResult> result){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        AristoPrivillageAdapter adapter = new AristoPrivillageAdapter(result);
        recyclerView.setAdapter(adapter);
    }

}
