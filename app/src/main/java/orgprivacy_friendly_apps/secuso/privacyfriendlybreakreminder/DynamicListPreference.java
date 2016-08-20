package orgprivacy_friendly_apps.secuso.privacyfriendlybreakreminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class DynamicListPreference extends ListPreference implements Preference.OnPreferenceChangeListener,DialogInterface.OnClickListener {

    Context mContext;
    SharedPreferences sharedPreferences;

    public DynamicListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected View onCreateDialogView() {
        ListView view = new ListView(getContext());
        view.setAdapter(adapter());
        setEntries(entries());
        setEntryValues(entryValues());
        setValueIndex(Integer.parseInt(sharedPreferences.getString("current_profile", "1")));
        return view;
    }


    private ListAdapter adapter() {
        return new ArrayAdapter(getContext(), android.R.layout.select_dialog_singlechoice);
    }

    private CharSequence[] entries() {
        //action to provide entry data in char sequence array for list

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String[] allProfiles = sharedPreferences.getString("profiles", "").split(";");
        CharSequence[] entries = new CharSequence[allProfiles.length];

        for (int i = 0; i < allProfiles.length; i++) {
            String profileName = allProfiles[i].split(",")[0];
            entries[i] = profileName;
            if (profileName.equals(sharedPreferences.getString("name_text", ""))) {
                editor.putString("current_profile", "" + i);
                System.out.println("My current index: "+i);
            }
        }
        editor.apply();
        return entries;
    }

    private CharSequence[] entryValues() {
        //action to provide entry data in char sequence array for list

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String[] allProfiles = sharedPreferences.getString("profiles", "").split(";");
        CharSequence[] entries = new CharSequence[allProfiles.length];
        for (int i = 0; i < allProfiles.length; i++) {
            String profileName = allProfiles[i].split(",")[0];
            entries[i] = "" + i;
        }
        return entries;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        System.out.println("Geschafft!!!");
        ListPreference listPref = (ListPreference) preference;
        int index = listPref.findIndexOfValue((String) newValue);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_profile", ""+index);
        editor.apply();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String[] allProfile = sharedPreferences.getString("profiles", "").split(";");

        //FIXME Deactivate the onPrefListener in SettingsActivity
        for (int i = 0; i < allProfile.length; i++) {
            if (allProfile[i].split(",")[0].equals(getValue())){
                editor.putString("name_text",allProfile[i].split(",")[0]);
                editor.putString("work_value",allProfile[i].split(",")[1]);
                editor.putString("break_value",allProfile[i].split(",")[2]);
                editor.apply();
            }

        }

        return true;
    }



}
