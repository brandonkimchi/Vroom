package com.example.vroomandroidapplicationv4.ui.menu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vroomandroidapplicationv4.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetMenu extends BottomSheetDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = View.inflate(getContext(), R.layout.bottom_sheet_menu, null);
        dialog.setContentView(view);

        TextView editProfile = view.findViewById(R.id.menu_edit_profile);
        TextView settings = view.findViewById(R.id.menu_settings);

        // Open Edit Profile Page
        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            startActivity(intent);
            dismiss();
        });

        // Settings Option
        settings.setOnClickListener(v -> {
            dismiss(); // Close menu
        });

        return dialog;
    }
}
