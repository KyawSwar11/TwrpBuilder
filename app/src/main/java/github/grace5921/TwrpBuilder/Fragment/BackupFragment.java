package github.grace5921.TwrpBuilder.Fragment;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;
import github.grace5921.TwrpBuilder.R;
import github.grace5921.TwrpBuilder.app.SendMail;
import github.grace5921.TwrpBuilder.util.Config;
import github.grace5921.TwrpBuilder.util.FileUtil;
import github.grace5921.TwrpBuilder.util.ShellExecuter;

import static github.grace5921.TwrpBuilder.util.Config.CheckDownloadedTwrp;

/**
 * Created by Sumit on 19.10.2016.
 */

public class BackupFragment extends Fragment {

    /*Buttons*/
    public static Button mUploadBackup,mDownloadRecovery,mBackupButton,mCancel,mEmailButton,mDeleteBackupButton;

    /*TextView*/
    private TextView ShowOutput;
    private TextView mBuildDescription;
    private TextView mBuildApproved;
    /*Uri*/
    private Uri file;


    /*Strings*/
    private String store_RecoveryPartitonPath_output;
    private String[] parts;
    private String[] recovery_output_last_value;
    private String recovery_output_path;
    private List<String> RecoveryPartitonPath;
    private String userId;
    private String Email;
    private String Uid;
    /*Progress Bar*/
    private ProgressDialog mProgressDialog;
    private ProgressBar mProgressBar;

    /*Notification*/
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    /**/
    private ImageView mRequestApprovedImage;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_backup, container, false);
        /*Buttons*/

        mBackupButton = (Button) view.findViewById(R.id.BackupRecovery);
        mUploadBackup = (Button) view.findViewById(R.id.UploadBackup);
        mEmailButton = (Button) view.findViewById(R.id.mail_backup);
        mDeleteBackupButton = (Button) view.findViewById(R.id.delete_backup);

        mDownloadRecovery = (Button) view.findViewById(R.id.get_recovery);
        mCancel = (Button) view.findViewById(R.id.cancel_upload);

        /*TextView*/

        ShowOutput = (TextView) view.findViewById(R.id.show_output);
        mBuildDescription = (TextView) view.findViewById(R.id.build_description);
        mBuildApproved = (TextView) view.findViewById(R.id.build_approved);
        /*Notification*/
        mNotifyManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        /*Progress Bar*/
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        /*ImageView*/
        mRequestApprovedImage = (ImageView) view.findViewById(R.id.twrp_request_approved);

        /*Define Methods*/

        if (CheckDownloadedTwrp()) {
            mDownloadRecovery.setEnabled(false);
            mBuildDescription.setVisibility(View.GONE);
        } else {
            mDownloadRecovery.setEnabled(true);
            mBuildDescription.setVisibility(View.GONE);
        }

        /*Buttons Visibility */


        /*Find Recovery (Works if device supports /dev/block/platfrom/---/by-name) else gives Exception*/

        try {
            RecoveryPartitonPath = Shell.SU.run("ls -la `find /dev/block/platform/ -type d -name \"by-name\"` | grep RECOVERY");
            store_RecoveryPartitonPath_output = String.valueOf(RecoveryPartitonPath);
            parts = store_RecoveryPartitonPath_output.split("\\s+");
            recovery_output_last_value = parts[7].split("\\]");
            recovery_output_path = recovery_output_last_value[0];
            ShowOutput.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            RecoveryPartitonPath = Shell.SU.run("ls -la `find /dev/block/platform/ -type d -name \"by-name\"` | grep recovery");
            store_RecoveryPartitonPath_output = String.valueOf(RecoveryPartitonPath);
            parts = store_RecoveryPartitonPath_output.split("\\s+");
            ShowOutput.setVisibility(View.VISIBLE);
            try {
                recovery_output_last_value = parts[7].split("\\]");
                recovery_output_path = recovery_output_last_value[0];
            } catch (Exception ExceptionE) {
                Toast.makeText(getContext(), R.string.device_not_supported, Toast.LENGTH_LONG).show();
            }
        }

        /*Check For Backup */

        if (Config.checkBackup()) {
            ShowOutput.setText(getString(R.string.recovery_mount_point) + recovery_output_path);
        } else {
            if (mDownloadRecovery.getVisibility() == View.VISIBLE) {
                mBackupButton.setVisibility(View.GONE);
            } else {
                mBackupButton.setVisibility(View.VISIBLE);
                ShowOutput.setText(R.string.warning_about_recovery_backup);
            }
        }

        /*On Click Listeners */

        mDownloadRecovery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DownloadStream();
                    }
                }
        );

        mBackupButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBackupButton.setVisibility(View.GONE);
                        Shell.SU.run("mkdir -p /sdcard/TwrpBuilder ; dd if=" + recovery_output_path + " of=/sdcard/TwrpBuilder/Recovery.img ; ls -la `find /dev/block/platform/ -type d -name \"by-name\"` >  /sdcard/TwrpBuilder/mounts ; getprop ro.build.fingerprint > /sdcard/TwrpBuilder/fingerprint ; tar -c /sdcard/TwrpBuilder/Recovery.img /sdcard/TwrpBuilder/fingerprint /sdcard/TwrpBuilder/mounts > /sdcard/TwrpBuilder/TwrpBuilderRecoveryBackup.tar ");
                        ShowOutput.setText("Backed up recovery " + recovery_output_path);
                        Snackbar.make(view, R.string.made_recovery_backup, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        /*mUploadBackup.setVisibility(View.VISIBLE);*/
                        mDeleteBackupButton.setVisibility(View.VISIBLE);
                        mEmailButton.setVisibility(View.VISIBLE);
                    }
                }
        );

      /*  mUploadBackup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(view, R.string.Uploading_please_wait, Snackbar.LENGTH_INDEFINITE)
                                .setAction("Action", null).show();
                        uploadStream();
                    }
                }
        );*/
      mDeleteBackupButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mDeleteBackupButton.setVisibility(View.GONE);
              mEmailButton.setVisibility(View.GONE);
              mBackupButton.setVisibility(View.VISIBLE);
              ShellExecuter.rm(getContext(),"TwrpBuilder/TwrpBuilderRecoveryBackup.tar");
          }
      });

        mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendMail sm = new SendMail(getContext(), "twrpbuilder024@gmail.com", "hi", "hi");
                sm.execute();

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mDeleteBackupButton=(Button)getView().findViewById(R.id.delete_backup);
        mEmailButton=(Button)getActivity().findViewById(R.id.mail_backup);
        if (BackupExist()) {
            mDeleteBackupButton.setVisibility(View.VISIBLE);
            mEmailButton.setVisibility(View.VISIBLE);
        }


    }

    private void uploadStream() {

    }

    private void DownloadStream() {

    }
    public static boolean BackupExist() {
        return new File("/sdcard/TwrpBuilder/TwrpBuilderRecoveryBackup.tar").isFile();
    }

}
