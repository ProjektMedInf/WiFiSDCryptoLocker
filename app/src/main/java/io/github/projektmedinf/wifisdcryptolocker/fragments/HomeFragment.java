package io.github.projektmedinf.wifisdcryptolocker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import io.github.projektmedinf.wifisdcryptolocker.R;
import io.github.projektmedinf.wifisdcryptolocker.model.Session;
import io.github.projektmedinf.wifisdcryptolocker.model.User;
import io.github.projektmedinf.wifisdcryptolocker.service.SessionService;
import io.github.projektmedinf.wifisdcryptolocker.service.impl.SessionServiceImpl;
import io.github.projektmedinf.wifisdcryptolocker.service.impl.UserServiceImpl;
import io.github.projektmedinf.wifisdcryptolocker.utils.CryptoUtils;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.util.Date;

import static io.github.projektmedinf.wifisdcryptolocker.utils.Constansts.USER_NAME;

public class HomeFragment extends android.support.v4.app.Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button insertSessionButton = (Button) view.findViewById(R.id.insertSessionButton);
        insertSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRandomSession();
            }
        });
        return view;
    }

    private void insertRandomSession() {
        byte[] date = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(new Date().getTime()).array();
        try {
            User user = new UserServiceImpl(getActivity()).getUserByUserName(USER_NAME);
            Session randomSession = new Session(0l, CryptoUtils.handleByteArrayCrypto(date, user, 1), null, "Vienna", user.getCryptoKeyIV());
            SessionService sessionService = new SessionServiceImpl(getActivity());
            sessionService.insertSession(randomSession);
            Log.d("HomeFragment", sessionService.getAllSessions().toString());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
